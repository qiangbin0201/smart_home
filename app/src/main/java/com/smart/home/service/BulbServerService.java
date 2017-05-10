package com.smart.home.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;

import com.smart.home.presenter.ServerThread;

import java.net.*;
import java.io.*;
import java.util.*;



public class BulbServerService extends BaseService
{

	private static final String BULB_PROTOCOL = "bulbProtocol";

	private String mBulbProtocol;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public static void Launch(Context context, String equipCode, String bulbProtocol){
		Intent intent = new Intent(context, BulbServerService.class);
		intent.putExtra(SELECT_EQUIP_CODE, equipCode);
		intent.putExtra(BULB_PROTOCOL, bulbProtocol);
		context.startService(intent);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		mEquipCode = intent.getStringExtra(SELECT_EQUIP_CODE);
        mBulbProtocol = intent.getStringExtra(BULB_PROTOCOL);

	}


	@Override
	public void onCreate() {
		super.onCreate();
        try {
            new Thread(new ServerThread(s, mEquipCode, mBulbProtocol)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
