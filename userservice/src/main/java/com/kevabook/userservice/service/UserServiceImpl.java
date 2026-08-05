package com.kevabook.userservice.service;

import com.kevabook.userservice.domain.User;
import com.kevabook.userservice.dto.CreateUserRequest;
import com.kevabook.userservice.dto.PartialUpdateUserRequest;
import com.kevabook.userservice.dto.UserResponse;
import com.kevabook.userservice.exception.ResourceNotFoundException;
import com.kevabook.userservice.mapper.UserMapper;
import com.kevabook.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserResponse createUser(CreateUserRequest request) {
        log.info("Creating user with email={}", request.getEmail());
        User user = userMapper.mapToUser(request);
        User savedUser = userRepository.save(user);

        log.info("User created successfully, userId={}, role={}", savedUser.getUserId(), savedUser.getRole());
        return userMapper.mapToUserResponse(savedUser);
    }

    @Override
    public Page<UserResponse> getAllUsers(Integer pageNumber,  Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> usersPage = userRepository.findAll(pageable);

        return usersPage.map(userMapper::mapToUserResponse);
    }

    @Override
    public UserResponse getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));

        return userMapper.mapToUserResponse(user);
    }

    @Transactional
    @Override
    public UserResponse partialUpdateUser(Long userId, PartialUpdateUserRequest request) {
        log.info("Updating user with id={}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));

        Optional.ofNullable(request.getFirstName())
                .filter(name -> !name.isBlank())
                .ifPresent(user::setFirstName);

        Optional.ofNullable(request.getLastName())
                .filter(name -> !name.isBlank())
                .ifPresent(user::setLastName);

        Optional.ofNullable(request.getEmail())
                .filter(email -> !email.isBlank())
                .ifPresent(user::setEmail);

        Optional.ofNullable(request.getPhoneNumber())
                .filter(number -> !number.isBlank())
                .ifPresent(user::setPhoneNumber);

        User saveUser = userRepository.save(user);

        log.info("Successfully updated user id={}", userId);
        return userMapper.mapToUserResponse(saveUser);
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));

        userRepository.delete(user);
    }


}
