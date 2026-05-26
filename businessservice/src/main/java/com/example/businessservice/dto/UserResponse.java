package com.example.businessservice.dto;


import com.example.businessservice.model.UserRole;
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
