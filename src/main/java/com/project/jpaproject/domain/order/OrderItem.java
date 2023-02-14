package com.project.jpaproject.domain.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "order_item")
@Getter
@Setter
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int price;
    private int quantity;

    /**
     * Order과 연관관계 편의 메소드
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id" ,referencedColumnName = "id")
    private Order order;

    /**
     * item 과 order_item의 연관관계
     */
    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    //연관관계 편의 메소드
    public void setOrder(Order order) {
        if(Objects.nonNull(order)) {
            order.getOrderItems().remove(this.order);
        }
        this.order = order;
        order.getOrderItems().add(this);
    }

    //연관관계 편의 메소드
    public void setItem(Item item) {
        if (Objects.nonNull(item)) {
            item.getOrderItems().remove(this.item);
        }
        this.item = item;
        item.getOrderItems().add(this);
    }

}