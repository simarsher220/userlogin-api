package com.example.challenge.userlogin.service;

import com.example.challenge.userlogin.auth.JwtUtility;
import com.example.challenge.userlogin.constants.ErrorConstants;
import com.example.challenge.userlogin.constants.JwtConstants;
import com.example.challenge.userlogin.dto.request.CreateUserRequest;
import com.example.challenge.userlogin.dto.request.LoginRequest;
import com.example.challenge.userlogin.dto.request.UpdateUserPasswordRequest;
import com.example.challenge.userlogin.dto.response.*;
import com.example.challenge.userlogin.error.exception.LoginException;
import com.example.challenge.userlogin.error.exception.NotFoundException;
import com.example.challenge.userlogin.mapper.UserMapper;
import com.example.challenge.userlogin.model.User;
import com.example.challenge.userlogin.repository.LoginRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class LoginService {

    @Autowired
    private LoginRespository loginRespository;

    public GetUserByIdResponse getUser(UUID userId) throws NotFoundException {
        User user = loginRespository.findByUserId(userId);
        if (user == null) {
            throw new NotFoundException();
        }
        return new GetUserByIdResponse(UserMapper.getUserResponseForUser(user));
    }

    public CreateUserResponse createUser(CreateUserRequest userRequest) throws LoginException {
        User user = null;
        try {
            user = loginRespository.saveAndFlush(UserMapper.getUserForRequest(userRequest));
        }
        catch (Exception ex) {
            throw new LoginException(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return UserMapper.getUserCreateResponse(user);
    }

    public LoginResponse doLogin(LoginRequest loginRequest) throws LoginException {
        User user = loginRespository.findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        if (user == null) {
            throw new LoginException(ErrorConstants.INVALID_USER, HttpStatus.NOT_FOUND);
        }
        String basicAuth = JwtUtility.getBasicAuth(loginRequest.getUsername(), loginRequest.getPassword());
        String accessToken = JwtUtility.getAccessToken(basicAuth);
        return UserMapper.getLoginResponse(accessToken);
    }

    public CreateUserRequest getUserDetails(String authorization) throws LoginException {
        List<String> userDetails = getUserDetailsFromAuth(authorization);
        User user = loginRespository.findByUsernameAndPassword(userDetails.get(0), userDetails.get(1));
        if (user == null) {
            throw new LoginException(ErrorConstants.TOKEN_EXPIRED, HttpStatus.FORBIDDEN);
        }
        return UserMapper.getUserCreateRequestFromUser(user);
    }

    public UpdateUserPasswordResponse updatePassword(UpdateUserPasswordRequest request, String authorization) throws LoginException, Exception {
        try {
            List<String> userDetails = getUserDetailsFromAuth(authorization);
            String oldPassword = userDetails.get(1);
            if (!oldPassword.equals(request.getOldPassword())) {
                throw new LoginException(ErrorConstants.NO_PASSWORD_MATCH, HttpStatus.BAD_REQUEST);
            }
            User user = loginRespository.findByUsernameAndPassword(userDetails.get(0), userDetails.get(1));
            user.setPassword(request.getNewPassword());
            user = loginRespository.saveAndFlush(user);
            if (!user.getPassword().equals(request.getNewPassword())) {
                throw new Exception(ErrorConstants.PASSWORD_UPDATE_FAIL);
            }
            UpdateUserPasswordResponse response = new UpdateUserPasswordResponse();
            return response;
        }
        catch (LoginException ex) {
            throw new LoginException(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private String checkAuth(String authorization) throws LoginException {
        if (StringUtils.isEmpty(authorization)) {
            throw new LoginException(ErrorConstants.AUTH_MISSING, HttpStatus.FORBIDDEN);
        }
        if (!authorization.startsWith(JwtConstants.AUTH_FORMAT)) {
            throw new LoginException(ErrorConstants.AUTH_INVALID, HttpStatus.FORBIDDEN);
        }
        String authToken = authorization.substring(JwtConstants.AUTH_FORMAT.length());
        String tokenStatus = JwtUtility.parseJwtToken(authToken);
        if (tokenStatus.equals(JwtConstants.TOKEN_INVALID_KEY)) {
            throw new LoginException(ErrorConstants.TOKEN_INVALID, HttpStatus.FORBIDDEN);
        }
        byte[] credDecoded = Base64.getDecoder().decode(tokenStatus);
        return new String(credDecoded, StandardCharsets.UTF_8);
    }

    private List<String> getUserDetailsFromAuth(String authorization) throws LoginException {
        String userInfo = checkAuth(authorization);
        List<String> userDetails = Arrays.asList(userInfo.split(":"));
        return userDetails;
    }

}
