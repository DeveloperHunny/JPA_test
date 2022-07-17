package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired private EntityManager em;

    @Autowired private OrderService orderService;
    @Autowired private OrderRepository orderRepository;



    @Test
    public void 주문성공() throws Exception {
        //given
        Member member = createMember();
        Item item = createItem();
        int count = 3;

        //when
        Long orderId = orderService.order(member.getId(), item.getId(), count);

        //then
        Order findOne = orderRepository.findOne(orderId).get();

        //주문 상태 : ORDER
        assertThat(findOne.getStatus()).isEqualTo(OrderStatus.ORDER);

        //주문 상품 수 동일해야 한다.
        assertThat(findOne.getOrderItems().size()).isEqualTo(1);

        //주문 가격 : orderItem.getTotalPrice의 합
        int totalPrice = 0;
        for(OrderItem orderItem : findOne.getOrderItems()){
            totalPrice += orderItem.getTotalPrice();
        }
        assertThat(findOne.getTotalPrice()).isEqualTo(totalPrice);

        //주문한 상품의 수가 감소해야 한다.
        assertThat(item.getStockQuantity()).isEqualTo(10 - count);

    }

    @Test
    public void 주문실패_재고초과() throws Exception {
        //given
        Member member = createMember();
        Item item = createItem();
        int count = 11;

        //when
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> {
            orderService.order(member.getId(), item.getId(), count);
        });

        //then
        assertThat(e.getMessage()).isEqualTo("재고량이 0보다 작습니다.");

    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember();
        Item item = createItem();
        int count = 5;

        Long orderId = orderService.order(member.getId(), item.getId(), count);

        //when
        orderService.cancleOrder(orderId);
        Order order = orderRepository.findOne(orderId).get();

        //then

        //Delivery Status : cancle
        assertThat(order.getDelivery().getStatus()).isEqualTo(DeliveryStatus.CANCEL);

        //Order Status : cancle
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCEL);

        //재고량 복원
        assertThat(item.getStockQuantity()).isEqualTo(10);
    }

    @Test
    public void 주문취소_실패_배달완료() throws Exception {
        //given
        Member member = createMember();
        Item item = createItem();
        int count = 5;

        Long orderId = orderService.order(member.getId(), item.getId(), count);

        //when
        Order order = orderRepository.findOne(orderId).get();
        order.getDelivery().setStatus(DeliveryStatus.COMP);

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> {
            orderService.cancleOrder(orderId);
        });

        //then
        assertThat(e.getMessage()).isEqualTo("이미 배송된 주문은 취소가 불가능합니다.");
    }

    private Member createMember(){
        Member member = new Member();
        member.setName("member1");
        member.setAddress(new Address("seoul" , "mabuk", "16910"));
        em.persist(member);

        return member;
    }

    private Item createItem(){
        Item item = new Item();
        item.setName("snack");
        item.setPrice(1000);
        item.setStockQuantity(10);
        em.persist(item);

        return item;
    }
}