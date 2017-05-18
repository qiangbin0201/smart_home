package com.smart.home.service;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.smart.home.presenter.ServerThread;

import java.io.IOException;

public class AirConditionService extends BaseService {

    private static final String AIR_CONDITION_PROTOCOL = "airConditionProtocol";

    private String mAirConditionProtocol;


    public static void Launch(Context context, String equipCode, String airConditionProtocol){
        Intent intent = new Intent(context, AirConditionService.class);
        intent.putExtra(SELECT_EQUIP_CODE, equipCode);
        intent.putExtra(AIR_CONDITION_PROTOCOL, airConditionProtocol);
        context.startService(intent);

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        mEquipCode = intent.getStringExtra(SELECT_EQUIP_CODE);
        mAirConditionProtocol = intent.getStringExtra(AIR_CONDITION_PROTOCOL);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            new Thread(new ServerThread(s, mEquipCode, null)).start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
