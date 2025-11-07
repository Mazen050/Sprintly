package com.sprintly.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintly.backend.entities.Users;


public interface UserRepository extends JpaRepository<Users, Long> {
}
