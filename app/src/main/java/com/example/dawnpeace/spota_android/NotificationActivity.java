package com.example.dawnpeace.spota_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.dawnpeace.spota_android.Classes.Notification;
import com.example.dawnpeace.spota_android.Interfaces.UserInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationActivity extends AppCompatActivity {
    private SharedPrefHelper sharedPrefHelper;
    RecyclerView rv_notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        if(getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Pemberitahuan");
        }
        sharedPrefHelper = SharedPrefHelper.getInstance(this);
        loadData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    public void loadData(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                .client(sharedPrefHelper.getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserInterface userInterface = retrofit.create(UserInterface.class);
        Call<List<Notification>> call = userInterface.getNotification();
        call.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                if(response.isSuccessful()){
                    rv_notification = findViewById(R.id.rv_notification);
                    NotificationRecyclerAdapter adapter = new NotificationRecyclerAdapter(response.body(),NotificationActivity.this);
                    rv_notification.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));
                    rv_notification.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {

            }
        });
    }
}
