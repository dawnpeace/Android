package com.example.dawnpeace.spota_android.Classes;

public class Approval {
    private String name;
    private String approval;
    private String date;

    public Approval(String name, String approval, String date) {
        this.name = name;
        this.approval = approval;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getApproval() {
        String approve = "";
        switch(approval){
            case "approve":
                approve = name+" menyutujui draft Anda";
                break;
            case "disapprove":
                approve = name+" menolak draft Anda";
                break;
        }
        return approve;
    }

    public String getDate() {
        return date;
    }

}
