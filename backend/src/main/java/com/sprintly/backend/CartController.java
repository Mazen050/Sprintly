// package com.sprintly.backend;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.util.UriComponentsBuilder;
// import org.springframework.security.core.context.SecurityContextHolder;

// import com.sprintly.backend.entities.CartItems;
// import com.sprintly.backend.entities.Carts;
// import com.sprintly.backend.entities.Users;
// import com.sprintly.backend.mappers.CartMapper;
// import com.sprintly.backend.repositories.CartRepository;
// import com.sprintly.backend.repositories.ProductRepository;
// import com.sprintly.backend.repositories.UserRepository;
// import com.sprintly.backend.dtos.AddItemToCartRequest;
// import com.sprintly.backend.dtos.CartDto;
// import com.sprintly.backend.dtos.CartItemDto;
// import com.sprintly.backend.dtos.CartResponse;
// import com.sprintly.backend.dtos.requests.AddToCartRequest;
// import com.sprintly.backend.dtos.requests.UpdateCartItemRequest;

// import jakarta.transaction.Transactional;
// import jakarta.validation.Valid;
// import lombok.RequiredArgsConstructor;

// @RestController
// @RequiredArgsConstructor
// @RequestMapping("/carts")
// public class CartController {

//     // private final CartService cartService;
//     private final UserRepository userRepository;
//     private final CartRepository cartRepository;
//     private final ProductRepository productRepository;
//     private final CartMapper cartMapper;

//     private Users getCurrentUser() {
//         return (Users) userRepository.findById(Long.valueOf(SecurityContextHolder
//                 .getContext()
//                 .getAuthentication()
//                 .getPrincipal().toString())).get();
//     }

//     @PostMapping
//     public ResponseEntity<CartDto> createCart(
//         UriComponentsBuilder uriBuilder
//     ) {
//         var cart = new Carts();
//         cartRepository.save(cart);

//         var cartDto = cartMapper.toDto(cart);
//         var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cart.getId()).toUri();

//         return ResponseEntity.created(uri).body(cartDto);
//     }

//     @Transactional
//     @PostMapping("/{cartId}/items")
//     public ResponseEntity<CartItemDto> addToCart(
//         @PathVariable Long cartId,
//         @Valid @RequestBody AddItemToCartRequest request) {
//         // var cartItemDto = cartService.addToCart(cartId, request.getProductId());
//         var cart = cartRepository.findById(cartId).orElse(null);
//         if (cart == null) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//         }

//         var product = productRepository.findById(request.getProductId()).orElse(null);
//         if (product == null) {
//             return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//         }

//         var cartItem = cart.getItems().stream()
//             .filter(item -> item.getProduct().getId().equals(request.getProductId()))
//             .findFirst()
//             .orElse(null);

//         if (cartItem != null) {
//             cartItem.setQuantity(cartItem.getQuantity() + 1);
//         }else{
//             var newItem = new CartItems();
//             newItem.setProduct(product);
//             newItem.setQuantity(1);
//             newItem.setCart(cart);
//             cart.getItems().add(newItem);
//             }
//         cartRepository.save(cart);

//         var cartItemDto = cartMapper.toDto(cartItem);
        
//         return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
//     }

//     @Transactional
//     @GetMapping("/{cartId}")
//     public ResponseEntity<CartDto> getCart(@PathVariable Long cartId) {

//         // Users user = getCurrentUser();
//         var cart = cartRepository.findById(cartId).orElse(null);
//         if (cart == null) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//         }

//         return ResponseEntity.ok(cartMapper.toDto(cart));
//     }

//     // @PutMapping("/items")
//     // public ResponseEntity<Void> updateItemQuantity(
//     //         @RequestBody UpdateCartItemRequest request) {

//     //     Users user = getCurrentUser();

//     //     cartService.updateItemQuantity(
//     //             user,
//     //             request.getProductId(),
//     //             request.getQuantity()
//     //     );

//     //     return ResponseEntity.ok().build();
//     // }

//     // @DeleteMapping("/items/{productId}")
//     // public ResponseEntity<Void> removeItemFromCart(
//     //         @PathVariable Long productId) {

//     //     Users user = getCurrentUser();

//     //     cartService.removeItemFromCart(user, productId);

//     //     return ResponseEntity.noContent().build();
//     // }

//     // @DeleteMapping
//     // public ResponseEntity<Void> clearCart() {

//     //     Users user = getCurrentUser();

//     //     cartService.clearCart(user);

//     //     return ResponseEntity.noContent().build();
//     // }
// }


package com.sprintly.backend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sprintly.backend.entities.Users;
import com.sprintly.backend.repositories.UserRepository;
import com.sprintly.backend.dtos.CartResponse;
import com.sprintly.backend.dtos.requests.AddToCartRequest;
import com.sprintly.backend.dtos.requests.UpdateCartItemRequest;
import com.sprintly.backend.services.CartService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final UserRepository userRepository;

    private final CartService cartService;


    private Users getCurrentUser() {
        return (Users) userRepository.findById(Long.valueOf(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal().toString())).get();
    }

    @Transactional
    @PostMapping("/items")
    public ResponseEntity<Void> addToCart(@Valid @RequestBody AddToCartRequest request) {
        
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