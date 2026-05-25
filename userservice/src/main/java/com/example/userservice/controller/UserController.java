package com.example.userservice.controller;

import com.example.userservice.config.AppConstants;
import com.example.userservice.dto.UserPageResponse;
import com.example.userservice.dto.UserRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody UserRequest request
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String userId){
        return ResponseEntity.ok()
                .body(userService.getUserById(userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@NotBlank @PathVariable String userId){
        userService.deleteUserById(userId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<UserPageResponse> getAllUsers(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_USERS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortOrder)
    {
        return ResponseEntity.ok(userService.getAllUsers(pageNumber, pageSize, sortBy, sortOrder));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponse> partialUpdateUser(
            @PathVariable String userId,
            @RequestBody UserRequest request){
        return ResponseEntity.ok()
                .body(userService.partialUpdateUser(userId, request));
    }

}
