package com.sprintly.backend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sprintly.backend.entities.Users;
import com.sprintly.backend.dtos.CartResponse;
import com.sprintly.backend.dtos.requests.AddToCartRequest;
import com.sprintly.backend.dtos.requests.UpdateCartItemRequest;
import com.sprintly.backend.services.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    private Users getCurrentUser() {
        return (Users) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    @PostMapping("/items")
    public ResponseEntity<Void> addToCart(@RequestBody AddToCartRequest request) {

        Users user = getCurrentUser();

        cartService.addItemToCart(
                user,
                request.getProductId(),
                request.getQuantity()
        );

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart() {

        Users user = getCurrentUser();

        return ResponseEntity.ok(cartService.getCart(user));
    }

    @PutMapping("/items")
    public ResponseEntity<Void> updateItemQuantity(
            @RequestBody UpdateCartItemRequest request) {

        Users user = getCurrentUser();

        cartService.updateItemQuantity(
                user,
                request.getProductId(),
                request.getQuantity()
        );

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeItemFromCart(
            @PathVariable Long productId) {

        Users user = getCurrentUser();

        cartService.removeItemFromCart(user, productId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart() {

        Users user = getCurrentUser();

        cartService.clearCart(user);

        return ResponseEntity.noContent().build();
    }
}
