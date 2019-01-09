package com.example.dawnpeace.spota_android;

import android.app.Application;
import android.os.SystemClock;

public class SpotaApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(1500);
    }
}
