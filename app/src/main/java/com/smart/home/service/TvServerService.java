package com.smart.home.service;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.smart.home.presenter.ServerThread;

import java.io.IOException;

public class TvServerService extends BaseService {


    private static final String SELECT_EQUIP_CODE = "equipCode";

    private static final String TV_PROTOCOL = "tvProtocol";

    private String mTvProtocol;



    public static void Launch(Context context, String equipCode, String tvProtocol){
        Intent intent = new Intent(context, TvServerService.class);
        intent.putExtra(SELECT_EQUIP_CODE, equipCode);
        intent.putExtra(TV_PROTOCOL, tvProtocol);
        context.startService(intent);

    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        mEquipCode = intent.getStringExtra(SELECT_EQUIP_CODE);
        mTvProtocol = intent.getStringExtra(TV_PROTOCOL);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            new Thread(new ServerThread(s, mEquipCode, mTvProtocol)).start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
