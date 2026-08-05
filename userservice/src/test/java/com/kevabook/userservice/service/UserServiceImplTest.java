package com.kevabook.userservice.service;

import com.kevabook.userservice.domain.Role;
import com.kevabook.userservice.domain.User;
import com.kevabook.userservice.dto.CreateUserRequest;
import com.kevabook.userservice.dto.PartialUpdateUserRequest;
import com.kevabook.userservice.dto.UserResponse;
import com.kevabook.userservice.exception.ResourceNotFoundException;
import com.kevabook.userservice.mapper.UserMapper;
import com.kevabook.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUserShouldReturnUserResponseWhenRequestIsValid() {
        // Arrange
        CreateUserRequest request = createUserRequest();
        User user = createUnsavedUser();
        User savedUser = createSavedUser();
        UserResponse response = savedUserResponse();


        when(userMapper.mapToUser(request)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.mapToUserResponse(savedUser)).thenReturn(response);

        // Act
        UserResponse result = userService.createUser(request);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getUserId());
        assertEquals("Nana", result.getFirstName());
        assertEquals("ADMIN", result.getRole().name());

        verify(userMapper).mapToUser(request);
        verify(userRepository).save(user);
        verify(userMapper).mapToUserResponse(savedUser);
    }

    @Test
    void getAllUsersShouldReturnPagedUsersWhenUsersExist() {
        // Arrange
        int pageNumber = 0;
        int pageSize = 5;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        User user1 = createSavedUser();
        User user2 = createSavedUser();
        user2.setUserId(2L);
        user2.setFirstName("John");
        user2.setLastName("Branny");
        user2.setEmail("john@gmail.com");

        Page<User> userPage = new PageImpl<>(List.of(user1, user2));

        UserResponse response1 = savedUserResponse();
        UserResponse response2 = savedUserResponse();
        response2.setUserId(2L);
        response2.setFirstName("John");
        response2.setLastName("Branny");
        response2.setEmail("john@gmail.com");

        when(userRepository.findAll(pageable)).thenReturn(userPage);
        when(userMapper.mapToUserResponse(user1)).thenReturn(response1);
        when(userMapper.mapToUserResponse(user2)).thenReturn(response2);

        // Act
        Page<UserResponse> result = userService.getAllUsers(pageNumber, pageSize);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());

        assertEquals("Nana", result.getContent().get(0).getFirstName());
        assertEquals("Branny", result.getContent().get(1).getLastName());

        verify(userRepository).findAll(pageable);
        verify(userMapper).mapToUserResponse(user1);
        verify(userMapper).mapToUserResponse(user2);
    }

    @Test
    void getUserShouldReturnUserResponseWhenUserExists() {
        // Arrange
        Long userId = 1L;
        User savedUser = createSavedUser();
        UserResponse response = savedUserResponse();

        when(userRepository.findById(userId)).thenReturn(Optional.of(savedUser));
        when(userMapper.mapToUserResponse(savedUser)).thenReturn(response);

        // Act
        UserResponse result = userService.getUser(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals("nana@test.com", result.getEmail());

        verify(userRepository).findById(userId);
        verify(userMapper).mapToUserResponse(savedUser);
    }

    @Test
    void getUserShouldThrowResourceNotFoundExceptionWhenUserDoesNotExist(){
        // Arrange
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        ResourceNotFoundException exception =
                assertThrows(ResourceNotFoundException.class,
                () -> userService.getUser(userId));


        assertEquals( "User not found with id: 1 ", exception.getMessage());

        verify(userRepository).findById(userId);
        verify(userMapper, never()).mapToUserResponse(any());
    }


    @Test
    void partialUpdateUserShouldUpdateSpecifiedFieldsWhenRequestIsValid() {
        // Arrange
        Long userId = 1L;
        PartialUpdateUserRequest request = createPartialUpdateUserRequest();
        User existingUser = createSavedUser();
        UserResponse response = savedUserResponse();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);
        when(userMapper.mapToUserResponse(existingUser)).thenReturn(response);

        // Act
        UserResponse result = userService.partialUpdateUser(userId, request);

        // Assert
        assertNotNull(result);
        assertEquals("Evans", existingUser.getFirstName());
        assertEquals("evans@test.com", existingUser.getEmail());
        assertEquals("Appiah", existingUser.getLastName());
        assertEquals("+1123456789", existingUser.getPhoneNumber());
    }

    @Test
    void deleteUserWhenUserExists() {
        // Arrange
        Long userId = 1L;
        User savedUser = createSavedUser();

        when(userRepository.findById(userId)).thenReturn(Optional.of(savedUser));

        // Act
        userService.deleteUser(userId);


        // Arrange
        verify(userRepository).findById(userId);
        verify(userRepository).delete(savedUser);
    }

    @Test
    void deleteUserShouldThrowResourceNotFoundExceptionWhenUserDoesNotExist() {
        // Arrange
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, ()-> userService.deleteUser(userId));

        verify(userRepository).findById(userId);
        verify(userRepository, never()).delete(any(User.class));
    }

    private CreateUserRequest createUserRequest() {
        CreateUserRequest request = new CreateUserRequest();
        request.setFirstName("Nana");
        request.setLastName("Appiah");
        request.setEmail("nana@test.com");
        request.setPhoneNumber("+1123456789");
        request.setRole(Role.ADMIN);

        return request;
    }

    private User createUnsavedUser() {
        User user = new User();
        user.setFirstName("Nana");
        user.setLastName("Appiah");
        user.setEmail("nana@test.com");
        user.setPhoneNumber("+1123456789");
        user.setRole(Role.ADMIN);

        return user;
    }

    private User createSavedUser() {
        User savedUser = new User();
        savedUser.setUserId(1L);
        savedUser.setFirstName("Nana");
        savedUser.setLastName("Appiah");
        savedUser.setEmail("nana@test.com");
        savedUser.setPhoneNumber("+1123456789");
        savedUser.setRole(Role.ADMIN);

        return savedUser;
    }

    private UserResponse savedUserResponse() {
        UserResponse response = new UserResponse();
        response.setUserId(1L);
        response.setFirstName("Nana");
        response.setLastName("Appiah");
        response.setEmail("nana@test.com");
        response.setPhoneNumber("+1123456789");
        response.setRole(Role.ADMIN);

        return response;
    }

    private PartialUpdateUserRequest  createPartialUpdateUserRequest() {
        PartialUpdateUserRequest request = new PartialUpdateUserRequest();
        request.setFirstName("Evans");
        request.setEmail("evans@test.com");

        return request;
    }
}