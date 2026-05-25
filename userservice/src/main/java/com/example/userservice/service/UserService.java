package com.example.userservice.service;

import com.example.userservice.dto.UserPageResponse;
import com.example.userservice.dto.UserRequest;
import com.example.userservice.dto.UserResponse;


public interface UserService {
    UserResponse createUser(UserRequest request);

    UserResponse getUserById(String userId);

    void deleteUserById(String userId);

    UserPageResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    UserResponse partialUpdateUser(String userId, UserRequest request);
}
