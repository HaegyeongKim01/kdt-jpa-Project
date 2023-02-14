package com.project.jpaproject.domain.order;

import com.project.jpaproject.domain.parent.Parent;
import com.project.jpaproject.domain.parent.ParentId;
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

@Slf4j
@SpringBootTest
public class ImproveMappingTest {

    @Autowired
    private EntityManagerFactory emf;

    @Test
    @DisplayName("Inheritance_test")
    void Inheritance_test() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Food food = new Food();
        food.setPrice(2000);
        food.setStockQuantity(200);
        food.setChef("이연복");
        food.setName("짬뽕");

        entityManager.persist(food);

        transaction.commit();
    }

    @Test
    @DisplayName("mapped_super_class Test")
    void mapped_super_class_test(){
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderStatus(OrderStatus.OPENED);
        order.setMemo("---");
        order.setOrderDatetime(LocalDateTime.now());

        order.setCreatedBy("guppy.kang");
        order.setCreatedAt(LocalDateTime.now());

        entityManager.persist(order);

        transaction.commit();

    }

    @Test
    @DisplayName("여러 식별자 테스트 ")
    void id_test() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Parent parent = new Parent();
        parent.setId(new ParentId("id1", "id2"));

        entityManager.persist(parent);
        entityManager.clear();   //persistent context 영속성 clear
        Parent parent1 = entityManager.find(Parent.class, new ParentId("id1", "id2"));
        log.info("{} {}", parent1.getId().getId1(), parent1.getId().getId2());

        transaction.commit();

    }

}
