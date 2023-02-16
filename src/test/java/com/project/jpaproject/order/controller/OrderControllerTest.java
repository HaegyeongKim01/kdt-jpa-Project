package com.project.jpaproject.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.jpaproject.domain.order.OrderStatus;
import com.project.jpaproject.item.dto.ItemDto;
import com.project.jpaproject.item.dto.ItemType;
import com.project.jpaproject.member.dto.MemberDto;
import com.project.jpaproject.order.dto.OrderDto;
import com.project.jpaproject.order.dto.OrderItemDto;
import com.project.jpaproject.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//Mok MVC를 이용한 rest 호출 Test를 쉽게 할 수 있다. //mock MVC 설정 자유롭게 도와줌
@AutoConfigureMockMvc      //restdocs를 추가해주어서 사용가능
@SpringBootTest
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    OrderService orderService;

    @Autowired
    ObjectMapper objectMapper;

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
    }


    @Test
    void saveCallTest() throws Exception {
        //request Body 요청 만들다.
        // Given
        OrderDto orderDto = OrderDto.builder()
                .uuid(UUID.randomUUID().toString())
                .memo("please put it in front of the door")
                .orderDatetime(LocalDateTime.now())
                .orderStatus(OrderStatus.OPENED)
                .memberDto(
                        MemberDto.builder()
                                .name("김나비")
                                .nickName("나비는나비")
                                .address("하늘공원")
                                .age(20)
                                .description("i am vip")
                                .build()
                )
                .orderItemDtos(List.of(
                        OrderItemDto.builder()
                                .price(1000)
                                .quantity(100)
                                .itemDto(
                                        ItemDto.builder()
                                                .type(ItemType.FOOD)
                                                .chef("이춘원")
                                                .price(1000)
                                                .build()
                                )
                                .build()
                ))
                .build();
        // When  // Then  //Controller에 만든 post를 호출 //application 띄우지 않고 test code로 할 수 있는 방법
        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDto)))  //String 형식으로 넣어줘야해서 objectMapper로 변환
                .andExpect(status().isOk()) //200
                .andDo(print());
    }

    @Test
    void getOne() throws Exception {
        mockMvc.perform(get("/orders/{uuid}", uuid)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test  //pageing 조회
    void getAll() throws Exception {
        mockMvc.perform(get("/orders")
                .param("page", String.valueOf(0))  //paeging 이기에
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}