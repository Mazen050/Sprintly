package com.sprintly.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintly.backend.entities.Addresses;


public interface AddressRepository extends JpaRepository<Addresses, Long> {
}
