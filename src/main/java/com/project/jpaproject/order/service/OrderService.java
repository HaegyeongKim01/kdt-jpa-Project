package com.project.jpaproject.order.service;

import com.project.jpaproject.domain.order.Order;
import com.project.jpaproject.domain.order.OrderRepository;
import com.project.jpaproject.order.converter.OrderConverter;
import com.project.jpaproject.order.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderConverter orderConverter;

    @Transactional  //aop를 이용하여 EntityManager만들고 이를 통해 영속성 관리해주고 flush 실행 etc
    public String save(OrderDto dto) {
        //1. dto -> entity 변환 (준영속 상태 객체)
        Order order = orderConverter.convertOrder(dto);
        //2. orderRepository.save(entity) -> 영속화
        Order entity = orderRepository.save(order);
        //3. 결과 반환
        return entity.getUuid();
    }

    public void findAll() {

    }

    public void findOne() {

    }
}
