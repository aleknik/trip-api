package com.aleknik.tripapi.model.dto;

/**
 * Represents error message sent by API.
 */
public class ErrorResponse {

    /**
     * Error message.
     */
    private String error;

    public ErrorResponse() {
    }

    public ErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

