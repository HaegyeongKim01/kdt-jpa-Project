package com.project.jpaproject.order.dto;

import com.project.jpaproject.item.dto.ItemDto;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private Long id;
    private Integer price;
    private Integer quantity;

    private ItemDto itemDto;
}