package com.example.businessservice.client;

import com.example.businessservice.dto.UserResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("http://userservice/api/v1/users")
public interface UserServiceClient {

    @GetExchange("/{userId}")
    UserResponse getUserDetails(@PathVariable String userId);
}
