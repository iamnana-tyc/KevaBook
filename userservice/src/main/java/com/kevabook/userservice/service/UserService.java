package com.kevabook.userservice.service;

import com.kevabook.userservice.dto.CreateUserRequest;
import com.kevabook.userservice.dto.PartialUpdateUserRequest;
import com.kevabook.userservice.dto.UserResponse;
import org.springframework.data.domain.Page;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);

    Page<UserResponse> getAllUsers(Integer pageNumber, Integer pageSize);

    UserResponse getUser(Long userId);

    UserResponse partialUpdateUser(Long userId, PartialUpdateUserRequest request);

    void deleteUser(Long userId);
}
