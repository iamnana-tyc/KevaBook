package com.example.userservice.dto;

import com.example.userservice.model.UserRole;
import lombok.Data;

@Data
public class UserResponse {
    private String userId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String imageUrl;
    private UserRole userRole;
}
