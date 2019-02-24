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
import com.example.dawnpeace.spota_android.Interfaces.UserInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConsultationActivity extends AppCompatActivity {

    private RecyclerView rv_consultation_review;
    private SharedPrefHelper mSharedPref;
    private FloatingActionButton fab_comment;
    private int section = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation);
        mSharedPref = SharedPrefHelper.getInstance(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle == null){
            finish();
        }

        section = bundle.getInt("section");


        if(getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getSection(section));
        }


        fab_comment = findViewById(R.id.fab_consultation_review);
        fab_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsultationActivity.this,ConsultationReviewActivity.class);
                intent.putExtra("section",section);
                startActivity(intent);

            }
        });

        loadData();
    }



    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                .client(mSharedPref.getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserInterface userInterface = retrofit.create(UserInterface.class);
        Call<List<Review>> call = userInterface.getConsultationReview(section);
        call.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if(response.isSuccessful()){
                    rv_consultation_review = findViewById(R.id.rv_consultation_review);
                    ConsultationReviewAdapter adapter = new ConsultationReviewAdapter(ConsultationActivity.this,response.body());
                    rv_consultation_review.setAdapter(adapter);
                    rv_consultation_review.setLayoutManager(new LinearLayoutManager(ConsultationActivity.this));
                } else {
                    Toast.makeText(ConsultationActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {

            }
        });

    }

    private String getSection(int section){
        String bab = "BAB I";
        switch (section){
            case 1:
                bab = "BAB I";
                break;
            case 2:
                bab = "BAB II";
                break;
            case 3:
                bab = "BAB III";
                break;
            case 4:
                bab = "BAB IV";
                break;
            case 5:
                bab = "BAB V";
                break;
        }
        return bab;
    }

}
