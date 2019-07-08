package com.example.challenge.userlogin.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetUserByIdResponse {

    private GetUserResponse response;

    public GetUserByIdResponse(GetUserResponse response) {
        this.response = response;
    }

    @JsonProperty("data")
    public GetUserResponse getResponse() {
        return response;
    }
}
