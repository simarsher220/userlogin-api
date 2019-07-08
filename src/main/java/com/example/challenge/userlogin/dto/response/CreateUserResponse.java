package com.example.challenge.userlogin.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CreateUserResponse {

    private UUID id;

    @JsonProperty("id")
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
