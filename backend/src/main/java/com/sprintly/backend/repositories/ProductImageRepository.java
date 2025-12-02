package com.sprintly.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintly.backend.entities.ProductImages;


public interface ProductImageRepository extends JpaRepository<ProductImages, Long> {
}
