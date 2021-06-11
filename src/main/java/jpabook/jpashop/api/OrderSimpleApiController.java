package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@RestController
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v2/simple-orders")
    public Result<List<SimpleOrderDto>> simpleOrderDto() {
        // ORDER 2
        // N + 1 : 1 + 회원 N + 배송 N 만큼 쿼리가 실행
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        List<SimpleOrderDto> simpleOrderDtoList = orders.stream()
                .map(SimpleOrderDto::new)
                .collect(toList());
        return new Result<>(simpleOrderDtoList);
    }

    @GetMapping("/api/v3/simple-orders")
    public Result<List<SimpleOrderDto>> simpleOrderFetchJoin() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> simpleOrderDtoList = orders.stream()
                .map(SimpleOrderDto::new)
                .collect(toList());
        return new Result<>(simpleOrderDtoList);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate; //주문시간
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }
}
