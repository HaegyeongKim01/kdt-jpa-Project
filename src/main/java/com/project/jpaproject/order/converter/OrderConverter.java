package com.project.jpaproject.order.converter;

import com.project.jpaproject.domain.order.*;
import com.project.jpaproject.item.dto.ItemDto;
import com.project.jpaproject.item.dto.ItemType;
import com.project.jpaproject.member.dto.MemberDto;
import com.project.jpaproject.order.dto.OrderDto;
import com.project.jpaproject.order.dto.OrderItemDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderConverter {

    /**
     * 준영속 상태로 만들기 위해 사용. Dto -> Entity
     * @param orderDto
     * @return
     */
    public Order convertOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setUuid(orderDto.getUuid());
        order.setMemo(orderDto.getMemo());
        order.setOrderStatus(orderDto.getOrderStatus());
        order.setOrderDatetime(LocalDateTime.now());
        //order.setCratedAt(LocalDateTime.now());
        order.setCreatedBy(orderDto.getMemberDto().getName());

        order.setMember(this.convertMember(orderDto.getMemberDto()));
        this.convertOrderItems(orderDto).forEach(order::addOrderItems);

        return order;
    }

    private Member convertMember(MemberDto memberDto) {
        Member member = new Member();
        member.setName(memberDto.getName());
        member.setNickName(memberDto.getNickName());
        member.setAge(memberDto.getAge());
        member.setAddress(memberDto.getAddress());
        member.setDescription(memberDto.getDescription());

        return member;
    }

    //Item이 @OneToMany임을 주의하자
    private List<OrderItem> convertOrderItems(OrderDto orderDto) {
        return orderDto.getOrderItemDtos().stream()
                .map(orderItemDto -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setPrice(orderItemDto.getPrice());
                    orderItem.setQuantity(orderItemDto.getQuantity());
                    orderItem.setItem(this.convertItem(orderItemDto.getItemDto()));
                    return orderItem;
                })
                .collect(Collectors.toList());
    }

    private Item convertItem(ItemDto itemDto) {
        if (ItemType.FOOD.equals(itemDto.getType())) {
            Food food = new Food();
            food.setPrice(itemDto.getPrice());
            food.setStockQuantity(itemDto.getStockQuantity());
            food.setChef(itemDto.getChef());
            return food;
        }

        if (ItemType.FURNITURE.equals(itemDto.getType())) {
            Furniture furniture = new Furniture();
            furniture.setPrice(itemDto.getPrice());
            furniture.setStockQuantity(itemDto.getStockQuantity());
            furniture.setHeight(itemDto.getHeight());
            furniture.setWidth(itemDto.getWidth());
            return furniture;
        }

        if (ItemType.CAR.equals(itemDto.getType())) {
            Car car = new Car();
            car.setPrice(itemDto.getPrice());
            car.setStockQuantity(itemDto.getStockQuantity());
            car.setPower(itemDto.getPower());
            return car;
        }

        throw new IllegalArgumentException("잘못된 아이템 타입 입니다.");
    }


    /**
     * Entity -> Dto
     * @param order Entity
     * @return Dto
     */
    public OrderDto convertOrderDto (Order order) {
        return OrderDto.builder()
                .uuid(order.getUuid())
                .memo(order.getMemo())
                .orderStatus(order.getOrderStatus())
                .orderDatetime(order.getOrderDatetime())
                .memberDto(this.convertMemberDto(order.getMember()))
                .orderItemDtos(order.getOrderItems().stream()
                        .map(this::convertOrderItemDto)
                        .collect(Collectors.toList())
                )
                .build();
    }

    private MemberDto convertMemberDto (Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .name(member.getName())
                .nickName(member.getNickName())
                .description(member.getDescription())
                .age(member.getAge())
                .address(member.getAddress())
                .build();
    }

    private OrderItemDto convertOrderItemDto (OrderItem orderItem) {
        return OrderItemDto.builder()
                .id(orderItem.getId())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .itemDto(this.convertItemDto(orderItem.getItem()))
                .build();
    }

    private ItemDto convertItemDto (Item item) {
        if (item instanceof Food) {
            return ItemDto.builder()
                    .id(item.getId())
                    .type(ItemType.FOOD)
                    .stockQuantity(item.getStockQuantity())
                    .price(item.getPrice())
                    .chef(((Food) item).getChef())
                    .build();
        }

        if (item instanceof Furniture) {
            return ItemDto.builder()
                    .id(item.getId())
                    .type(ItemType.FURNITURE)
                    .stockQuantity(item.getStockQuantity())
                    .price(item.getPrice())
                    .width(((Furniture) item).getWidth())
                    .width(((Furniture) item).getHeight())
                    .build();
        }

        if (item instanceof Car) {
            return ItemDto.builder()
                    .id(item.getId())
                    .type(ItemType.CAR)
                    .stockQuantity(item.getStockQuantity())
                    .price(item.getPrice())
                    .power(((Car) item).getPower())
                    .build();
        }

        throw new IllegalArgumentException("잘못된 아이템 타입 입니다.");
    }

}