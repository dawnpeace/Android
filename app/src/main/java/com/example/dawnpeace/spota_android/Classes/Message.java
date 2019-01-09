package com.example.dawnpeace.spota_android.Classes;

public class Message {
    private String error_message;
    private boolean success;
    private User user;

    public Message(String error_message, User user,Boolean success) {
        this.error_message = error_message;
        this.user = user;
        this.success = success;
    }

    public String getError_message() {
        return error_message != null ? error_message : "";
    }

    public User getUser() {
        return user;
    }

    public boolean isSuccess() {
        return success;
    }
}
