package com.example.dawnpeace.spota_android.Interfaces;

import com.example.dawnpeace.spota_android.Classes.Schedule;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

public interface ScheduleInterface {
    @POST("api/mahasiswa/jadwal")
    Call<List<Schedule>> getSchedules();
}
