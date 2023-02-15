package com.project.jpaproject.order.service;

import com.project.jpaproject.domain.order.Order;
import com.project.jpaproject.domain.order.OrderRepository;
import com.project.jpaproject.order.converter.OrderConverter;
import com.project.jpaproject.order.dto.OrderDto;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 의도치 않게 영속화된 객체(Entity)가 빠져나가게 되면 Query가 모르게 수행 될 가능성이 있어서 [transaction 안에서 끝날 때] Entity -> Dto로 변환 후 내보내는 것이 좋음
 */
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


    @Transactional
    public OrderDto findOne(String uuid) throws NotFoundException {
        //1. 조회를 위한 키값 인자로 받기
        //2. orderRepository.findById(uuid) -> 조회  -> 영속화된 Entity
        //3. Entity -> dto   //transaction 영역 밖으로 보낼 때는 Dto로 내보내는 것이 좋음
        return orderRepository.findById(uuid)         // (2)
                .map(order -> orderConverter.convertOrderDto(order))  // (3)
                .orElseThrow(() -> new NotFoundException("주문을 찾을 수 없습니다. "));    //Optional  : findById return이 없을 수 있다.
    }

    @Transactional
    public Page<OrderDto> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable)   //Dto 객체로 변환
                .map(orderConverter::convertOrderDto);
    }
}
