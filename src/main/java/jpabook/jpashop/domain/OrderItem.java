package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "order_item")
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    private Integer orderPrice;
    private Integer count;

    // === 생성 로직 === //
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.item = item;
        orderItem.count = count;
        orderItem.orderPrice = orderPrice;

        item.reduceStock(count);
        return orderItem;
    }

    // === 비즈니스 로직 === //

    //주문 취소
    public void cancle() {this.item.addStock(this.count);}


    //전체 가격 조회
    public Integer getTotalPrice(){
        return this.orderPrice * this.count;
    }

}
