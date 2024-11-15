package com.rkisuru.ezbuy.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<Cart> getCart(String oAuth2User) {
        return ResponseEntity.ok(cartService.getCart(oAuth2User));
    }

    @PostMapping
    public ResponseEntity<Cart> addItemsToCart(@RequestParam Long productId, String oAuth2User) {
        return ResponseEntity.ok(cartService.addItemsToCart(productId, oAuth2User));
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(String oAuth2User) {
        cartService.clearCart(oAuth2User);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Long productId, String oAuth2User) {
        cartService.removeItemFromCart(productId, oAuth2User);
        return ResponseEntity.noContent().build();
    }

}
