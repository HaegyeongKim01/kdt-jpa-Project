package com.project.jpaproject.domain.order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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



}
