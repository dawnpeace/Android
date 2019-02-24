package com.example.dawnpeace.spota_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private boolean logging_in = false;
    private SharedPrefHelper mSharedPref = SharedPrefHelper.getInstance(this);



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

    public void setLogging_in(boolean logging_in) {
        this.logging_in = logging_in;
    }

    public void login(View view){
        identity_number = _identity_number.getText().toString();
        password = _password.getText().toString();
        if(logging_in){
            Toast.makeText(this, "Harap tunggu hingga proses selesai . . ", Toast.LENGTH_SHORT).show();
        }

        if(!ConnectionChecker.isNetworkAvailable(getApplicationContext())){
            Toast.makeText(this, "Tidak ada koneksi Internet .", Toast.LENGTH_SHORT).show();
        } else
            if(!isValid(identity_number,password)){
                Toast.makeText(this,"Nomor Identitas atau Password tidak boleh kosong.",Toast.LENGTH_SHORT).show();
        } else {
            setLogging_in(true);
            Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
            LoginInterface token = retrofit.create(LoginInterface.class);
            Call<LoginToken> call = token.getAuth(identity_number, password);
            call.enqueue(new Callback<LoginToken>() {
                @Override
                public void onResponse(Call<LoginToken> call, Response<LoginToken> response) {
                    if(response.isSuccessful()){
                        if(response.body().getType().equals("M")  ){
                            if(response.body().getStatus().equals("A")){
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                mSharedPref.storeToken(response.body().getAccess_token());
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Akun anda nonaktif", Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            Toast.makeText(LoginActivity.this, "Akun tidak ditemukan !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if(response.code() == 401){
                            Toast.makeText(LoginActivity.this, "Akun tidak ditemukan !", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }
                    setLogging_in(false);
                }
                @Override
                public void onFailure(Call<LoginToken> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Tidak dapat menghubungi server !", Toast.LENGTH_SHORT).show();
                    setLogging_in(false);
                }
            });
        }
    }
}
