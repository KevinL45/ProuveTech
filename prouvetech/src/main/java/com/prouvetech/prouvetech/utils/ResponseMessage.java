package com.prouvetech.prouvetech.utils;

import java.util.List;

public class ResponseMessage {
    private boolean success;
    private String message;
    private List<String> roles;

    public ResponseMessage() {
    }

    public ResponseMessage(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ResponseMessage(boolean success, String message, List<String> roles) {
        this.success = success;
        this.message = message;
        this.roles = roles;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getRoles() {
        return roles;
    }
}
