package com.example.dawnpeace.spota_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    protected Boolean is_logged_in = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }

    public void login(View view){
//        Toast.makeText(this,"wasun",Toast.LENGTH_SHORT).show();
        Intent loginIntent = new Intent(this,MainActivity.class);
        startActivity(loginIntent);
    }

}
