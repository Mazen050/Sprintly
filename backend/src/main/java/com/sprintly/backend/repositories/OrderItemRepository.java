package com.sprintly.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintly.backend.entities.OrderItem;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
