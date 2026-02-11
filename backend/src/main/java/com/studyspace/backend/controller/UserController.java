package com.studyspace.backend.controller;

import com.studyspace.backend.dto.AuthRequest;
import com.studyspace.backend.dto.AuthResponse;
import com.studyspace.backend.model.User;
import com.studyspace.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody User user) {
        try {
            User created = userService.registerUser(user);
            return new AuthResponse(created.getId(), created.getUsername(), created.getEmail());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return userService.authenticate(request.getIdentifier(), request.getPassword())
                .map(u -> new AuthResponse(u.getId(), u.getUsername(), u.getEmail()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
    }

    @GetMapping
    public List<AuthResponse> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(u -> new AuthResponse(u.getId(), u.getUsername(), u.getEmail()))
                .collect(Collectors.toList());
    }
}