package com.rkisuru.ezbuy.payment;


import com.rkisuru.ezbuy.order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private String userId;
    private String userName;
    private String transactionId;
    private String paymentDate;
}
