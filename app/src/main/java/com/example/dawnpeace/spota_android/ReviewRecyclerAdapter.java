package com.example.dawnpeace.spota_android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewRecyclerAdapter.MyViewHolder> {
    Context mContext;
    List<Review> mReview;
    SharedPrefHelper mSharedPref;
    String status;

    public ReviewRecyclerAdapter(Context mContext, List<Review> mReview,@Nullable  String status) {
        this.mContext = mContext;
        this.mReview = mReview;
        mSharedPref = SharedPrefHelper.getInstance(mContext);
        this.status = status;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.review_layout, viewGroup, false);
        final MyViewHolder viewHolder = new MyViewHolder(v);
        if(!status.equals("open")){
            viewHolder.tv_sub_menu.setVisibility(View.GONE);
        }
        viewHolder.tv_sub_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(mContext, viewHolder.tv_sub_menu);
                popup.inflate(R.menu.review_sub_menu);
                if (mReview.get(viewHolder.getAdapterPosition()).getIdentity_number().equals(mSharedPref.getUser().getIdentity_number())) {
                    popup.getMenu().getItem(1).setVisible(true);
                }
                popup.show();
                final String message = mReview.get(viewHolder.getAdapterPosition()).getComment();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int parent_comment_id = mReview.get(viewHolder.getAdapterPosition()).getParent_comment();
                        switch (item.getItemId()) {
                            case R.id.review_reply:
                                Intent intent = new Intent(mContext, ReplyActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("MESSAGE", message);
                                parent_comment_id = parent_comment_id == 0 ? mReview.get(viewHolder.getAdapterPosition()).getId() : parent_comment_id;
                                intent.putExtra("PARENT_ID", parent_comment_id);
                                mContext.startActivity(intent);
                                break;
                            case R.id.review_delete:
                                AlertDialog.Builder alert = new AlertDialog.Builder((Activity) mContext);
                                alert.setTitle("Hapus Komentar");
                                alert.setMessage("Apakah Anda yakin ingin melanjutkan ?");
                                alert.setPositiveButton("YA", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int count = 0;
                                        int comment_id = mReview.get(viewHolder.getAdapterPosition()).getId();
                                        for(int i = viewHolder.getAdapterPosition()+1 ; i < mReview.size() ; i++){
                                            if(mReview.get(i).getParent_comment() == comment_id){
                                                count++;
                                            } else {
                                                break;
                                            }
                                        }

                                        deleteReview(comment_id, viewHolder.getAdapterPosition(),count);
                                    }
                                });
                                alert.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                alert.show();
                                break;
                        }
                        return true;
                    }
                });
            }
        });
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

    private void deleteReview(final int comment_id, final int review_position, final int child_count) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                .client(mSharedPref.getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ReviewInterface review = retrofit.create(ReviewInterface.class);
        Call<Void> call = review.deleteReview(comment_id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    for(int i = 0; i < child_count+1; i++){
                        mReview.remove(review_position);
                        notifyItemRemoved(review_position);
                    }
                    notifyItemRangeChanged(review_position, mReview.size());
                    Toast.makeText(mContext, "Komentar Berhasil dihapus", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(mContext, "Telah terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv_review;
        TextView tv_name;
        TextView tv_identity_number;
        TextView tv_comment;
        TextView tv_date;
        TextView tv_sub_menu;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cv_review = (CardView) itemView.findViewById(R.id.cv_review);
            tv_name = (TextView) itemView.findViewById(R.id.cv_name);
            tv_identity_number = (TextView) itemView.findViewById(R.id.cv_identity_number);
            tv_comment = (TextView) itemView.findViewById(R.id.cv_comment);
            tv_date = (TextView) itemView.findViewById(R.id.cv_date);
            tv_sub_menu = (TextView) itemView.findViewById(R.id.tv_sub_menu);
        }
    }
}
