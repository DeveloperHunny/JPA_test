package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    private Member member;

    @OneToMany
    private List<OrderItem> orderItems;

    @OneToOne
    private Delivery delivery;

    private Date orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

}
