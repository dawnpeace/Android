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

import com.example.dawnpeace.spota_android.Classes.Announcement;
import com.example.dawnpeace.spota_android.Classes.AnnouncementList;
import com.example.dawnpeace.spota_android.Interfaces.AnnouncementInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    private View v;
    private RecyclerView mRecyclerView;
    private SharedPrefHelper mySharedPref;
    SwipeRefreshLayout swipeManager;
    private RelativeLayout rl_progressbar;
    private RelativeLayout rl_content;
    private RelativeLayout rl_fail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home,container,false);
        swipeManager = (SwipeRefreshLayout) v.findViewById(R.id.home_swipe_refresh);
        swipeManager.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setAnnouncements();
            }
        });
        rl_progressbar = v.findViewById(R.id.rl_pb_home);
        rl_content = v.findViewById(R.id.rl_home_content);
        rl_fail = v.findViewById(R.id.rl_home_fail);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setAnnouncements();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(ConnectionChecker.isNetworkAvailable(getActivity())){
            setAnnouncements();
        } else {
            Toast.makeText(getActivity(), "Periksa koneksi Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void setAnnouncements(){
        mySharedPref = SharedPrefHelper.getInstance(getActivity());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .client(mySharedPref.getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AnnouncementInterface announcements = retrofit.create(AnnouncementInterface.class);
        Call<List<Announcement>> al = announcements.getAnnouncements();
        al.enqueue(new Callback<List<Announcement>>() {
            @Override
            public void onResponse(Call<List<Announcement>> call, Response<List<Announcement>> response) {
                if(response.isSuccessful()){
                    mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_announcement);
                    RecyclerViewAdapater recyclerAdapter = new RecyclerViewAdapater(getContext(),response.body());
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mRecyclerView.setAdapter(recyclerAdapter);
                    rl_content.setVisibility(View.VISIBLE);
                    rl_progressbar.setVisibility(View.GONE);
                    if(swipeManager != null){
                        swipeManager.setRefreshing(false);
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<Announcement>> call, Throwable t) {
                if(swipeManager != null){
                    swipeManager.setRefreshing(false);
                }
                rl_progressbar.setVisibility(View.GONE);
                rl_content.setVisibility(View.GONE);
                rl_fail.setVisibility(View.VISIBLE);
            }
        });
    }
}
