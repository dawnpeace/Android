package com.example.dawnpeace.spota_android;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawnpeace.spota_android.Classes.Announcement;
import com.example.dawnpeace.spota_android.Interfaces.AnnouncementInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecyclerViewAdapater extends RecyclerView.Adapter<RecyclerViewAdapater.MyViewHolder> {
    private Context mContext;
    private List<Announcement> mAnnouncement;
    private SharedPrefHelper mSharedPref;

    public RecyclerViewAdapater(Context mContext, List<Announcement> mAnnouncementList) {
        this.mContext = mContext;
        this.mAnnouncement = mAnnouncementList;
        mSharedPref = SharedPrefHelper.getInstance(mContext);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.announcement, viewGroup, false);
        final MyViewHolder vHolder = new MyViewHolder(v);


        vHolder.rl_ann_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder logoutAlert = new AlertDialog.Builder(mContext);
                TextView textView = new TextView(mContext);
                textView.setText(Html.fromHtml(mAnnouncement.get(vHolder.getAdapterPosition()).getContent(), null, new MyTagHandler()));
                textView.setPadding(20, 20, 20, 20);
                textView.setMinHeight(500);
                textView.setMovementMethod(LinkMovementMethod.getInstance());
                textView.setMinWidth(750);
                logoutAlert.setView(textView);
                logoutAlert.setCancelable(true).setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();

                if (!mAnnouncement.get(vHolder.getAdapterPosition()).isRead()) {

                    Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(mSharedPref.getInterceptor())
                            .build();
                    AnnouncementInterface announcement = retrofit.create(AnnouncementInterface.class);
                    Call<Void> call = announcement.getAnnouncement(mAnnouncement.get(vHolder.getAdapterPosition()).getId());
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                mAnnouncement.get(vHolder.getAdapterPosition()).read();
                                vHolder.rl_ann_parent.setBackgroundResource(R.color.grayShade400);
                                vHolder.tv_title.setTextColor(Color.parseColor("#9E9E9E"));
                                vHolder.tv_date.setTextColor(Color.parseColor("#9E9E9E"));
                                vHolder.tv_name.setTextColor(Color.parseColor("#9E9E9E"));

                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(mContext, "Tidak dapat menghubungi server", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

//                mDialog.show();
            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        if (!mAnnouncement.get(i).isRead()) {
            myViewHolder.rl_ann_parent.setBackgroundResource(R.color.grayShade200);
            myViewHolder.tv_title.setTextColor(Color.parseColor("#E0E0E0"));
            myViewHolder.tv_date.setTextColor(Color.parseColor("#E0E0E0"));
            myViewHolder.tv_name.setTextColor(Color.parseColor("#E0E0E0"));
        }
        myViewHolder.tv_title.setText(mAnnouncement.get(i).getTitle());
        myViewHolder.tv_name.setText(mAnnouncement.get(i).getSender_name());
        myViewHolder.tv_date.setText(mAnnouncement.get(i).getDate());
    }

    @Override
    public int getItemCount() {
        return mAnnouncement.size();
    }


    protected static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_name;
        private TextView tv_date;
        private RelativeLayout rl_ann_parent;

        protected MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            rl_ann_parent = (RelativeLayout) itemView.findViewById((R.id.rl_ann_parent_layout));
        }
    }
}
