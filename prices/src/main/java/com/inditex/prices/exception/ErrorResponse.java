package com.inditex.prices.exception;

public class ErrorResponse {
    private String message;
    private String details;
    private int status;

    public ErrorResponse(String message, String details, int status) {
        this.message = message;
        this.details = details;
        this.status = status;
    }

    // Getters
    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public int getStatus() {
        return status;
    }
}