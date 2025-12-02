package com.sprintly.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintly.backend.entities.DiscountCodes;


public interface DiscountCodeRepository extends JpaRepository<DiscountCodes, Long> {
}
