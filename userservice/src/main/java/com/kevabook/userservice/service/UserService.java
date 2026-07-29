package com.kevabook.userservice.service;

import com.kevabook.userservice.dto.CreateUserRequest;
import com.kevabook.userservice.dto.PartialUpdateUserRequest;
import com.kevabook.userservice.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);

    List<UserResponse> getAllUsers();

    UserResponse getUser(Long userId);

    UserResponse partialUpdateUser(Long userId, PartialUpdateUserRequest request);

    void deleteUser(Long userId);
}
