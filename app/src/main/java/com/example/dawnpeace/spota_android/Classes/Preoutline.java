package com.example.dawnpeace.spota_android.Classes;

public class Preoutline {
    private int preoutline_id;
    private String title;
    private String name;
    private String status;
    private int approval_count;
    private int disapproval_count;
    private int review_count;
    private String identity_number;


    public Preoutline(int preoutline_id, String name, String title, String status, String expertise, int approval_count, int disapproval_count, int review_count, String identity_number) {
        this.preoutline_id = preoutline_id;
        this.title = title;
        this.name = name;
        this.status = status;
        this.approval_count= approval_count;
        this.disapproval_count = disapproval_count;
        this.review_count = review_count;
        this.identity_number = identity_number;

    }

    public int getId() {
        return preoutline_id;
    }

    public String getIdentity_number() {
        return identity_number;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        switch (status){
            case "approved":
                return "Diterima";
            case "open":
                return "Terbuka";
            case "closed":
                return "Gugur";
            case "rejected":
                return "Ditolak";
            default:
                return status;
        }
    }


    public String getName() {
        return name;
    }

    public int getReview_count() {
        return review_count;
    }

    public int getApprove_count() {
        return approval_count;
    }

    public int getDisapprove_count() {
        return disapproval_count;
    }
}

