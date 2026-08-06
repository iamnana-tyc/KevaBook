package com.kevabook.userservice.controller;

import com.kevabook.userservice.domain.Role;
import com.kevabook.userservice.dto.CreateUserRequest;
import com.kevabook.userservice.dto.PartialUpdateUserRequest;
import com.kevabook.userservice.dto.UserResponse;
import com.kevabook.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
public class UserControllerTest {
    @MockitoBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void createUserShouldReturnSuccess() throws Exception {
        // Arrange
        CreateUserRequest request = createUserRequest();
        UserResponse response = createUserResponse();

        when(userService.createUser(request)).thenReturn(response);

        // Act and Assert
        mockMvc.perform(
                        post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(response.getUserId()))
                .andExpect(jsonPath("$.email").value(response.getEmail()));

        verify(userService).createUser(request);
    }

    @Test
    void getAllUsersShouldReturnUsersWhenRequestIsSuccessful() throws Exception {
        // Arrange
        UserResponse userResponse = createUserResponse();
        Page<UserResponse> page = new PageImpl<UserResponse>(List.of(userResponse));

        when(userService.getAllUsers(0,5)).thenReturn(page);

        // Act and Assert
        mockMvc.perform(
                get("/api/v1/users")
                        .param("pageNumber","0")
                        .param("pageSize","5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName")
                        .value("Nana"));

        verify(userService).getAllUsers(0,5);
    }

    @Test
    void getUserShouldReturnUserWhenUserExists() throws Exception {
        // Arrange
        Long userId = 1L;
        UserResponse response = createUserResponse();

        when(userService.getUser(userId)).thenReturn(response);

        // Act and Assert
        mockMvc.perform(
                get("/api/v1/users/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(response.getUserId()))
                .andExpect(jsonPath("$.email").value(response.getEmail()))
                .andExpect(jsonPath("$.role").value(response.getRole().name()));

        verify(userService).getUser(userId);
    }

    @Test
    void partialUpdateUserShouldUpdateSpecifiedFieldsSuccessfully()  throws Exception {
        // Arrange
        Long userId = 1L;
        PartialUpdateUserRequest request = createPartialUpdateUserRequest();
        UserResponse response = createUserResponse();

        when(userService.partialUpdateUser(userId, request)).thenReturn(response);

        // Act and Assert
        mockMvc.perform(
                patch("/api/v1/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(response.getFirstName()))
                .andExpect(jsonPath("$.email").value(response.getEmail()));

    }

    @Test
    void deleteUserWhenUserExist() throws Exception {
        // Arrange
        Long userId = 1L;

        // Act and Assert
       mockMvc.perform(
               delete("/api/v1/users/{userId}", userId))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message").value("Successfully deleted user"));

       verify(userService).deleteUser(userId);
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

    private UserResponse createUserResponse() {
        UserResponse response = new UserResponse();
        response.setUserId(1L);
        response.setFirstName("Nana");
        response.setLastName("Appiah");
        response.setEmail("nana@test.com");
        response.setPhoneNumber("+1123456789");
        response.setRole(Role.ADMIN);

        return response;
    }

    private PartialUpdateUserRequest createPartialUpdateUserRequest() {
        PartialUpdateUserRequest request = new PartialUpdateUserRequest();
        request.setFirstName("Evans");
        request.setEmail("evans@test.com");

        return request;
    }
}
