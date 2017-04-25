package com.smart.home.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.smart.home.presenter.SocketPresenter;

/**
 * Created by lenovo on 2017/4/24.
 */

public class SocketeService extends Service {

    public static void Launch(Context context){
        Intent intent = new Intent(context, SocketeService.class);
        context.startActivity(intent);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SocketPresenter.getInstance().connect();
    }
}
