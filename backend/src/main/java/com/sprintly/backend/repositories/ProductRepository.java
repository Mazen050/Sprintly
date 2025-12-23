package com.sprintly.backend.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sprintly.backend.entities.Products;
import java.util.List;

public interface ProductRepository extends JpaRepository<Products, Long> {

    List<Products> findByCategoryId(Long categoryId);

    @EntityGraph(attributePaths = "category")
    @Query("SELECT p FROM Products p")
    List<Products> findAllWithCategory();
}
