package com.example.dawnpeace.spota_android;

import com.google.gson.annotations.SerializedName;

import java.io.File;

public class User {

    public User(String id, String student, String name, String identity_number, String email, int major_id, String photo){
        this.email = email;
        this.id = id;
        this.identity_number = identity_number;
        this.major_id = major_id;
        this.name = name;
        this.student = student;
        this.photo = photo;
    }

    @SerializedName("id")
    private String id;
    @SerializedName("student")
    private String student;
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


    public String getStudent_id() {
        return student;
    }
}
