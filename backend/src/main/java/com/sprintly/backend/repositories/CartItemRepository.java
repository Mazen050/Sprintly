package com.sprintly.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintly.backend.entities.CartItem;


public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
