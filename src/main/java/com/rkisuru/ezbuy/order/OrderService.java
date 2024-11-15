package com.rkisuru.ezbuy.order;

import com.rkisuru.ezbuy.purchase.Purchase;
import com.rkisuru.ezbuy.purchase.PurchaseRepository;
import com.rkisuru.ezbuy.product.Product;
import com.rkisuru.ezbuy.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final PurchaseRepository purchaseRepository;

    public Product initOrder(Long productId) {

        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    public Order createOrder(Long productId, Integer quantity, String oAuth2User) throws Exception {

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
        order.setUserId(oAuth2User);

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders(String oAuth2User) {
        return orderRepository.findOrdersByUser(oAuth2User);
    }

    public Order getOrderById(Long orderId, String oAuth2User) {
        return orderRepository.findOrderByUser(oAuth2User, orderId);
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
