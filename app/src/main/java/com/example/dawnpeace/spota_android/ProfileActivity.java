package com.example.dawnpeace.spota_android;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dawnpeace.spota_android.Classes.Message;
import com.example.dawnpeace.spota_android.Classes.User;
import com.example.dawnpeace.spota_android.Interfaces.UserInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfileActivity extends AppCompatActivity {
    final int FILE_REQUEST_CODE = 42;
    private ImageView imageView;
    private SharedPrefHelper mSharedPref;
    private EditText et_name;
    private EditText et_email;
    private EditText et_password;
    private EditText et_old_password;
    private EditText et_confirm;
    private Button submit_btn;
    private Uri image_uri;
    private String username;
    private String email;
    private String password;
    private String old_password;
    private boolean uploading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.menu_update_profile);
        }
        mSharedPref = SharedPrefHelper.getInstance(this);
        initView();


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        image_uri = null;
        if (requestCode == FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                image_uri = data.getData();
                Glide.with(this)
                        .load(image_uri)
                        .into(imageView);
            }
        }
    }

    private void setUploading(boolean uploading){
        this.uploading = uploading;
    }

    public void uploadImage(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, FILE_REQUEST_CODE);

    }

    public void submitChange(View view) {
        if (checkSubmit()) {

            if(uploading){
                Toast.makeText(this, "Harap tunggu sampai data selesai dikirim . .", Toast.LENGTH_SHORT).show();
                return;
            }



            username = et_name.getText().toString();
            email = et_email.getText().toString();
            password = et_password.getText().toString();
            old_password = et_old_password.getText().toString();
            MultipartBody.Part filePart = null;
            if (image_uri != null) {
                String filePath = Build.VERSION.SDK_INT < 21 ? getImagePath(image_uri) : getRealPathFromURIPath(image_uri, this);
                File file = new File(filePath);
                RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                filePart = MultipartBody.Part.createFormData("picture", file.getName(), mFile);
            }

            RequestBody req_name = RequestBody.create(MediaType.parse("text/plain"), username);
            RequestBody req_email = RequestBody.create(MediaType.parse("text/plain"), email);
            RequestBody req_password = RequestBody.create(MediaType.parse("text/plain"), password);
            RequestBody req_old_password = RequestBody.create(MediaType.parse("text/plain"), old_password);
            setUploading(true);
            Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(mSharedPref.getInterceptor())
                    .build();

            UserInterface auth = retrofit.create(UserInterface.class);
            Call<Message> call = auth.updateProfile(req_name,req_email,req_password,req_old_password,filePart);
            call.enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {
                    if(response.isSuccessful()){
                        mSharedPref.storeUser(response.body().getUser());
                        Toast.makeText(ProfileActivity.this, "Berhasil !", Toast.LENGTH_SHORT).show();
                        finish();
                        setUploading(false);
                    } else {
                        setUploading(false);
                        if(response.code() == 400){
                            Gson gson = new GsonBuilder().create();
                            Message err_message;
                            try {
                                err_message = gson.fromJson(response.errorBody().string(), Message.class);
                                Toast.makeText(ProfileActivity.this, err_message.getError_message(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(ProfileActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Message> call, Throwable t) {
                    setUploading(false);
                    Toast.makeText(ProfileActivity.this, "Terjadi Kesalahan . .", Toast.LENGTH_SHORT).show();
                }
            });


        }

    }

    private boolean checkSubmit(){
        String new_password = et_password.getText().toString();
        String old_password = et_old_password.getText().toString();
        String confirm_password = et_confirm.getText().toString();

        if(new_password.length() == 0 && confirm_password.length() == 0 && old_password.length() == 0){
            return true;
        }

        if(new_password.length() > 6 && new_password.length() < 12 && new_password.equals(confirm_password)){
            if(old_password.length() > 6 && old_password.length() < 12 && !old_password.equals(new_password)){
                return true;
            }
        }
        return false;
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    private String getImagePath(Uri uri){
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":")+1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }



    public void initView() {
        User user = mSharedPref.getUser();
        imageView = findViewById(R.id.iv_change_photo);
        et_name = findViewById(R.id.et_change_name);
        et_email = findViewById(R.id.et_change_email);
        et_password = findViewById(R.id.et_change_password);
        et_confirm = findViewById(R.id.et_confirm_password);
        et_old_password = findViewById(R.id.et_old_password);

        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.length() > 0){
                    if(s.toString().length() < 6 || s.toString().length() > 12){
                        et_password.setError("Password harus diantara 6 - 12 Karakter");
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length() == 0 && et_old_password.getText().toString().length() == 0 && et_confirm.getText().toString().length() == 0){
                    et_confirm.setError(null);
                    et_password.setError(null);
                    et_old_password.setError(null);
                }

                if(s.length() > 0){
                    if(s.toString().length() < 6 || s.toString().length() > 12){
                        et_password.setError("Password harus diantara 6 - 12 Karakter");
                    }

                    if(!et_confirm.getText().toString().equals(s.toString())){
                        et_confirm.setError("Konfirmasi password tidak sama !");
                    }

                    if(et_old_password.getText().toString().isEmpty()){
                        et_old_password.setError("Password Lama Kosong");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){
                    if(s.toString().length() < 6 || s.toString().length() > 12){
                        et_password.setError("Password harus diantara 6 - 12 Karakter");
                    }

                    if(et_password.getText().toString().equals(et_old_password.getText().toString())){
                        et_password.setError("Password tidak boleh sama dengan password lama");
                    }
                }
            }
        });

        et_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(et_password.getText().toString().length() > 0){
                    if(!s.toString().equals(et_password.getText().toString())){
                        et_confirm.setError("Konfirmasi password tidak sama !");
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et_password.getText().toString().length() == 0){
                    et_password.setError("Password baru kosong !");
                } else {
                    if(!s.toString().equals(et_password.getText().toString())){
                        et_confirm.setError("Konfirmasi password tidak sama !");
                    }

                    if(s.toString().equals(et_old_password.getText().toString())){
                        et_old_password.setError("Password lama tidak boleh sama");
                    }

                    if(s.length() > 6 && s.length() < 12 && s.toString().equals(et_password.getText().toString())){
                        et_password.setError(null);
                        et_confirm.setError(null);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(et_password.getText().toString().length() == 0){
                    et_password.setError("Password baru kosong !");
                } else {
                    if(!s.toString().equals(et_password.getText().toString())){
                        et_confirm.setError("Konfirmasi password tidak sama !");
                    }
                }
            }
        });

        et_old_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(et_password.getText().toString().length() > 0){
                    if(s.length() == 0){
                        et_old_password.setError("Password lama tidak boleh kosong !");
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et_password.getText().toString().length() > 0){
                    if(s.length() < 6 || s.length() > 12){
                        et_old_password.setError("Password diantara 6 - 12 karakter !");
                    }
                    if(s.toString().equals(et_password.getText().toString())){
                        et_old_password.setError("Password lama tidak boleh sama");
                    } else {
                        et_old_password.setError(null);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(et_password.getText().toString().length() > 0){
                    if(s.length() < 6 || s.length() > 12){
                        et_old_password.setError("Password diantar 6 - 12 karakter !");
                    }
                }
            }
        });

        submit_btn = findViewById(R.id.btn_submit_update);
        if (user != null) {
            et_name.setText(user.getName());
            et_email.setText(user.getEmail());
        }
    }
}
