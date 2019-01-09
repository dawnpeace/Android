package com.example.dawnpeace.spota_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawnpeace.spota_android.Interfaces.UserInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConsultationReviewActivity extends AppCompatActivity {
    private int section;
    private SharedPrefHelper mSharedPref;
    private TextView tv_comment;
    private Button bt_reply;
    private EditText et_reply;
    private boolean commenting = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_review);

        mSharedPref = SharedPrefHelper.getInstance(this);

        if(getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.menu_consultation));
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        section = bundle.getInt("section");
        tv_comment = findViewById(R.id.tv_comment);
        bt_reply = findViewById(R.id.bt_reply);
        et_reply = findViewById(R.id.tv_reply);
        bt_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(et_reply.getText().toString());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setCommenting(boolean isCommenting){
        this.commenting = isCommenting;
    }

    private void sendMessage(String message){
        if(!message.isEmpty()){
            if(commenting){
                Toast.makeText(this, "Harap tunggu hingga pesan terkirim . .", Toast.LENGTH_SHORT).show();
                return;
            }
            setCommenting(true);
            Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(mSharedPref.getInterceptor())
                    .build();

            UserInterface userInterface = retrofit.create(UserInterface.class);
            Call<Void> call = userInterface.sendConsultation(section,message);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(ConsultationReviewActivity.this, "Terjadi kesalahan !", Toast.LENGTH_SHORT).show();
                        setCommenting(false);
                    } else {
                        setCommenting(false);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(ConsultationReviewActivity.this, "Terjadi kesalahan !", Toast.LENGTH_SHORT).show();
                    setCommenting(false);
                }
            });

        } else {

        }
    }


}
