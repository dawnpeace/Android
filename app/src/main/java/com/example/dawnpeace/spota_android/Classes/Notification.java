package com.example.dawnpeace.spota_android.Classes;

public class Notification {
    private int id;
    private String title;
    private String messages;
    private String created_at;

    public Notification(int id, String title, String messages, String created_at) {
        this.id = id;
        this.title = title;
        this.messages = messages;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessages() {
        return messages;
    }

    public String getDate() {
        return created_at;
    }
}
