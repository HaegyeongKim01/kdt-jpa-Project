package com.project.jpaproject.domain.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseEntity{

    @Id
    @Column(name = "id")
    private String uuid;

    @Column(name = "order_datetime", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderDatetime;

    @Enumerated(EnumType.STRING)  //Enum의 타입을 String그대로 가지고 간다.
    private OrderStatus orderStatus;

    @Lob   //긴 글 사용 시 사용
    private String memo;

    //fk
    /**
     * JPA에서 Entity 관리할 때 annotation으로 관리
     * 객체 참조 가능
     * 관계 - 회원 한명 당 여러 주문이 발생할 수 있으니 주문(N) - 회원(1)
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)  //fetch를 지연로딩으로 설정 . 프록시를 실제 사용할 때 초기화하면서 데이터 베이스를 조회한다. //fetch(default:EAGER)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true) //영속성 전이 추가 +cascade   //고아객체 true 같이 삭제  //fetch(deafault: LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    //연관관계 편의 메소드
    public void setMember(Member member) {
        //이미 존재한다면 remove
        if (Objects.nonNull(this.member)) {
            member.getOrders().remove(this);
        }
        this.member = member;
        member.getOrders().add(this);
    }

    /**
     * OrderItems와 연관관계 편의 메소드
     * @param orderItem
     */
    public void addOrderItems(OrderItem orderItem) {
        orderItem.setOrder(this);
    }

}