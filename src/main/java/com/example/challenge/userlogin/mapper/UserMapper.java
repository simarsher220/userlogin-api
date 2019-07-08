package com.example.challenge.userlogin.mapper;

import com.example.challenge.userlogin.dto.request.CreateUserRequest;
import com.example.challenge.userlogin.dto.response.CreateUserResponse;
import com.example.challenge.userlogin.dto.response.GetUserResponse;
import com.example.challenge.userlogin.dto.response.LoginResponse;
import com.example.challenge.userlogin.model.User;

import java.util.UUID;

public class UserMapper {

    public static GetUserResponse getUserResponseForUser(User user) {
        GetUserResponse userResponse = new GetUserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setUsername(user.getUsername());
        return userResponse;
    }

    public static User getUserForRequest(CreateUserRequest userRequest) {
        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setEmail(userRequest.getEmail());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPassword(userRequest.getPassword());
        user.setUsername(userRequest.getUsername());
        return user;
    }

    public static CreateUserResponse getUserCreateResponse(User user) {
        CreateUserResponse userResponse = new CreateUserResponse();
        userResponse.setId(user.getUserId());
        return userResponse;
    }

    public static LoginResponse getLoginResponse(String accessToken) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(accessToken);
        return loginResponse;
    }

    public static CreateUserRequest getUserCreateRequestFromUser(User user) {
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setEmail(user.getEmail());
        userRequest.setPassword(user.getPassword());
        userRequest.setFirstName(user.getFirstName());
        userRequest.setLastName(user.getLastName());
        userRequest.setUsername(user.getUsername());
        return userRequest;
    }
}
