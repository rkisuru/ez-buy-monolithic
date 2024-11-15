package com.rkisuru.ezbuy.cart;

import com.rkisuru.ezbuy.product.Product;
import com.rkisuru.ezbuy.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public Cart addItemsToCart(Long productId, String oAuth2User) {

        Cart cart = cartRepository.findByUser(oAuth2User);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(oAuth2User);
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new RuntimeException("Product not found"));
        cart.addProduct(product);
        return cartRepository.save(cart);
    }

    public Cart getCart(String oAuth2User) {
        return cartRepository.findByUser(oAuth2User);
    }

    public void clearCart(String oAuth2User) {
        Cart cart = cartRepository.findByUser(oAuth2User);
        cart.getProducts().clear();
        cartRepository.save(cart);
    }

    public void removeItemFromCart(Long productId, String oAuth2User) {
        Cart cart = cartRepository.findByUser(oAuth2User);
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new RuntimeException("Product not found"));
        cart.getProducts().remove(product);
        cartRepository.save(cart);
    }
}
