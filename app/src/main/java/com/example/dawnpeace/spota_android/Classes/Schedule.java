package com.example.dawnpeace.spota_android.Classes;

public class Schedule {
    private String title;
    private String location;
    private String date;
    private String time;
    private int id;
    private int student_id;
    private String name;
    private String identity_number;
    private String type;

    public Schedule(String title, String location, String date, String time, int id, int student_id, String name, String identity_number, String type) {
        this.title = title;
        this.location = location;
        this.date = date;
        this.time = time;
        this.id = id;
        this.student_id = student_id;
        this.name = name;
        this.identity_number = identity_number;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getId() {
        return id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public String getName() {
        return name;
    }

    public String getIdentity_number() {
        return identity_number;
    }

    public String getType() {
        return type;
    }
}
