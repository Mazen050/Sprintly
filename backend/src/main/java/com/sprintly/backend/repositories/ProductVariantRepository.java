package com.sprintly.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintly.backend.entities.ProductVariant;


public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
}
