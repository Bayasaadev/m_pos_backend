package com.bank.pos.common;

public class BadRequestException extends RuntimeException {
    private final String responseCode;

    public BadRequestException(String message) {
        super(message);
        this.responseCode = null;
    }

    public BadRequestException(String message, String responseCode) {
        super(message);
        this.responseCode = responseCode;
    }

    public String getResponseCode() {
        return responseCode;
    }
}


