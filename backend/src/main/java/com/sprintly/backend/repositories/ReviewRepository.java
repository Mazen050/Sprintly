package com.sprintly.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintly.backend.entities.Review;


public interface ReviewRepository extends JpaRepository<Review, Long> {
}
