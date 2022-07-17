package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private Integer price;
    private Integer stockQuantity;

    // === 비즈니스 로직 === //
    public void addStock(int stockQuantity){
        this.stockQuantity += stockQuantity;
    }

    public void reduceStock(int stockQuantity){
        int restQuantity = this.stockQuantity - stockQuantity;

        if(restQuantity < 0){
            throw new IllegalStateException("재고량이 0보다 작습니다.");
        }

        this.stockQuantity = restQuantity;
    }

}
