package com.sprintly.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sprintly.backend.entities.Carts;


public interface CartRepository extends JpaRepository<Carts, Long> {
    @EntityGraph(attributePaths = "items.product")
    @Query("SELECT c FROM Carts c WHERE c.id = :cartId")
    Optional<Carts> getCartWithItems(@Param("cartId") Long cartId);
}
