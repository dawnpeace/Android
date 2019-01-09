package com.example.dawnpeace.spota_android;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginInterface {
    @FormUrlEncoded
    @POST("api/auth/login")
    Call<LoginToken> getAuth(@Field("identity_number") String identity_number, @Field("password")String password);

}
