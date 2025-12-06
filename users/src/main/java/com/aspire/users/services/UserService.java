package com.aspire.users.services;

import com.aspire.users.dtos.CreateUserRequest;
import com.aspire.users.models.Role;
import com.aspire.users.models.User;
import com.aspire.users.repositories.UserRepository;
import com.aspire.users.response.CreateUserResponse;
import com.aspire.users.response.UserResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public CreateUserResponse create(CreateUserRequest createUserRequest) {
        User newUser = User.builder()
                .username(createUserRequest.getUsername())
                .displayName(createUserRequest.getDisplayName())
                .password(createUserRequest.getPassword())
                .role(Role.CUSTOMER)
                .build();

        userRepository.save(newUser);

        return new CreateUserResponse("User successfully created");
    }

    @NullMarked
    public Page<UserResponse> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable)
                .map(UserResponse::from);
    }

    public UserResponse getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(UserResponse::from)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public UserResponse updateUser(Long userId, CreateUserRequest userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setUsername(userRequest.getUsername());
        user.setDisplayName(userRequest.getDisplayName());
        user.setPassword(userRequest.getPassword());

        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getDisplayName(),
                savedUser.getRole()
        );
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
