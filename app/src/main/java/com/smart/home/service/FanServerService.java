package com.smart.home.service;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.smart.home.presenter.ServerThread;

import java.io.IOException;

public class FanServerService extends BaseService {

    public static final String FAN_PROTOCOL = "fanProtocol";

    private String mFanProtocol;

    public static void Launch(Context context, String equipCode, String fanProtocol){
        Intent intent = new Intent(context, FanServerService.class);
        intent.putExtra(SELECT_EQUIP_CODE, equipCode);
        intent.putExtra(FAN_PROTOCOL, fanProtocol);
        context.startService(intent);

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        mEquipCode = intent.getStringExtra(SELECT_EQUIP_CODE);
        mFanProtocol = intent.getStringExtra(FAN_PROTOCOL);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            new Thread(new ServerThread(s, mEquipCode, mFanProtocol)).start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
