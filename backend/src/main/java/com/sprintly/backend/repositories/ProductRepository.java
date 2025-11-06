package com.sprintly.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintly.backend.entities.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {
}
