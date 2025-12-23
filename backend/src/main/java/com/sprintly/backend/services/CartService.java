// package com.sprintly.backend.services;

// import java.util.Optional;

// import org.springframework.stereotype.Service;

// import com.sprintly.backend.dtos.CartDto;
// import com.sprintly.backend.dtos.CartItemDto;
// import com.sprintly.backend.dtos.CartItemResponse;
// import com.sprintly.backend.dtos.CartResponse;
// import com.sprintly.backend.entities.CartItems;
// import com.sprintly.backend.entities.Carts;
// import com.sprintly.backend.entities.Users;
// import com.sprintly.backend.mappers.CartMapper;
// import com.sprintly.backend.repositories.CartItemRepository;
// import com.sprintly.backend.repositories.CartRepository;
// import com.sprintly.backend.repositories.ProductRepository;

// import jakarta.transaction.Transactional;
// import lombok.RequiredArgsConstructor;

// @Service
// @RequiredArgsConstructor
// public class CartService {

//     private final CartRepository cartRepository;
//     private final CartItemRepository cartItemRepository;
//     private final ProductRepository productRepository;
//     private final CartMapper cartMapper;


//     public CartDto createCart() {
//         var cart = new Carts();
//         cartRepository.save(cart);

//         return cartMapper.toDto(cart);
//     }
//     // helper: get or create cart for user
//     private Carts getUserCart(Users user) {
//         return cartRepository.findAll().stream().filter(c -> c.getUser().getId().equals(user.getId())).findFirst().orElseGet(() -> {
//                     Carts cart = new Carts();
//                     cart.setUser(user);
//                     return cartRepository.save(cart);
//                 });
//     }

//     //  Add item
//      public CartItemDto addToCart(Long cartId, Long productId) {
//         var cart = cartRepository.getCartWithItems(cartId).orElse(null);
//         if (cart == null) {
//             throw new CartNotFoundException();
//         }

//         var product = productRepository.findById(productId).orElse(null);
//         if (product == null) {
//             throw new ProductNotFoundException();
//         }

//         var cartItem = cart.addItem(product);

//         cartRepository.save(cart);

//         return cartMapper.toDto(cartItem);
//     }

//     //  View cart
//     public CartResponse getCart(Users user) {

//         Carts cart = getUserCart(user);

//         CartResponse response = new CartResponse();
//         response.setCartId(cart.getId());

//         var items = cart.getItems().stream().map(item -> {
//             CartItemResponse dto = new CartItemResponse();
//             dto.setProductId(item.getProduct().getId());
//             dto.setProductName(item.getProduct().getName());
//             dto.setQuantity(item.getQuantity());
//             return dto;
//         }).toList();

//         response.setItems(items);
//         return response;
//     }

//     // Update quantity
//     public void updateItemQuantity(Users user, Long productId, int quantity) {

//         if (quantity <= 0)
//             throw new IllegalArgumentException("Quantity must be greater than zero");

//         Carts cart = getUserCart(user);

//         CartItems item = cart.getItems().stream().filter(i -> i.getProduct().getId().equals(productId)).findFirst().orElseThrow(() -> new RuntimeException("Item not found"));

//         item.setQuantity(quantity);
//         cartItemRepository.save(item);
//     }

//     //  Remove item
//     public void removeItemFromCart(Users user, Long productId) {

//         Carts cart = getUserCart(user);

//         CartItems item = cart.getItems().stream().filter(i -> i.getProduct().getId().equals(productId)).findFirst().orElseThrow(() -> new RuntimeException("Item not found"));

//         cartItemRepository.delete(item);
//     }

//     //  Clear cart
//     public void clearCart(Users user) {

//         Carts cart = getUserCart(user);
//         cartItemRepository.deleteAll(cart.getItems());
//     }
// }


package com.sprintly.backend.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sprintly.backend.dtos.CartItemResponse;
import com.sprintly.backend.dtos.CartResponse;
import com.sprintly.backend.entities.CartItems;
import com.sprintly.backend.entities.Carts;
import com.sprintly.backend.entities.Users;
import com.sprintly.backend.repositories.CartItemRepository;
import com.sprintly.backend.repositories.CartRepository;
import com.sprintly.backend.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    // helper: get or create cart for user
    private Carts getUserCart(Users user) {
        return cartRepository.findAll().stream().filter(c -> c.getUser().getId().equals(user.getId())).findFirst().orElseGet(() -> {
                    Carts cart = new Carts();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    //  Add item
    public void addItemToCart(Users user, Long productId, int quantity) {

        if (quantity <= 0)
            throw new IllegalArgumentException("Quantity must be greater than zero");

        Carts cart = getUserCart(user);

        var product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        CartItems item = cart.getItems().stream().filter(i -> i.getProduct().getId().equals(productId)).findFirst().orElse(null);

        if (item != null) {
            item.setQuantity(item.getQuantity() + quantity);
            cartItemRepository.save(item);
        } else {
            CartItems newItem = new CartItems();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cartItemRepository.save(newItem);
        }
    }

    //  View cart
    public CartResponse getCart(Users user) {

        Carts cart = getUserCart(user);

        CartResponse response = new CartResponse();
        response.setCartId(cart.getId());

        var items = cart.getItems().stream().map(item -> {
            CartItemResponse dto = new CartItemResponse();
            dto.setProductId(item.getProduct().getId());
            dto.setProductName(item.getProduct().getName());
            dto.setQuantity(item.getQuantity());
            return dto;
        }).toList();

        response.setItems(items);
        return response;
    }

    // Update quantity
    public void updateItemQuantity(Users user, Long productId, int quantity) {

        if (quantity <= 0)
            throw new IllegalArgumentException("Quantity must be greater than zero");

        Carts cart = getUserCart(user);

        CartItems item = cart.getItems().stream().filter(i -> i.getProduct().getId().equals(productId)).findFirst().orElseThrow(() -> new RuntimeException("Item not found"));

        item.setQuantity(quantity);
        cartItemRepository.save(item);
    }

    //  Remove item
    public void removeItemFromCart(Users user, Long productId) {

        Carts cart = getUserCart(user);

        CartItems item = cart.getItems().stream().filter(i -> i.getProduct().getId().equals(productId)).findFirst().orElseThrow(() -> new RuntimeException("Item not found"));

        cartItemRepository.delete(item);
    }

    //  Clear cart
    public void clearCart(Users user) {

        Carts cart = getUserCart(user);
        cartItemRepository.deleteAll(cart.getItems());
    }
}