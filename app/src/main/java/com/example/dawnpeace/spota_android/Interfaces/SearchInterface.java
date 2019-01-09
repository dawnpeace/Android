package com.example.dawnpeace.spota_android.Interfaces;

import com.example.dawnpeace.spota_android.Classes.Preoutline;
import com.example.dawnpeace.spota_android.Classes.ViewPreoutline;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SearchInterface {
    @FormUrlEncoded
    @POST("api/mahasiswa/pencarian")
    Call<List<Preoutline>> studentSearcDraft(@Field("q") String q);

    @POST("api/mahasiswa/praoutline/{id}")
    Call<ViewPreoutline> getPreoutline(@Path("id") int id);

    @POST("api/dosen/pencarian")
    Call<List<Preoutline>> lecturerSearchDraft(@Field("q") String q);
}
