package com.example.dawnpeace.spota_android;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserInterface {
    @POST("api/mahasiswa/")
    Call<User> getAuthUser();

}
