package com.sprintly.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintly.backend.entities.DiscountCode;


public interface DiscountCodeRepository extends JpaRepository<DiscountCode, Long> {
}
