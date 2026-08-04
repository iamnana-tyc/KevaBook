package com.kevabook.userservice.controller;

import com.kevabook.userservice.dto.*;
import com.kevabook.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Value("${app.pagination.default-page-number}")
    private final String defaultPageNumber;

    @Value("${app.pagination.default-page-size}")
    private final String defaultPageSize;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        UserResponse user = userService.createUser(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(user);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @RequestParam(name = "pageNumber", defaultValue = "${app.pagination.default-page-number}" , required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "${app.pagination.default-page-size}", required = false) Integer pageSize

    ) {
        Page<UserResponse> users = userService.getAllUsers(pageNumber, pageSize);

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("userId") Long userId) {
        UserResponse user = userService.getUser(userId);

        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponse> partialUpdateUser(
            @PathVariable("userId") Long userId,
            @RequestBody PartialUpdateUserRequest request){
        UserResponse updateUser = userService.partialUpdateUser(userId, request);

        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<APIResponse> deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);

        return ResponseEntity.ok(new APIResponse("Successfully deleted user", true));
    }
}
