package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    // 연관관계의 주인은 (fk로 member_id를 갖고 있는) Order 이고, Member.orders 는 member 필드에 의해 매핑되었다.
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}
