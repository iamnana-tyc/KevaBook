package com.example.userservice.mapper;

import com.example.userservice.dto.UserRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapToUser(UserRequest request){
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setImageUrl(request.getImageUrl());

        return user;
    }

    public UserResponse mapToUserResponse(User user){
        if (user == null)
            return null;

        UserResponse response = new UserResponse();
        response.setUserId(user.getUserId());
        response.setEmail(user.getEmail());
        response.setPassword(user.getPassword());
        response.setFirstName(user.getFirstName());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setLastName(user.getLastName());
        response.setImageUrl(user.getImageUrl());
        response.setUserRole(user.getUserRole());

        return response;
    }

    public void partialUserUpdate(UserRequest request, User user){
        if (request.getEmail() != null)
            user.setEmail(request.getEmail());

        if (request.getPassword() != null)
            user.setPassword(request.getPassword());

        if (request.getFirstName() != null)
            user.setFirstName(request.getFirstName());

        if (request.getLastName() != null)
            user.setLastName(request.getLastName());

        if (request.getPhoneNumber() != null)
            user.setPhoneNumber(request.getPhoneNumber());

        if (request.getImageUrl() != null)
            user.setImageUrl(request.getImageUrl());
    }
}
