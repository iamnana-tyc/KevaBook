package com.example.userservice.service;

import com.example.userservice.dto.UserPageResponse;
import com.example.userservice.dto.UserRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.exception.APIException;
import com.example.userservice.exception.ResourceNotFoundException;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserResponse createUser(UserRequest request) {
        if (request == null )
            return null;

        if (userRepository.existsUserByEmail(request.getEmail()))
             throw new APIException("The user with " + request.getEmail() + " already exist");

        User user =  userMapper.mapToUser(request);
        userRepository.save(user);

        return userMapper.mapToUserResponse(user);
    }

    @Override
    public UserResponse getUserById(String userId) {
        if (userId == null)
            return null;

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));

        return userMapper.mapToUserResponse(user);
    }

    @Transactional
    @Override
    public void deleteUserById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        userRepository.delete(user);
    }

    @Override
    public UserPageResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortAndOrderBy = "asc".equalsIgnoreCase(sortOrder)
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortAndOrderBy);
        Page<User> userPage = userRepository.findAll(pageable);
        List<UserResponse> users = userPage.getContent()
                .stream()
                .map(userMapper::mapToUserResponse)
                .toList();

        return new UserPageResponse(
                users,
                userPage.getNumber(),
                userPage.getSize(),
                userPage.getTotalElements(),
                userPage.getTotalPages(),
                userPage.isLast()
        );
    }

    @Transactional
    @Override
    public UserResponse partialUpdateUser(String userId, UserRequest request) {
        if (userId == null)
            return null;

        if (request == null)
            return null;

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));

        if (request.getEmail() != null &&
                !request.getEmail().equals(user.getEmail()) &&
                userRepository.existsUserByEmail(request.getEmail())) {
            throw new APIException("Email already in use");
        }

        userMapper.partialUserUpdate(request, user);
        userRepository.save(user);

        return userMapper.mapToUserResponse(user);
    }

}
