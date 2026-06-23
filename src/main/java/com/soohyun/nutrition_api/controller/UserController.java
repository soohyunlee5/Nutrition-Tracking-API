package com.soohyun.nutrition_api.controller;

import com.soohyun.nutrition_api.exception.UserNotFoundException;
import com.soohyun.nutrition_api.model.User;
import com.soohyun.nutrition_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    List<User> all() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    User one(@PathVariable UUID id) {
        return userService.getUserById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PostMapping
    User newUser(@Valid @RequestBody User newUser) {
        return userService.createUser(newUser);
    }

    @PatchMapping("/{id}")
    User updateUser(@PathVariable UUID id, @Valid @RequestBody User updatedUser) {
        return userService.updateUser(id, updatedUser);
    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }
}
