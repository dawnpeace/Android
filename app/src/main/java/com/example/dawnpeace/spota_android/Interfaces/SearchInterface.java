package com.example.dawnpeace.spota_android;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;

public interface SearchInterface {
    @POST("api/mahasiswa/pencarian")
    Call<List<Draft>> searchDraft(@Field("q") String q);
}
