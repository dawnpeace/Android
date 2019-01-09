package com.example.dawnpeace.spota_android.Classes;

import java.sql.Timestamp;

public class Draft {
    private String title;
    private int upvote;
    private int downvote;
    private String student_name;
    private String date;

    public Draft(String title, int upvote, int downvote, String student_name, String date){
        this.title = title;
        this.student_name = student_name;
        this.upvote = upvote;
        this.downvote = downvote;
        this.date = date;
    }

    public int getDownvote() {
        return downvote;
    }

    public int getUpvote() {
        return upvote;
    }

    public String get_date() {
        return date;
    }

    public String getStudent_name() {
        return student_name;
    }

    public String get_title() {
        return title;
    }

}

