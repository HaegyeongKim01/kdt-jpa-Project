package com.project.jpaproject.domain.order;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.project.jpaproject.domain.order.OrderStatus.OPENED;

@Slf4j
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)  //BeforeAll 사용으로 추가
public class ProxyTest {

    @Autowired
    EntityManagerFactory emf;

    private String uuid = UUID.randomUUID().toString();

    @BeforeAll    //Duplicate memer.Uk,..... Error 발생으로 BeforeEach -> BeforeAll로 바꾸고 @TestInstance추가
    void setUp() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 주문 엔티티
        Order order = new Order();
        order.setUuid(uuid);
        order.setMemo("please... push the bell");
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OPENED);

        entityManager.persist(order);

        // 회원 엔티티
        Member member = new Member();
        member.setName("kanghonggu");
        member.setNickName("guppy.ki");
        member.setAge(33);
        member.setAddress("서울시 동작구");
        member.setDescription("KDT 화이팅");

        member.addOrder(order); // 연관관계 편의 메소드 사용
        entityManager.persist(member);
        transaction.commit();
    }


    @Test
    void proxy() {
        EntityManager entityManager = emf.createEntityManager();
        Order order = entityManager.find(Order.class, uuid);

        Member member = order.getMember();
        log.info("MEMBER USE BEFORE IS-LOADED: {}", emf.getPersistenceUnitUtil().isLoaded(member));   //member객체는 Lazy: Proxy객체, EAGER:
        String nickName = member.getNickName();  //member객체 사용
        log.info("MEMBER USE AFTER IS-LOADED: {}", emf.getPersistenceUnitUtil().isLoaded(member));   //member 객체 Lazy: entity

    }

    /**
     * 영속성 정의하지 않으면 OrderItem에 대한 insert문이 없다. addOrderItems() 를 호출했음에도 불구하고
     * orderItem이 영속화가 같이 되지 않고 준영속 상태로 남아있게 된다.
     */
//    @Test
//    @DisplayName("영속성 전이")
//    void move_persist() {
//        EntityManager entityManager= emf.createEntityManager();
//        EntityTransaction transaction = entityManager.getTransaction();
//
//        Order order = entityManager.find(Order.class, uuid);
//
//        transaction.begin();
//
//        OrderItem orderItem = new OrderItem();   //준영속 상태
//        orderItem.setQuantity(10);
//        orderItem.setPrice(1000);
//
//        order.addOrderItems(orderItem); //order class에서 orderItems를 cascade하여 commit 하면 영속전이를 통해 영속으로 바뀌고 쿼리가 날라간다.
//
//        transaction.commit();   //flush()   //영속성 정의 X ordrItem: 쿼리 날라가지 않음
//
//        entityManager.clear();
//
//        //////////Orphan Test start
//        Order order2 = entityManager.find(Order.class, uuid);
//
//        transaction.begin();
//        order2.getOrderItems().remove(0);   //고아상태 flush 순간 RDS 에서도 삭제하겠다.   //orphan 속성 추가해야함 (ORder.class에서)
//        transaction.commit();
//    }

}
