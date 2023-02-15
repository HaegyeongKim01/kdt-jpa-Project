package com.project.jpaproject.domain.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName("USE SpringDataJPA test")
    void test() {
        String uuid = UUID.randomUUID().toString();
        Order order = new Order();
        order.setUuid(uuid);
        order.setOrderStatus(OrderStatus.OPENED);
        order.setOrderDatetime(LocalDateTime.now());
        order.setMemo("hello");
        order.setCreatedBy("guppy.kang");
        order.setCreatedAt(LocalDateTime.now());
        //현재까지 준영속 상태. persist() -> flush() 안 했기 때문

        orderRepository.save(order);  //EntityManager 만들고 transaction 열고 닫고, flush 일어나면서 save

        Order order1 = orderRepository.findById(uuid).get();

        List<Order> all = orderRepository.findAll();

        //method Query test
        orderRepository.findAllByOrderStatus(OrderStatus.OPENED);
        orderRepository.findAllByOrderStatusOrderByOrderDatetime(OrderStatus.OPENED);

        //Custom Query test
        Order newOrder = orderRepository.findByMemo("hel").get();      //optional일 땐 get() method 추가해야함
        log.info("adsfasdfadsfa=> {} , {}", newOrder.getMember(), newOrder.getUuid());

    }
}