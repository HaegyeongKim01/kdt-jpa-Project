package com.project.jpaproject.domain.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @Column(name = "id")
    private String uuid;

    @Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDatetime;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Lob   //긴 글 사용 시 사용
    private String memo;

    //fk
    /**
     * insertable, updateble = false ===> Entity가 persist될 때 insert나 update될 때 쿼리 날아가지 않음
     */
    @Column(name = "member_id", insertable = false, updatable = false)
    private Long memberId;

    /**
     * JPA에서 Entity 관리할 때 annotation으로 관리
     * 객체 참조 가능
     * 관계 - 회원 한명 당 여러 주문이 발생할 수 있으니 주문(N) - 회원(1)
     */
    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

}