package com.smart.home.model;


import android.app.Activity;
import android.os.Handler;

import java.lang.ref.WeakReference;

/**
 * Created by lenovo on 2017/4/9.
 */

public class WeakRefHandler extends Handler {
    WeakReference<Activity> activityRef;

    public WeakRefHandler(Activity activity) {
        activityRef = new WeakReference<Activity>(activity);
    }
}
