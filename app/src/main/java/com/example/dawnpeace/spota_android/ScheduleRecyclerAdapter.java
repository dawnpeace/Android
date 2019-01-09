package com.example.dawnpeace.spota_android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dawnpeace.spota_android.Classes.Schedule;

import java.util.List;

public class ScheduleRecyclerAdapter extends RecyclerView.Adapter<ScheduleRecyclerAdapter.MyViewHolder> {

    private Context mCtx;
    private List<Schedule> mSchedule;

    public ScheduleRecyclerAdapter(Context mCtx, List<Schedule> mSchedule) {
        this.mCtx = mCtx;
        this.mSchedule = mSchedule;
    }

    @NonNull
    @Override
    public ScheduleRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mCtx).inflate(R.layout.schedule,viewGroup,false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_sch_type.setText(mSchedule.get(i).getType());
        myViewHolder.tv_sch_name.setText(mSchedule.get(i).getName() + " ("+mSchedule.get(i).getIdentity_number()+")");
        myViewHolder.tv_sch_date.setText(mSchedule.get(i).getDate()+", "+mSchedule.get(i).getTime());
    }


    @Override
    public int getItemCount() {
        return mSchedule.size();
    }


    protected static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_sch_type;
        private TextView tv_sch_name;
        private TextView tv_sch_date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_sch_type = (TextView) itemView.findViewById(R.id.tv_sch_type);
            tv_sch_name = (TextView) itemView.findViewById(R.id.tv_sch_name);
            tv_sch_date = (TextView) itemView.findViewById(R.id.tv_sch_date);

        }
    }
}
