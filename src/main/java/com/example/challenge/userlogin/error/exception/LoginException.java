package com.example.challenge.userlogin.error.exception;

import org.springframework.http.HttpStatus;

public class LoginException extends Exception {

    private HttpStatus status;

    public LoginException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
