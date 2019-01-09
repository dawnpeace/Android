package com.example.dawnpeace.spota_android.Classes;

import java.util.List;

public class ViewPreoutline {
    private PreoutlineDraft preoutline;
    private List<Review> reviews;

    public ViewPreoutline(PreoutlineDraft preoutline, List<Review> reviews) {
        this.preoutline = preoutline;
        this.reviews = reviews;
    }

    public PreoutlineDraft getPreoutlineDraft() {
        return preoutline;
    }

    public List<Review> getReviews() {
        return reviews;
    }
}

