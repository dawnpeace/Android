package com.example.dawnpeace.spota_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawnpeace.spota_android.Classes.LoginToken;
import com.example.dawnpeace.spota_android.Interfaces.LoginInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private TextView _identity_number ;
    private TextView _password;
    private String identity_number;
    private String password;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        //Check if user has internet access
        if(!ConnectionChecker.isNetworkAvailable(getApplicationContext())){
            Toast.makeText(this, "Tidak ada koneksi Internet .", Toast.LENGTH_SHORT).show();
        }

        //Check if user has loggedIn
        if(SharedPrefHelper.getInstance(getApplicationContext()).isLoggedIn()){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    //init view objects
    private void initView(){
        _identity_number = findViewById(R.id.ET_identity_number);
        _password = findViewById(R.id.ET_password);
    }

    private boolean isValid(String... texts){
        for(String text: texts){
            if(text.isEmpty()){
                return false;
            }
        }
        return true;
    }

    public void login(View view){
        identity_number = _identity_number.getText().toString();
        password = _password.getText().toString();

        if(!ConnectionChecker.isNetworkAvailable(getApplicationContext())){
            Toast.makeText(this, "Tidak ada koneksi Internet .", Toast.LENGTH_SHORT).show();
        } else
            if(!isValid(identity_number,password)){
            Toast.makeText(this,"Pastikan Form terisi",Toast.LENGTH_SHORT).show();
        } else {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
            LoginInterface token = retrofit.create(LoginInterface.class);
            Call<LoginToken> call = token.getAuth(identity_number, password);
            Log.d("identity", "login: "+identity_number);
            Log.d("password", "login: "+password);
            call.enqueue(new Callback<LoginToken>() {
                @Override
                public void onResponse(Call<LoginToken> call, Response<LoginToken> response) {
                    if(response.isSuccessful()){
                        if(response.body().getType() != 'M'){
                            Toast.makeText(LoginActivity.this, "Akun Tidak ditemukan", Toast.LENGTH_SHORT).show();
                        } else {
                            SharedPrefHelper.getInstance(getApplicationContext()).storeToken(response.body().getAccess_token());
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Akun Tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }

                }
                @Override
                public void onFailure(Call<LoginToken> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Tidak dapat menghubungi server !", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
