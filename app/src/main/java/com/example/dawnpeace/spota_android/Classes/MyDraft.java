package com.example.dawnpeace.spota_android.Classes;

import java.util.List;

public class MyDraft {
    private String preoutline_title;
    private String preoutline_status;
    private int approval_count;
    private int disapproval_count;
    private int review_count;
    private List<Approval> approval_list;

    public MyDraft(String preoutline_title, int approval_count, int disapproval_count, int review_count,String preoutline_status, List<Approval> approval_list) {
        this.preoutline_title = preoutline_title;
        this.approval_count = approval_count;
        this.disapproval_count = disapproval_count;
        this.review_count = review_count;
        this.preoutline_status = preoutline_status;
        this.approval_list = approval_list;
    }

    public String getPreoutline_title() {
        return preoutline_title;
    }

    public int getApproval_count() {
        return approval_count;
    }

    public int getDisapproval_count() {
        return disapproval_count;
    }

    public String getPreoutline_status() {
        return preoutline_status;
    }

    public int getReview_count() {
        return review_count;
    }

    public List<Approval> getApproval_list() {
        return approval_list;
    }

}
