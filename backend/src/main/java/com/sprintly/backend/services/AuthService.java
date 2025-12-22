package com.sprintly.backend.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sprintly.backend.entities.Users;
import com.sprintly.backend.repositories.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;

    public Users getCurrentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        
        var id = (Long) auth.getPrincipal();

        var user = userRepository.findById(id).orElse(null);
        return user;
    }
}
