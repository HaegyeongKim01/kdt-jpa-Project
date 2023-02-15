package com.project.jpaproject.order.service;

import com.project.jpaproject.domain.order.OrderRepository;
import com.project.jpaproject.domain.order.OrderStatus;
import com.project.jpaproject.item.dto.ItemDto;
import com.project.jpaproject.item.dto.ItemType;
import com.project.jpaproject.member.dto.MemberDto;
import com.project.jpaproject.order.dto.OrderDto;
import com.project.jpaproject.order.dto.OrderItemDto;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@SpringBootTest
class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    static String uuid = UUID.randomUUID().toString();

    @BeforeEach
    void save_test() {
        // Given
        OrderDto orderDto = OrderDto.builder()
                .uuid(uuid)
                .memo("please put it in front of the door")
                .orderDatetime(LocalDateTime.now())
                .orderStatus(OrderStatus.OPENED)
                .memberDto(
                        MemberDto.builder()
                                .name("홍길동")
                                .nickName("ggg.hhh")
                                .address("인천시")
                                .age(26)
                                .description("no shake it ")
                                .build()
                )
                .orderItemDtos(List.of(
                        OrderItemDto.builder()
                                .price(1000)
                                .quantity(100)
                                .itemDto(
                                        ItemDto.builder()
                                                .type(ItemType.FOOD)
                                                .chef("백종원")
                                                .price(1000)
                                                .build()
                                )
                                .build()
                ))
                .build();
        // When
        String saveUuid = orderService.save(orderDto);

        // Then
        assertThat(uuid).isEqualTo(saveUuid);
        log.info("UUID:{}", uuid);
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }

    @Test
    @DisplayName("조회 test")
    void findOneTest() throws NotFoundException {
        // Given - 값이 주어졌을 때
        String orderUuid = uuid;

        // When - find 할 것 이고
        OrderDto one = orderService.findOne(uuid);

        // Then
        assertThat(one.getUuid()).isEqualTo(orderUuid);
    }

    @Test
    void findAllTest() {
        // Given
        PageRequest page = PageRequest.of(0, 10);

        // When
        Page<OrderDto> all = orderService.findAll(page);

        // Then
        assertThat(all.getTotalElements()).isEqualTo(1);
    }
}