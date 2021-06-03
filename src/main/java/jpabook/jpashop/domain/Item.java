package jpabook.jpashop.domain;

import jpabook.jpashop.domain.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Item을 상속하는 엔티티와의 테이블 전략 : 싱글은 한 테이블에 모두 담겠다
@DiscriminatorColumn(name = "dtype") // 상속한 엔티티의 구분 컬럼명
@Getter @Setter
@Entity
public abstract class Item {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
