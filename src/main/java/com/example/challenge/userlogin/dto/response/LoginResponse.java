package com.example.challenge.userlogin.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {

    private String accessToken;

    @JsonProperty("access_token")
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
