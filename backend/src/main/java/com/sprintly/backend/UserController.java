package com.sprintly.backend;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.sprintly.backend.dtos.RegisterUserRequest;
import com.sprintly.backend.dtos.UpdateUserRequest;
import com.sprintly.backend.dtos.UserDto;
import com.sprintly.backend.mappers.UserMapper;
import com.sprintly.backend.repositories.UserRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;




//*
// CRUD Controller for Users */
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository UserRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public Iterable<UserDto> getUsers() {
        System.out.println(UserRepository.findAll().stream().map(userMapper::toDto).toList());
        return UserRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getMethodName(@PathVariable Long id) {
        var user = UserRepository.findById(id).orElse(null);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toDto(user));
    }
    
    @PostMapping
    public ResponseEntity<UserDto> creatUser(@Valid @RequestBody RegisterUserRequest request, UriComponentsBuilder uriBuilder) {
        var user = userMapper.toEntity(request);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        System.out.println(user.getPasswordHash());

        UserRepository.save(user);
        var location = uriBuilder.path("/users/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(location).body(userMapper.toDto(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        var user = UserRepository.findById(id).orElse(null);

        if(user == null){
            return ResponseEntity.notFound().build();
        }

        userMapper.update(request, user);
        UserRepository.save(user);

        return ResponseEntity.ok(userMapper.toDto(user));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Valid Long id) {
        var user = UserRepository.findById(id).orElse(null);

        if(user == null){
            return ResponseEntity.notFound().build();
        }

        UserRepository.delete(user);
        return ResponseEntity.noContent().build();
    }
}
