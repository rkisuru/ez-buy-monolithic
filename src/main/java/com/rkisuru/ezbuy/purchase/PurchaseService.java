package com.rkisuru.ezbuy.purchase;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.rkisuru.ezbuy.order.Order;
import com.rkisuru.ezbuy.order.OrderRepository;
import com.rkisuru.ezbuy.order.OrderStatus;
import com.rkisuru.ezbuy.paypal.PaypalService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final OrderRepository orderRepository;
    private final PaypalService paypalService;

    public void handlePaymentRequest(Long orderId) {

        String currency = "USD";
        String method = "paypal";
        String intent = "sale";
        String cancelUrl = "http://example.com/cancel"; // Example URL
        String successUrl = "http://example.com/success"; // Example URL

        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new EntityNotFoundException("Order not found"));

        try {
            Payment payment = paypalService.createPayment(
                    order.getTotalPrice(), currency, method, intent, order.getId().toString(), cancelUrl, successUrl
            );

            System.out.println("Payment created successfully. Payment ID: " + payment.getId());
        } catch (PayPalRESTException e) {
            System.err.println("Error while creating payment: " + e.getMessage());
        }
    }

    public void processPaymentExecution(String paymentId, String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);

            Order order = orderRepository.findById(Long.valueOf(payment.getTransactions().getFirst().getDescription()))
                    .orElseThrow(()->new EntityNotFoundException("Order not found"));

            Purchase purchase = new Purchase();
            purchase.setTransactionId(payment.getId());
            purchase.setAmount(Double.valueOf(payment.getTransactions().getFirst().getAmount().getTotal()));
            purchase.setOrder(order);
            purchase.setUserId(order.getUserId());
            purchase.setUserName(order.getUserName());
            purchase.setPaymentDate(payment.getCreateTime());

            order.setStatus(OrderStatus.COMPLETED);
            orderRepository.save(order);

        } catch (PayPalRESTException e) {
            throw new RuntimeException(e);
        }
    }
}
