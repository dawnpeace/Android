package com.example.dawnpeace.spota_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.dawnpeace.spota_android.Classes.AnnouncementList;
import com.example.dawnpeace.spota_android.Classes.User;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SharedPrefHelper {

    private static SharedPrefHelper mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "SPOTA_ANDROID_2";

    private static final String KEY_USER_IDENTITY_NUMBER = "identity_number";
    private static final String KEY_USER_TOKEN = "authtoken";
    private static final String KEY_AUTH_USER = "user";
    private static final String KEY_ANNOUNCEMENT_LIST = "announcements";
    private static final String KEY_BOOL_FCM_STORED = "boolfcmtoken";
    private static final String KEY_FCM_TOKEN = "fcmtoken";

    private SharedPrefHelper(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefHelper(context);
        }
        return mInstance;
    }

    public void storeToken(String token) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_TOKEN, token);
        editor.apply();
    }

    public String getFirebaseToken(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_FCM_TOKEN,null);
    }

    public void storeFirebaseToken(String token){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_FCM_TOKEN,token);
        editor.apply();
    }


    public void storeUser(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(KEY_AUTH_USER, json);
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_TOKEN, null) != null)
            return true;
        return false;
    }


    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getToken(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_TOKEN,null);
    }

    public String getUserBody(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_AUTH_USER,null);
    }

    public OkHttpClient getInterceptor(){
        final SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + sharedPreferences.getString(KEY_USER_TOKEN,""))
                        .build();
                return chain.proceed(newRequest);
            }
        })
                .connectTimeout(60,TimeUnit.SECONDS)
                .build();
    }

    public User getUser(){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(KEY_AUTH_USER,null);
        return gson.fromJson(json, User.class);
    }


    public void setAnnouncement(AnnouncementList announcementList){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(announcementList);
        editor.putString(KEY_ANNOUNCEMENT_LIST,json);
        editor.apply();
    }


    public void setFcmTokenAvailability(boolean available) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_BOOL_FCM_STORED,available);
        editor.apply();
    }

    public boolean issetFCMToken(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_BOOL_FCM_STORED,false);
    }
}
