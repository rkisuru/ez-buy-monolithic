package com.rkisuru.ezbuy.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT orders FROM Order orders WHERE orders.userId = :userId")
    List<Order> findOrdersByUser(String userId);

    @Query("SELECT order FROM Order order WHERE order.userId = :userId AND order.id = :orderId")
    Order findOrderByUser(String userId, @Param("orderId") Long orderId);
}
