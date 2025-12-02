package com.sprintly.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintly.backend.entities.CartItems;


public interface CartItemRepository extends JpaRepository<CartItems, Long> {
}
