package com.example.businessservice.client;

import com.example.businessservice.dto.UserResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface UserServiceClient {

    @GetExchange("http://localhost/api/v1/users/{userId}")
    UserResponse getUserDetails(@PathVariable String userId);
}
