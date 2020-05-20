package com.lemon.utils;

public enum ResultStatus {

    SUCCESS(2000, "Success"),
    WARN(3000, "Warn"),
    NOT_FOUND(4000, "Not Found"),
    ERROR(5000, "Error");

    private final int code;
    private final String message;

    ResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
