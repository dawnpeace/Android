package com.example.dawnpeace.spota_android.Classes;

public class PreoutlineDraft {
    private String title;
    private String description;
    private String file_url;
    private String name;
    private String identity_number;
    private int approval_count;
    private int disapproval_count;
    private String status;

    public PreoutlineDraft(String title, String description, String file_url, String name, String identity_number, int approval_count, int disapproval_count, String status) {
        this.title = title;
        this.description = description;
        this.file_url = file_url;
        this.name = name;
        this.identity_number = identity_number;
        this.approval_count = approval_count;
        this.disapproval_count = disapproval_count;
        this.status = status;
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

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getFile_url() {
        return file_url;
    }

    public String getName() {
        return name;
    }

    public String getIdentity_number() {
        return identity_number;
    }

    public int getApproval_count() {
        return approval_count;
    }

    public int getDisapproval_count() {
        return disapproval_count;
    }
}
