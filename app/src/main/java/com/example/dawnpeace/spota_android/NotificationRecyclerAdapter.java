package com.example.dawnpeace.spota_android;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dawnpeace.spota_android.Classes.Notification;

import java.util.List;

public class NotificationRecyclerAdapter extends RecyclerView.Adapter<NotificationRecyclerAdapter.MyViewHolder> {
    private List<Notification> mNotif;
    private Context mContext;

    public NotificationRecyclerAdapter(List<Notification> mNotif, Context mContext) {
        this.mNotif = mNotif;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.notifications,viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_title.setText(mNotif.get(i).getTitle());
        myViewHolder.tv_message.setText(mNotif.get(i).getMessages());
        myViewHolder.tv_date.setText(mNotif.get(i).getDate());
    }

    @Override
    public int getItemCount() {
        return mNotif.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title;
        TextView tv_message;
        TextView tv_date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_notification_title);
            tv_message = itemView.findViewById(R.id.tv_notification_message);
            tv_date = itemView.findViewById(R.id.tv_notification_date);
        }
    }
}
