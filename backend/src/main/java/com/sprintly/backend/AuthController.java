package com.sprintly.backend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprintly.backend.dtos.JwtResponse;
import com.sprintly.backend.dtos.LoginRequest;
import com.sprintly.backend.dtos.UserDto;
import com.sprintly.backend.mappers.UserMapper;
import com.sprintly.backend.repositories.UserRepository;
import com.sprintly.backend.services.JwtService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtservice;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        var user = userRepository.findByEmail(loginRequest.getEmail()).orElse(null);

        if (user!=null && passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            var token = jwtservice.generateToken(userMapper.toDto(user));
            return ResponseEntity.ok(new JwtResponse(token));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        
        var id = (Long) auth.getPrincipal();

        var user = userRepository.findById(id).orElse(null);
        if(user == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userMapper.toDto(user));
    }
    

}
