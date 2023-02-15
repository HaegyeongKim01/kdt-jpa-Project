package com.project.jpaproject.order.service;

import com.project.jpaproject.domain.order.OrderStatus;
import com.project.jpaproject.item.dto.ItemDto;
import com.project.jpaproject.item.dto.ItemType;
import com.project.jpaproject.member.dto.MemberDto;
import com.project.jpaproject.order.dto.OrderDto;
import com.project.jpaproject.order.dto.OrderItemDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@SpringBootTest
class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    static String uuid = UUID.randomUUID().toString();

    @Test
    @DisplayName("order service test")
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
                                .nickName("ggg.hong")
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

}