package com.example.dawnpeace.spota_android;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dawnpeace.spota_android.Classes.Approval;

import java.util.List;

public class ApprovalAdapter  extends RecyclerView.Adapter<ApprovalAdapter.MyViewHolder> {

    private List<Approval> approval;
    private Context context;

    public ApprovalAdapter(List<Approval> approval,Context context) {
        this.approval = approval;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.approval_list_layout,viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Approval approval = this.approval.get(i);
        myViewHolder.tv_date.setText(approval.getDate());
        myViewHolder.tv_approval.setText(approval.getApproval());
    }

    @Override
    public int getItemCount() {
        return approval.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_approval;
        private TextView tv_date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_approval =  itemView.findViewById(R.id.tv_approval);
            tv_date = itemView.findViewById(R.id.tv_date);
        }
    }

}
