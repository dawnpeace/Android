package com.example.dawnpeace.spota_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dawnpeace.spota_android.Classes.MyDraft;
import com.example.dawnpeace.spota_android.Interfaces.UserInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OutlineFragment extends Fragment {
    private View v;
    private TextView tv_title;
    private TextView tv_upvote;
    private TextView tv_downvote;
    private TextView tv_comment;
    private Button bt_review;
    private TextView tv_status;
    private SharedPrefHelper mSharedPref;
    private RelativeLayout rl_fail;
    private RelativeLayout rl_progressbar;
    private LinearLayout ll_content;
    private ImageView iv_comment;
    private RecyclerView rv_approval;
    private Context context;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_outline,container,false);
        initView(v);
        setPreoutlineStatus();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        setPreoutlineStatus();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void setPreoutlineStatus(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .client(mSharedPref.getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final UserInterface myDraft = retrofit.create(UserInterface.class);
        Call<MyDraft> call = myDraft.getDraft();
        call.enqueue(new Callback<MyDraft>() {
            @Override
            public void onResponse(Call<MyDraft> call, Response<MyDraft> response) {
                if(response.isSuccessful()){
                    MyDraft draft = response.body();
                    bt_review.setVisibility(View.VISIBLE);
                    tv_title.setText(draft.getPreoutline_title());
                    final Intent intent = new Intent(context,ReviewActivity.class);
                    if(draft.getPreoutline_status().equals("open")){
                        bt_review.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                intent.putExtra("status","open");
                                startActivity(intent);
                            }
                        });
                    } else {
                        String status = "Draft telah diterima";
                        tv_status.setText(status);
                        bt_review.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                intent.putExtra("status","approved");
                                startActivity(intent);
                            }
                        });

                        iv_comment.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                intent.putExtra("status","approved");
                                startActivity(intent);
                            }
                        });
                    }
                    rv_approval.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rv_approval.setAdapter(new ApprovalAdapter(draft.getApproval_list(),getActivity()));
                    tv_upvote.setText(draft.getApproval_count() + "");
                    tv_downvote.setText(draft.getDisapproval_count() + "");
                    tv_comment.setText(draft.getReview_count() + "");



                    rl_progressbar.setVisibility(View.GONE);
                    ll_content.setVisibility(View.VISIBLE);
                } else {
                    bt_review.setVisibility(View.GONE);
                    rl_progressbar.setVisibility(View.GONE);
                    ll_content.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<MyDraft> call, Throwable t) {
                rl_progressbar.setVisibility(View.GONE);
                rl_fail.setVisibility(View.VISIBLE);
                ll_content.setVisibility(View.GONE);
            }
        });
    }

    private void initView(View v){
        mSharedPref = SharedPrefHelper.getInstance(getActivity());
        ll_content = v.findViewById(R.id.ll_outline_second);
        rl_progressbar = v.findViewById(R.id.rl_pb_outline);
        rl_fail = v.findViewById(R.id.rl_outline_fail);
        tv_title =  v.findViewById(R.id.tv_outline_title);
        tv_upvote =  v.findViewById(R.id.tv_upvote);
        tv_downvote =  v.findViewById(R.id.tv_downvote);
        tv_comment = v.findViewById(R.id.tv_comment);
        bt_review = v.findViewById(R.id.btn_review);
        tv_status =  v.findViewById(R.id.tv_outline_status);
        ll_content.setVisibility(View.GONE);
        rl_fail.setVisibility(View.GONE);
        iv_comment = v.findViewById(R.id.iv_review);
        rv_approval = v.findViewById(R.id.rv_approval);
    }
}
