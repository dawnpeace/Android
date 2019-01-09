package com.example.dawnpeace.spota_android;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.dawnpeace.spota_android.Classes.Schedule;
import com.example.dawnpeace.spota_android.Interfaces.ScheduleInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScheduleFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private SharedPrefHelper mSharedPref;
    private View v;
    private SwipeRefreshLayout swipeManager;
    private RelativeLayout rl_content;
    private RelativeLayout rl_progressbar;
    private RelativeLayout rl_fail;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_schedule, container, false);
        swipeManager = v.findViewById(R.id.schedule_swipe_refresh);
        swipeManager.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setSchedule();
            }
        });
        rl_content = v.findViewById(R.id.rl_schedule_content);
        rl_progressbar = v.findViewById(R.id.rl_pb_schedule);
        rl_fail = v.findViewById(R.id.rl_schedule_fail);
        rl_progressbar.setVisibility(View.VISIBLE);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setSchedule();
    }

    @Override
    public void onResume() {
        super.onResume();
        setSchedule();
    }

    private void setSchedule() {
        mSharedPref = SharedPrefHelper.getInstance(getContext());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .client(mSharedPref.getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ScheduleInterface schedules = retrofit.create(ScheduleInterface.class);
        Call<List<Schedule>> call = schedules.getSchedules();
        call.enqueue(new Callback<List<Schedule>>() {
            @Override
            public void onResponse(Call<List<Schedule>> call, Response<List<Schedule>> response) {
                if (response.isSuccessful()) {
                    mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_schedule);
                    ScheduleRecyclerAdapter sch_adapter = new ScheduleRecyclerAdapter(getContext(), response.body());
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    mRecyclerView.setAdapter(sch_adapter);
                    if (swipeManager != null) {
                        swipeManager.setRefreshing(false);
                    }
                    rl_content.setVisibility(View.VISIBLE);
                    rl_fail.setVisibility(View.GONE);
                    rl_progressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Schedule>> call, Throwable t) {

                if (swipeManager != null) {
                    swipeManager.setRefreshing(false);
                }
                rl_content.setVisibility(View.GONE);
                rl_fail.setVisibility(View.VISIBLE);
                rl_progressbar.setVisibility(View.GONE);
            }
        });
    }
}
