package com.example.dawnpeace.spota_android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawnpeace.spota_android.Classes.Review;
import com.example.dawnpeace.spota_android.Interfaces.ReviewInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PreoutlineReviewRecyclerAdapter extends RecyclerView.Adapter<PreoutlineReviewRecyclerAdapter.MyViewHolder> {
    Context mContext;
    List<Review> mReview;
    SharedPrefHelper mSharedPref;

    public PreoutlineReviewRecyclerAdapter(Context mContext, List<Review> mReview) {
        this.mContext = mContext;
        this.mReview = mReview;
        mSharedPref = SharedPrefHelper.getInstance(mContext);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.preoutline_review, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (mReview.get(i).getLevel() > 0) {
            lp.setMargins(100, 8, 8, 8);
            myViewHolder.cv_review.setLayoutParams(lp);
        } else {
            lp.setMargins(12, 8, 12, 8);
            myViewHolder.cv_review.setLayoutParams(lp);
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


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv_review;
        TextView tv_name;
        TextView tv_identity_number;
        TextView tv_comment;
        TextView tv_date;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cv_review = (CardView) itemView.findViewById(R.id.cv_preoutline_review);
            tv_name = (TextView) itemView.findViewById(R.id.tv_preoutline_student_name);
            tv_identity_number = (TextView) itemView.findViewById(R.id.tv_preoutline_identity_number);
            tv_comment = (TextView) itemView.findViewById(R.id.tv_preotuline_comment);
            tv_date = (TextView) itemView.findViewById(R.id.tv_preoutline_date);
        }
    }
}
