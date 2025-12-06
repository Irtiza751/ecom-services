package com.aspire.users.controllers;

import com.aspire.users.dtos.CreateUserRequest;
import com.aspire.users.response.CreateUserResponse;
import com.aspire.users.response.UserResponse;
import com.aspire.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @NullMarked
    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        return new ResponseEntity<>(userService.create(createUserRequest), HttpStatus.CREATED);
    }

    @NullMarked
    @GetMapping
    public ResponseEntity<List<UserResponse>> getUserList() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @NullMarked
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getSingleUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @NullMarked
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId, @RequestBody CreateUserRequest userRequest) {
        return ResponseEntity.ok(userService.updateUser(userId, userRequest));
    }

    @NullMarked
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
