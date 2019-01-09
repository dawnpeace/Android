package com.example.dawnpeace.spota_android.Classes;

public class ConsultationStatus {
    private boolean first_status;
    private boolean second_status;
    private boolean third_status;
    private boolean fourth_status;
    private boolean fifth_status;
    private String first_last_commented;
    private String second_last_commented;
    private String third_last_commented;
    private String fourth_last_commented;
    private String fifth_last_commented;
    private int first_comment_count;
    private int second_comment_count;
    private int third_comment_count;
    private int fourth_comment_count;
    private int fifth_comment_count;

    public ConsultationStatus(boolean first_status, boolean second_status, boolean third_status, boolean fourth_status, boolean fifth_status, String first_last_commented, String second_last_commented, String third_last_commented, String fourth_last_commented, String fifth_last_commented, int first_comment_count, int second_comment_count, int third_comment_count, int fourth_comment_count, int fifth_comment_count) {
        this.first_status = first_status;
        this.second_status = second_status;
        this.third_status = third_status;
        this.fourth_status = fourth_status;
        this.fifth_status = fifth_status;
        this.first_last_commented = first_last_commented;
        this.second_last_commented = second_last_commented;
        this.third_last_commented = third_last_commented;
        this.fourth_last_commented = fourth_last_commented;
        this.fifth_last_commented = fifth_last_commented;
        this.first_comment_count = first_comment_count;
        this.second_comment_count = second_comment_count;
        this.third_comment_count = third_comment_count;
        this.fourth_comment_count = fourth_comment_count;
        this.fifth_comment_count = fifth_comment_count;
    }


    public boolean isFirst_status() {
        return first_status;
    }

    public boolean isSecond_status() {
        return second_status;
    }

    public boolean isThird_status() {
        return third_status;
    }

    public boolean isFourth_status() {
        return fourth_status;
    }

    public boolean isFifth_status() {
        return fifth_status;
    }

    public String getFirst_last_commented() {
        return first_last_commented;
    }

    public String getSecond_last_commented() {
        return second_last_commented;
    }

    public String getThird_last_commented() {
        return third_last_commented;
    }

    public String getFourth_last_commented() {
        return fourth_last_commented;
    }

    public String getFifth_last_commented() {
        return fifth_last_commented;
    }

    public int getFirst_comment_count() {
        return first_comment_count;
    }

    public int getSecond_comment_count() {
        return second_comment_count;
    }

    public int getThird_comment_count() {
        return third_comment_count;
    }

    public int getFourth_comment_count() {
        return fourth_comment_count;
    }

    public int getFifth_comment_count() {
        return fifth_comment_count;
    }
}

