package com.example.dawnpeace.spota_android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.dawnpeace.spota_android.Classes.User;
import com.example.dawnpeace.spota_android.Interfaces.UserInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConsultationReviewAdapter extends RecyclerView.Adapter<ConsultationReviewAdapter.MyViewHolder> {
    private Context context;
    private List<Review> reviewList;
    private SharedPrefHelper mSharedPref;
    private User user;

    public ConsultationReviewAdapter(Context context, List<Review> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
        mSharedPref = SharedPrefHelper.getInstance(context);
        user = mSharedPref.getUser();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.review_layout, viewGroup, false);
        final MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(12, 8, 12, 8);
        myViewHolder.cv_review.setLayoutParams(lp);
        myViewHolder.tv_name.setText(reviewList.get(i).getName());
        myViewHolder.tv_identity_number.setText(reviewList.get(i).getIdentity_number());
        myViewHolder.tv_comment.setText(reviewList.get(i).getComment());
        myViewHolder.tv_date.setText(reviewList.get(i).getDate());
        final int position = i;
        if(user.getIdentity_number().equals(reviewList.get(i).getIdentity_number())){
            myViewHolder.tv_sub_menu.setVisibility(View.VISIBLE);
            myViewHolder.tv_sub_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(context,myViewHolder.tv_sub_menu);
                    popupMenu.inflate(R.menu.only_delete_menu);
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            AlertDialog.Builder alert = new AlertDialog.Builder((Activity) context);
                            alert.setTitle("Hapus Komentar");
                            alert.setMessage("Apakah Anda yakin ingin melanjutkan ?");
                            final int review_id = reviewList.get(position).getId();

                            alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteReview(review_id,position);
                                }
                            });
                            alert.show();
                            return  true;
                        }
                    });
                }
            });
        } else {
            myViewHolder.tv_sub_menu.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    private void deleteReview(int id, final int position){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                .client(mSharedPref.getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserInterface userInterface = retrofit.create(UserInterface.class);
        Call<Void> call = userInterface.deleteConsultationReview(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    reviewList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,reviewList.size());
                } else {
                    Toast.makeText(context, "Terjadi Kesalahan !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Terjadi Kesalahan !", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
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
