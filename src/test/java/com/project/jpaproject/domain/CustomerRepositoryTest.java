package com.project.jpaproject.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository repository;

    @Test
    void testCustomer() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("gong");
        customer.setLastName("kang");

        //When
        repository.save(customer);

        //Then
        Customer entity = repository.findById(1L).get();
        log.info("hihi" );
    }

}