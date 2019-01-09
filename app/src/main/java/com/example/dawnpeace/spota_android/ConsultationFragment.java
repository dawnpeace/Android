package com.example.dawnpeace.spota_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dawnpeace.spota_android.Classes.ConsultationStatus;
import com.example.dawnpeace.spota_android.Interfaces.UserInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConsultationFragment extends Fragment {
    private RelativeLayout rl_fail;
    private LinearLayout ll_content;
    private SharedPrefHelper mSharedPref;
    private RelativeLayout rl_progressbar;

    private LinearLayout ll_first;
    private LinearLayout ll_second;
    private LinearLayout ll_third;
    private LinearLayout ll_fourth;
    private LinearLayout ll_fifth;

    private TextView tv_first_count;
    private TextView tv_second_count;
    private TextView tv_third_count;
    private TextView tv_fourth_count;
    private TextView tv_fifth_count;

    private TextView tv_first_last_commented;
    private TextView tv_second_last_commented;
    private TextView tv_third_last_commented;
    private TextView tv_fourth_last_commented;
    private TextView tv_fifth_last_commented;

    private TextView tv_connection_fail;

    private Context context;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_consultation,container,false);
        rl_fail = v.findViewById(R.id.rl_consultation_fail);
        ll_content = v.findViewById(R.id.ll_consultation_content);
        mSharedPref = SharedPrefHelper.getInstance(context);
        initView(v);
        setConsultationStatus();
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void setConsultationStatus(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(mSharedPref.getInterceptor())
                .build();

        UserInterface userInterface = retrofit.create(UserInterface.class);
        Call<ConsultationStatus> call = userInterface.getConsultationStatus();
        call.enqueue(new Callback<ConsultationStatus>() {
            @Override
            public void onResponse(Call<ConsultationStatus> call, Response<ConsultationStatus> response) {
                rl_progressbar.setVisibility(View.GONE);
                switch (response.code()){
                    case 200:
                        setConsultationView(response.body());
                        break;
                    case 400:
                        ll_content.setVisibility(View.GONE);
                        rl_fail.setVisibility(View.VISIBLE);
                    case 403:
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<ConsultationStatus> call, Throwable t) {
                ll_content.setVisibility(View.GONE);
                rl_fail.setVisibility(View.VISIBLE);
                tv_connection_fail.setText(R.string.connetion_failure);
                rl_progressbar.setVisibility(View.GONE);
            }
        });
    }

    private void setConsultationView(ConsultationStatus status){
        try{
            if(status.isFirst_status()){
                ll_first.setVisibility(View.VISIBLE);
                String comment_count = String.valueOf(status.getFirst_comment_count());
                String last_commented = context.getString(R.string.last_commented)+" "+status.getFirst_last_commented();
                tv_first_last_commented.setText(last_commented);
                String comment_placeholder = context.getResources().getString(R.string.comment_count_placeholder)+" "+comment_count;
                tv_first_count.setText(comment_placeholder);
            }

            if(status.isSecond_status()){
                ll_second.setVisibility(View.VISIBLE);
                String comment_count = String.valueOf(status.getSecond_comment_count());
                String last_commented = getResources().getString(R.string.last_commented)+" "+status.getSecond_last_commented();
                tv_second_last_commented.setText(last_commented);
                String comment_placeholder = getResources().getString(R.string.comment_count_placeholder)+" "+comment_count;
                tv_second_count.setText(comment_placeholder);
            }

            if(status.isThird_status()){
                ll_third.setVisibility(View.VISIBLE);
                String comment_count = String.valueOf(status.getThird_comment_count());
                String last_commented = getResources().getString(R.string.last_commented)+" "+status.getThird_last_commented();
                tv_third_last_commented.setText(last_commented);
                String comment_placeholder = getResources().getString(R.string.comment_count_placeholder)+" "+comment_count;
                tv_third_count.setText(comment_placeholder);
            }

            if(status.isFourth_status()){
                ll_fourth.setVisibility(View.VISIBLE);
                String comment_count = String.valueOf(status.getFourth_comment_count());
                String last_commented = getResources().getString(R.string.last_commented)+" "+status.getFourth_last_commented();
                tv_fourth_last_commented.setText(last_commented);
                String comment_placeholder = getResources().getString(R.string.comment_count_placeholder)+" "+comment_count;
                tv_fourth_count.setText(comment_placeholder);
            }

            if(status.isFifth_status()){
                ll_fifth.setVisibility(View.VISIBLE);
                String comment_count = String.valueOf(status.getFifth_comment_count());
                String last_commented = getResources().getString(R.string.last_commented)+" "+status.getFifth_last_commented();
                tv_fifth_last_commented.setText(last_commented);
                String comment_placeholder = getResources().getString(R.string.comment_count_placeholder)+" "+comment_count;
                tv_fifth_count.setText(comment_placeholder);
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setConsultationStatus();
    }

    private void initView(View view){
        ll_first = view.findViewById(R.id.ll_first_consultation);
        ll_second = view.findViewById(R.id.ll_second_consultation);
        ll_third = view.findViewById(R.id.ll_third_consultation);
        ll_fourth = view.findViewById(R.id.ll_fourth_consultation);
        ll_fifth = view.findViewById(R.id.ll_fifth_consultation);

        ll_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ConsultationActivity.class);
                intent.putExtra("section",1);
                startActivity(intent);
            }
        });

        ll_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ConsultationActivity.class);
                intent.putExtra("section",2);
                startActivity(intent);
            }
        });

        ll_third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ConsultationActivity.class);
                intent.putExtra("section",3);
                startActivity(intent);
            }
        });

        ll_fourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ConsultationActivity.class);
                intent.putExtra("section",4);
                startActivity(intent);
            }
        });

        ll_fifth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ConsultationActivity.class);
                intent.putExtra("section",5);
                startActivity(intent);
            }
        });

        tv_first_count = view.findViewById(R.id.first_comment_count);
        tv_second_count = view.findViewById(R.id.second_comment_count);
        tv_third_count = view.findViewById(R.id.third_comment_count);
        tv_fourth_count = view.findViewById(R.id.fourth_comment_count);
        tv_fifth_count = view.findViewById(R.id.fifth_comment_count);

        tv_first_last_commented = view.findViewById(R.id.first_last_commented);
        tv_second_last_commented = view.findViewById(R.id.second_last_commented);
        tv_third_last_commented = view.findViewById(R.id.third_last_commented);
        tv_fourth_last_commented = view.findViewById(R.id.fourth_last_commented);
        tv_fifth_last_commented = view.findViewById(R.id.fifth_last_commented);

        ll_content = view.findViewById(R.id.ll_consultation_content);
        rl_fail = view.findViewById(R.id.rl_consultation_fail);

        tv_connection_fail = view.findViewById(R.id.tv_consultation_fail);

        rl_progressbar = view.findViewById(R.id.rl_pb_consultation);
    }
}
