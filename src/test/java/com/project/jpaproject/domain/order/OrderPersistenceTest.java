package com.project.jpaproject.domain.order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.project.jpaproject.domain.order.OrderStatus.OPENED;

@Slf4j
@SpringBootTest
public class OrderPersistenceTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void member_insert() {
        Member member = new Member();
        member.setName("kanghonggu");
        member.setAddress("서울시 동작구(만) 움직이면 쏜다.");
        member.setAge(33);
        member.setNickName("guppy.ddd");
        member.setDescription("백앤드 개발자에요.");

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(member);

        transaction.commit();
    }
/*
    @Test
    void 잘못된_설계() {
        Member member = new Member();
        member.setName("kanghonggu");
        member.setAddress("서울시 동작구(만) 움직이면 쏜다.");
        member.setAge(33);
        member.setNickName("guppy.kang");
        member.setDescription("백앤드 개발자에요.");

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(member);
        Member memberEntity = entityManager.find(Member.class, 1L);

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OPENED);
        order.setMemo("부재시 전화주세요.");
        order.setMemberId(memberEntity.getId()); // 외래키를 직접 지정

        entityManager.persist(order);
        transaction.commit();

        Order orderEntity = entityManager.find(Order.class, order.getUuid());
        // FK 를 이용해 회원 다시 조회
        Member orderMemberEntity = entityManager.find(Member.class, orderEntity.getMemberId());
        // orderEntity.getMember() // 객체중심 설계라면 이렇게 해야하지 않을까?
        log.info("nick : {}", orderMemberEntity.getNickName());
    }
*/
    @Test
    @DisplayName("양방향 관계 test")
    void 연관관계_테스트() {
        EntityManager entityManager = emf.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Member member = new Member();
        member.setAddress("서울");
        member.setName("홍길동");
        member.setDescription("hoxy... please push bell ");
        member.setAge(20);
        member.setNickName("서울사람인가");

        //persistence
        entityManager.persist(member);

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderStatus(OPENED);
        order.setMemo("if you want stay ");
        order.setOrderDatetime(LocalDateTime.now());
        order.setMember(member);

        entityManager.persist(order);
        transaction.commit();

        entityManager.clear();
        Order entity = entityManager.find(Order.class, order.getUuid());

        //order로 member 필드 접근 가능. Order class에 Member 객체를 필드로 선언
        log.info("{}", order.getMember().getNickName());

        //Member  class에 List<Order> = new ArrayList<>(); 추가해줌으로써 양방향 관계 성립
        //객체 그래프 탐색
        log.info("{}", entity.getMember().getOrders().size());
        log.info("{}", order.getMember().getOrders().size());    //member.setOrders(Lists.newArrayList(order)); 둘 다 객체에 set해줘야지 안 하면 size0으로 원하는 결과 안 나옴
    }
}
