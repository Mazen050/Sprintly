package com.sprintly.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintly.backend.entities.Order;


public interface OrderRepository extends JpaRepository<Order, Long> {
}
