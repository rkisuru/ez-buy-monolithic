package com.rkisuru.ezbuy.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query("SELECT p FROM Purchase p WHERE p.order.id = :orderId")
    Purchase findByOrderId(Long orderId);
}
