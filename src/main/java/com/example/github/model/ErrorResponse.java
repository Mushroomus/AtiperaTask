package com.example.github.model;

import lombok.Data;
@Data
public class ErrorResponse {

    private int status;

    private String message;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}