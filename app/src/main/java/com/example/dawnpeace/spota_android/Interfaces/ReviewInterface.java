package com.example.dawnpeace.spota_android.Interfaces;

import com.example.dawnpeace.spota_android.Classes.Review;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReviewInterface {

    @POST("api/mahasiswa/review-saya")
    Call<List<Review>> getReviews();

    @FormUrlEncoded
    @POST("api/mahasiswa/review-saya/balas/{id}")
    Call<Void> replyReview(@Path("id") String id, @Field("message") String message);

    @FormUrlEncoded
    @POST("api/mahasiswa/review-saya/balas")
    Call<Void> replyReview(@Field("message") String message);

    @POST("api/mahasiswa/review-saya/hapus/{id}")
    Call<Void> deleteReview(@Path("id") int id);
}
