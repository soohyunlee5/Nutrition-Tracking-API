package com.soohyun.nutrition_api.controller;

import com.soohyun.nutrition_api.model.User;
import com.soohyun.nutrition_api.security.JwtService;
import com.soohyun.nutrition_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    // Constructor injection of all dependencies
    public AuthController(UserService userService,
                          JwtService jwtService,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    // Register a new user and return a JWT token
    @PostMapping("/register")
    public Map<String, String> register(@Valid @RequestBody User user) {
        // Hash the password before saving to database
        user = new User(user.getName(), user.getUsername(),
                passwordEncoder.encode(user.getPassword()));
        userService.createUser(user);
        String token = jwtService.generateToken(user.getUsername());
        return Map.of("token", token);
    }

    // Login with email and password and return a JWT token
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        // Verify email and password against the database
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );
        String token = jwtService.generateToken(user.getUsername());
        return Map.of("token", token);
    }
}