package com.example.dawnpeace.spota_android.Interfaces;

import com.example.dawnpeace.spota_android.Classes.ConsultationStatus;
import com.example.dawnpeace.spota_android.Classes.Message;
import com.example.dawnpeace.spota_android.Classes.MyDraft;
import com.example.dawnpeace.spota_android.Classes.Notification;
import com.example.dawnpeace.spota_android.Classes.Review;
import com.example.dawnpeace.spota_android.Classes.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserInterface {
    @POST("api/mahasiswa")
    Call<User> getAuthUser();

    @POST("api/mahasiswa/praoutline-saya")
    Call<MyDraft> getDraft();

    @Multipart
    @POST("api/mahasiswa/update-profile")
    Call<Message> updateProfile(@Part("name") RequestBody name, @Part("email") RequestBody email, @Part("password") RequestBody password, @Part("old_password") RequestBody old_password,@Part MultipartBody.Part picture);

    @POST("api/mahasiswa/notifikasi")
    Call<List<Notification>> getNotification();

    @POST("api/mahasiswa/konsultasi")
    Call<ConsultationStatus> getConsultationStatus();

    @POST("api/mahasiswa/konsultasi/{section}")
    Call<List<Review>> getConsultationReview(@Path("section") int section);

    @FormUrlEncoded
    @POST("api/mahasiswa/konsultasi/{section}/review")
    Call<Void> sendConsultation(@Path("section") int section,@Field("message") String message);

    @POST("api/mahasiswa/konsultasi/{id}/delete")
    Call<Void> deleteConsultationReview(@Path("id") int id);

    @FormUrlEncoded
    @POST("api/auth/fcm-token")
    Call<Void> storeFCMToken(@Field("fcm_token") String fcm_token);

    @POST("api/auth/destroy-fcm")
    Call<Void> deleteFCMToken();
}
