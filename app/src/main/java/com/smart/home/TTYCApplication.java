package com.smart.home;

import android.app.Application;
import android.content.Context;


public class TTYCApplication extends Application {

    private static final String TAG = TTYCApplication.class.getSimpleName();

    private static TTYCApplication sInstance = null;

    public static Context mContext;

    public static TTYCApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

    }
}




