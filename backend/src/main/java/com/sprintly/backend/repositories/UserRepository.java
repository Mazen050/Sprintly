package com.sprintly.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintly.backend.entities.Users;


public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);
}
