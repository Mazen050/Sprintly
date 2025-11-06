package com.sprintly.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintly.backend.entities.ProductImage;


public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
