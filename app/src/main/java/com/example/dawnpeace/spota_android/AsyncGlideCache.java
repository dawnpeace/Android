package com.example.dawnpeace.spota_android;

import android.content.Context;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;

public class AsyncGlideCache extends AsyncTask<Void,Void,Void>  {

    private Context mContext;
    public AsyncGlideCache(Context ctx) {
        mContext = ctx;

    }

    @Override
    protected Void doInBackground(Void... voids) {
        Glide.get(mContext).clearDiskCache();
        return null;
    }
}
