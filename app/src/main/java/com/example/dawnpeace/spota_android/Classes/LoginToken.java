package com.example.dawnpeace.spota_android.Classes;


public class LoginToken {

    private String access_token;
    private String token_type;
    private int error;
    private String type;
    private String status;

    public LoginToken(String access_token, String token_type, int error, String type, String status) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.error = error;
        this.type = type;
        this.status = status;
    }

    public String getToken_type() {
        return token_type;
    }


    public String getStatus() {
        return status;
    }

    public String getAccess_token() {
        return access_token;

    }

    public String getType() {
        return type;
    }

    public int getError() {
        return error;
    }
}
