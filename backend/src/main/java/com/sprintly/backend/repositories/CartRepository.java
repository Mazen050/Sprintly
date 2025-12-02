package com.sprintly.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintly.backend.entities.Carts;


public interface CartRepository extends JpaRepository<Carts, Long> {
}
