package com.example.dawnpeace.spota_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawnpeace.spota_android.Interfaces.ReviewInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReplyActivity extends AppCompatActivity {

    private String messages;
    private String parent_comment_id;
    private TextView tv_parent_message;
    private TextView tv_reply;
    private Retrofit retrofit;
    private SharedPrefHelper mSharedPref;
    private boolean commenting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        mSharedPref = SharedPrefHelper.getInstance(this);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.review_menu);
        }
        initView();
    }

    public void initView(){
        tv_parent_message = findViewById(R.id.tv_parent_comment);
        tv_reply = findViewById(R.id.tv_reply);
        try{
            messages = getIntent().getExtras().getString("MESSAGE");
            parent_comment_id = Integer.toString(getIntent().getExtras().getInt("PARENT_ID"));
            parent_comment_id = parent_comment_id == "0" ? "" : parent_comment_id;
        } catch (NullPointerException e){
            messages = null;
        }

        if(messages != null){
            tv_parent_message.setVisibility(View.VISIBLE);
            tv_parent_message.setText(messages);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private void setCommenting(boolean is_commenting){
        this.commenting = is_commenting;
    }

    public void replyOnClick(View v){
        String replyMessage = tv_reply.getText().toString();
        if(replyMessage.isEmpty()){
            Toast.makeText(this, "Pastikan Anda telah menulis komentar .", Toast.LENGTH_SHORT).show();
        } else {
            if(commenting){
                Toast.makeText(this, "Harap tunggu hingga pesan terkirim . .", Toast.LENGTH_SHORT).show();
                return;
            }
            setCommenting(true);
            retrofit = new Retrofit.Builder()
                    .baseUrl(APIUrl.BASE_URL)
                    .client(mSharedPref.getInterceptor())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ReviewInterface review = retrofit.create(ReviewInterface.class);
            Call<Void> call = parent_comment_id == null ? review.replyReview(replyMessage) : review.replyReview(parent_comment_id, replyMessage);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(ReplyActivity.this, "Komentar berhasil ditambahkan .", Toast.LENGTH_SHORT).show();
                        setCommenting(false);
                        finish();
                    } else {
                        setCommenting(false);
                        Toast.makeText(ReplyActivity.this, "Terjadi Kesalahan ."+ response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(ReplyActivity.this, "Terjadi Kesalahan .", Toast.LENGTH_SHORT).show();
                    setCommenting(false);
                }
            });
        }
    }

}
