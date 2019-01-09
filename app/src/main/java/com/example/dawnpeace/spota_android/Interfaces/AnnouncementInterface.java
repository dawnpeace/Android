package com.example.dawnpeace.spota_android.Interfaces;

import com.example.dawnpeace.spota_android.Classes.Announcement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AnnouncementInterface {
    @POST("api/mahasiswa/pengumuman")
    Call<List<Announcement>> getAnnouncements();

    @POST("api/mahasiswa/pengumuman/{id}/lihat")
    Call<Void> getAnnouncement(@Path("id") int announcementId);

}
