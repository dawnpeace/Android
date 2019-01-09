package com.example.dawnpeace.spota_android;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dawnpeace.spota_android.Classes.Review;

import java.util.List;

public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewRecyclerAdapter.MyViewHolder> {
    Context mContext;
    List<Review> mReview;

    public ReviewRecyclerAdapter(Context mContext, List<Review> mReview) {
        this.mContext = mContext;
        this.mReview = mReview;
    }

    private static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.review_layout,viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(v);



        return viewHolder;
    }
    

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        if(mReview.get(i).getLevel() > 0){
            myViewHolder.cv_review.setContentPadding(100,5,5,5);
        }
        myViewHolder.tv_name.setText(mReview.get(i).getName());
        myViewHolder.tv_identity_number.setText(mReview.get(i).getIdentity_number());
        myViewHolder.tv_comment.setText(mReview.get(i).getComment());
        myViewHolder.tv_date.setText(mReview.get(i).getDate());
    }

    @Override
    public int getItemCount() {
        return mReview.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cv_review;
        TextView tv_name;
        TextView tv_identity_number;
        TextView tv_comment;
        TextView tv_date;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cv_review = (CardView) itemView.findViewById(R.id.cv_review);
            tv_name = (TextView) itemView.findViewById(R.id.cv_name);
            tv_identity_number = (TextView) itemView.findViewById(R.id.cv_identity_number);
            tv_comment = (TextView) itemView.findViewById(R.id.cv_comment);
            tv_date = (TextView) itemView.findViewById(R.id.cv_date);
        }
    }
}
