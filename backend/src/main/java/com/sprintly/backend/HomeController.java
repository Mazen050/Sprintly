package com.sprintly.backend;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprintly.backend.entities.Users;
import com.sprintly.backend.repositories.UserRepository;


@RestController
public class HomeController {

    private final UserRepository usersRepository;

    public HomeController(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/testDB")
    public ResponseEntity<?> testConnection() {
        try {
            // Fetch all users from DB
            List<Users> users = usersRepository.findAll();
            int count = users.size();

            return ResponseEntity.ok("✅ Connected! Total users in DB: " + count);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("❌ Database connection failed: " + e.getMessage());
        }
    }

    @GetMapping("/")
    public String index() {
        return "Welcome to the Sprintly Backend!";
    }

    @PostMapping("/")
    public String postindex() {
        return "This is a POST request to the Sprintly Backend!";
    }
}
