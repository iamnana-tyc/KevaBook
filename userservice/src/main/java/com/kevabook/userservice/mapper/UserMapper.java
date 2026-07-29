package com.kevabook.userservice.mapper;

import com.kevabook.userservice.domain.User;
import com.kevabook.userservice.dto.CreateUserRequest;
import com.kevabook.userservice.dto.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public User mapToUser(CreateUserRequest request){
        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(request.getRole());

        return user;
    }

    public UserResponse mapToUserResponse(User user){
        UserResponse userResponse = new UserResponse();

        userResponse.setUserId(user.getUserId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setRole(user.getRole());

        return userResponse;
    }
}
