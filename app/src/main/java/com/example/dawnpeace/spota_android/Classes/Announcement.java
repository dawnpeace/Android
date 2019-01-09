package com.example.dawnpeace.spota_android.Classes;

import com.google.gson.annotations.SerializedName;

public class Announcement {
    private int id;
    private boolean is_read;
    private String title;
    private String content;
    @SerializedName("sender")
    private String sender_name;
    private String date;
    public Announcement(int id, boolean is_read, String title, String content, String sender_name, String date){
        this.id = id;
        this.is_read = is_read;
        this.title = title;
        this.content = content;
        this.sender_name = sender_name;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public boolean isRead(){
        return is_read;
    }

    public boolean read(){
        return this.is_read = true;
    }

    public String getSender_name() {
        return sender_name;
    }

    public String getDate() {
        return date;
    }
}
