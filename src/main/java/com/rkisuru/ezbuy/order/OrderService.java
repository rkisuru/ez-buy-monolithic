package com.rkisuru.ezbuy.order;

import com.rkisuru.ezbuy.payment.Purchase;
import com.rkisuru.ezbuy.payment.PurchaseRepository;
import com.rkisuru.ezbuy.product.Product;
import com.rkisuru.ezbuy.product.ProductRepository;
import com.rkisuru.ezbuy.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final PurchaseRepository purchaseRepository;

    public Product initOrder(Long productId) {

        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    public Order createOrder(Long productId, Integer quantity) throws Exception {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        if (product.getStock() < quantity) {
            throw new Exception("Insufficient stock available");
        }

        Order order = new Order();
        order.setProduct(product);
        order.setQuantity(quantity);
        order.setTotalPrice(product.getPrice() * quantity);
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setUserId(userService.getUserId());
        order.setUserName(userService.getUsername());

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders(Authentication user) {
        return orderRepository.findOrdersByUser(user);
    }

    public Order getOrderById(Long orderId, Authentication user) {
        return orderRepository.findOrderByUser(user, orderId);
    }

    public void deleteOrder(Long orderId) {
        Purchase purchase = purchaseRepository.findByOrderId(orderId);
        if (purchase != null) {
            purchaseRepository.delete(purchase);
        }
        orderRepository.deleteById(orderId);
    }

    public Double totalPrice(Integer quantity, Double price) {
        return quantity * price;
    }
}
