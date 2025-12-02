package com.sprintly.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintly.backend.entities.Reviews;


public interface ReviewRepository extends JpaRepository<Reviews, Long> {
}
