package com.example.challenge.userlogin.controller;

import com.example.challenge.userlogin.dto.request.CreateUserRequest;
import com.example.challenge.userlogin.dto.request.LoginRequest;
import com.example.challenge.userlogin.dto.request.UpdateUserPasswordRequest;
import com.example.challenge.userlogin.dto.response.*;
import com.example.challenge.userlogin.error.exception.LoginException;
import com.example.challenge.userlogin.error.exception.NotFoundException;
import com.example.challenge.userlogin.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/api/user/{userId}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GetUserByIdResponse getUser(@PathVariable UUID userId) throws NotFoundException {
        return loginService.getUser(userId);
    }

    @RequestMapping(value = "/api/users/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponse createUser(@RequestBody CreateUserRequest userRequest) throws LoginException {
        return loginService.createUser(userRequest);
    }

    @RequestMapping(value = "/api/users/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponse login(@RequestBody LoginRequest loginRequest) throws LoginException {
        return loginService.doLogin(loginRequest);
    }

    @RequestMapping(value = "/auth/secured", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CreateUserRequest getUserDetails(@RequestHeader("Authorization") String authorization) throws LoginException {
        return loginService.getUserDetails(authorization);
    }

    @RequestMapping(value = "/api/user/updatePassword", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UpdateUserPasswordResponse updatePassword(@RequestBody UpdateUserPasswordRequest request, @RequestHeader("Authorization") String authorization) throws LoginException, Exception {
        return loginService.updatePassword(request, authorization);
    }

}
