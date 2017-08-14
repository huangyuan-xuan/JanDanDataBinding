package com.huangyuanlove.jandan.app;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by huangyuan on 2017/8/10.
 */

public class MyApplication extends Application {
    public static Retrofit retrofit;
    @Override
    public void onCreate() {
        super.onCreate();
        retrofit = new Retrofit.Builder().baseUrl("http://i.jandan.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
