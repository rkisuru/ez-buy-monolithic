package com.rkisuru.ezbuy.order;

import com.rkisuru.ezbuy.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT orders FROM Order orders WHERE orders.userId = ?#{authentication.principal.claims['sub']}")
    List<Order> findOrdersByUser(Authentication authentication);

    @Query("SELECT order FROM Order order WHERE order.userId = ?#{authentication.principal.claims['sub']} AND order.id = :orderId")
    Order findOrderByUser(Authentication authentication, Long orderId);
}
