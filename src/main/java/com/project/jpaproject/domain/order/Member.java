package com.project.jpaproject.domain.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@Setter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "nick_name", nullable = false, length = 30, unique = true)
    private String nickName;

    private int age;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description")
    private String description;

    /**
     * 회원(Member)에서 Order 객체 가지고 오도록
     */
    @OneToMany(mappedBy = "member")   //연관관계 주인 // fk를 가지고 있는 객체의 field값 mappedBy에 설정
    private List<Order> orders = new ArrayList<>();

    //연관관계 편의 메소드
    public void addOrder(Order order) {
        order.setMember(this);
    }

}