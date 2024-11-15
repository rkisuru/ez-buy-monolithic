package com.rkisuru.ezbuy.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT cart FROM Cart cart WHERE cart.userId = :userId")
    Cart findByUser(String userId);
}
