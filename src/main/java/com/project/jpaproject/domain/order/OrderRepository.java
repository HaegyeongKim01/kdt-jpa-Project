package com.project.jpaproject.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {

    //Method Query
    List<Order> findAllByOrderStatus(OrderStatus orderStatus);   //method Query
    List<Order> findAllByOrderStatusOrderByOrderDatetime(OrderStatus orderStatus);   //OrderStatus를 select order_by 정렬 -> OrderDateTime으로

    //Custom Query
    @Query("SELECT o FROM Order AS o WHERE o.memo LIKE %?1%")
    Optional<Order> findByMemo(String memo);



}
