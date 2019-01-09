package com.example.dawnpeace.spota_android;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dawnpeace.spota_android.Classes.Preoutline;

import java.util.List;


public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.MyViewHolder>{
    private List<Preoutline> preoutlines;
    private Context mCtx;
    private View v;

    public SearchRecyclerViewAdapter(List<Preoutline> preoutlines, Context mCtx) {
        this.preoutlines = preoutlines;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(mCtx).inflate(R.layout.draft,viewGroup,false);
        final MyViewHolder myViewHolder = new MyViewHolder(v);
        myViewHolder.ll_search_draft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx,PreoutlineActivity.class);
                intent.putExtra("id",preoutlines.get(myViewHolder.getAdapterPosition()).getId());
                mCtx.startActivity(intent);
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_title.setText(preoutlines.get(i).getTitle());
        myViewHolder.tv_name.setText(preoutlines.get(i).getName() + " ( "+ preoutlines.get(i).getIdentity_number() +" )");
        myViewHolder.tv_draft_status.setText(preoutlines.get(i).getStatus());
        myViewHolder.tv_upvote.setText(Integer.toString(preoutlines.get(i).getApprove_count()));
        myViewHolder.tv_downvote.setText(Integer.toString(preoutlines.get(i).getDisapprove_count()));
        myViewHolder.tv_comment.setText(Integer.toString(preoutlines.get(i).getReview_count()));
    }

    @Override
    public int getItemCount() {
        return preoutlines.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_title;
        private TextView tv_name;
        private TextView tv_draft_status;
        private TextView tv_upvote;
        private TextView tv_downvote;
        private TextView tv_comment;
        private LinearLayout ll_search_draft;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_draft_title);
            tv_name = itemView.findViewById(R.id.tv_draft_student_name);
            tv_draft_status = itemView.findViewById(R.id.tv_draft_status);
            tv_upvote = itemView.findViewById(R.id.tv_draft_upvote_count);
            tv_downvote = itemView.findViewById(R.id.tv_draft_downvote_count);
            tv_comment = itemView.findViewById(R.id.tv_draft_comment_count);
            ll_search_draft = itemView.findViewById(R.id.ll_search_draft);
        }
    }
}
