package com.smart.home.utils;

import android.content.SharedPreferences;
import android.os.Build;

/**
 * Created by Ponyets on 14-5-5.
 */
public class SPUtils {
    public static void apply(SharedPreferences.Editor editor) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            editor.apply();
        } else {
            editor.commit();
        }
    }
}
