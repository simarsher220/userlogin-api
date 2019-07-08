package com.example.challenge.userlogin.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateUserPasswordResponse {

    private String status;

    public UpdateUserPasswordResponse() {
        this.status = "success";
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }
}
