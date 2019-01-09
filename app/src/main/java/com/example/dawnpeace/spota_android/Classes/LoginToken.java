package com.example.dawnpeace.spota_android.Classes;

import com.google.gson.annotations.SerializedName;

public class LoginToken {
        @SerializedName("access_token")
        private String access_token;
        @SerializedName("token_type")
        private String token_type;
        @SerializedName("error")
        private int error;
        @SerializedName("type")
        private Character type;

        public String getToken_type() {
            return token_type;
        }


        public String getAccess_token() {
            try{
                return access_token;
            } catch (NullPointerException e){
                return access_token = "";
            }
        }

        public Character getType() {
            return type;
        }

        public int getError() {
            return error;
        }
}
