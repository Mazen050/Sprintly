package com.sprintly.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintly.backend.entities.Products;


public interface ProductRepository extends JpaRepository<Products, Long> {
}
