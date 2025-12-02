package com.sprintly.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintly.backend.entities.OrderItems;


public interface OrderItemRepository extends JpaRepository<OrderItems, Long> {
}
