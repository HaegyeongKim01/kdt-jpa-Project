package com.project.jpaproject.domain.order;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Table(name = "order_item")
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private int price;
    private int quantity;

    @Column(name = "order_id") // fk
    private String order_id;
    @Column(name = "item_id") // fk
    private Long item_id;
}