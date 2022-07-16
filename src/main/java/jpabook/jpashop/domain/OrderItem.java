package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "orderItem_id")
    private Long id;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Item item;

    private Integer orderPrice;
    private Integer count;
}
