package com.example.dawnpeace.spota_android.Classes;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("identity_number")
    private String identity_number;
    @SerializedName("email")
    private String email;
    @SerializedName("major_id")
    private int major_id;
    @SerializedName("photo")
    private String photo;
    private String fcmtoken;

    public User(String id, String name, String identity_number, String email, int major_id, String photo, String fcmtoken){
        this.email = email;
        this.id = id;
        this.identity_number = identity_number;
        this.major_id = major_id;
        this.name = name;
        this.photo = photo;
        this.fcmtoken = fcmtoken;
    }





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentity_number() {
        return identity_number;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getPictureUrl(){
        return photo;
    }


}
