package com.example.dawnpeace.spota_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.dawnpeace.spota_android.Classes.Review;
import com.example.dawnpeace.spota_android.Interfaces.ReviewInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewActivity extends AppCompatActivity {
    SharedPrefHelper mSharedPref;
    RecyclerView mRecyclerView;
    FloatingActionButton fab;
    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        if(getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.review_menu);
        }
        Bundle bundle = getIntent().getExtras();
        status = bundle.getString("status");
        fab = findViewById(R.id.fab_review);
        if(!status.equals("open")){
            fab.hide();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ReplyActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        mSharedPref = SharedPrefHelper.getInstance(this);
        setReview();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setReview();
    }

    public void setReview(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                .client(mSharedPref.getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ReviewInterface review = retrofit.create(ReviewInterface.class);
        Call<List<Review>> call = review.getReviews();

        call.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if(response.isSuccessful()){
                    mRecyclerView = findViewById(R.id.rv_review);
                    ReviewRecyclerAdapter adapter = new ReviewRecyclerAdapter(ReviewActivity.this,response.body(),status);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    mRecyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                Toast.makeText(ReviewActivity.this, "Telah terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
