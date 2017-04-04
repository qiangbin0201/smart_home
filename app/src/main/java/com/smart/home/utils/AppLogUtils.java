package com.smart.home.utils;

import android.util.Log;

import com.smart.home.BuildConfig;

/**
 * Created by 冯子杰(fengzijie@machine.com)
 * Date: 14-11-17
 */
public class AppLogUtils {

    public static void v(String message){
        try {
            if (BuildConfig.DEBUG){
                Log.v("AppLog",message);
            }
//            Crashlytics.log(message);
        } catch (Exception e) {
        }
    }

    public static void d(String message){
        try {
            if (BuildConfig.DEBUG){
                Log.d("AppLog",message);
            }
//            Crashlytics.log(message);
        } catch (Exception e) {
        }
    }

    public static void i(String message){
        try {
            if (BuildConfig.DEBUG){
                Log.i("AppLog",message);
            }
//            Crashlytics.log(message);
        } catch (Exception e) {
        }
    }

    public static void w(String message){
        try {
            if (BuildConfig.DEBUG){
                Log.w("AppLog",message);
            }
//            Crashlytics.log(message);
        } catch (Exception e) {
        }
    }

    public static void e(String message){
        try {
            if (BuildConfig.DEBUG){
                Log.e("AppLog",message);
            }
//            Crashlytics.log(message);
        } catch (Exception e) {
        }
    }

}
