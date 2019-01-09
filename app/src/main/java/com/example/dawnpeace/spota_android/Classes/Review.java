package com.example.dawnpeace.spota_android.Classes;


public class Review {
    private int id;
    private String comment;
    private String name;
    private int level;
    private String created_at;
    private String identity_number;
    private int parent_comment;


    public Review(int id, String comment, String name, String created_at, String identity_number, int level, int parent_comment){
        this.id = id;
        this.comment = comment;
        this.name = name;
        this.created_at = created_at;
        this.identity_number = identity_number;
        this.level = level;
        this.parent_comment = parent_comment;
    }

    public int getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return created_at;
    }

    public String getIdentity_number() {
        return identity_number;
    }

    public int getLevel() {
        return level;
    }

    public int getParent_comment() {
        return parent_comment;
    }
}
