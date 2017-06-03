package com.smart.home.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;

import com.smart.home.model.HandlerProtocol;
import com.smart.home.presenter.ServerThread;

import java.net.*;
import java.io.*;
import java.util.*;



public class ServerService extends Service {

	public static ArrayList<Socket> socketList = new ArrayList<Socket>();
	protected Socket s;

	protected static final String SELECT_EQUIP_CODE = "equipCode";

	private static final String BULB_PROTOCOL = "bulbProtocol";

	private static final String IS_NET_CONNECT = "isNetConnect";

	public static final String REGISTER_BROADCAST = "com.smart.home.broadcast";

	public static final String IS_CONTROL_SUCCESS = "isControlSuccess";

	private static final String RECEIVE_MESSAGE = "receiveMessage";

	protected String mEquipCode;

	private String mBulbProtocol;

	private Handler mUiHandler;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public static void Launch(Context context, String equipCode){
		Intent intent = new Intent(context, ServerService.class);
		intent.putExtra(SELECT_EQUIP_CODE, equipCode);
		context.startService(intent);
	}

	public static void Launch(Context context){
		Intent intent = new Intent(context, ServerService.class);
		context.startService(intent);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
//		mEquipCode = intent.getStringExtra(SELECT_EQUIP_CODE);
//        mBulbProtocol = intent.getStringExtra(BULB_PROTOCOL);

	}


	@Override
	public void onCreate() {
		super.onCreate();
		new Thread(networkTask).start();
		mUiHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				Intent sendIntent = new Intent();
				sendIntent.setAction(REGISTER_BROADCAST);
				if(msg.what == HandlerProtocol.NET_CONNECT){
					sendIntent.putExtra(IS_NET_CONNECT, true);
					sendBroadcast(sendIntent);
				}else if(msg.what == HandlerProtocol.NET_NOT_CONNECT){
					sendIntent.putExtra(IS_NET_CONNECT, false);
					sendBroadcast(sendIntent);
				}else if(msg.what == HandlerProtocol.CONTROL_SUCCESS){
					sendIntent.putExtra(IS_CONTROL_SUCCESS, true);
					sendIntent.putExtra(RECEIVE_MESSAGE, msg.obj.toString());
					sendBroadcast(sendIntent);
				}
			}
		};

    }

	Runnable networkTask = new Runnable() {

		@Override
		public void run() {
			try {
				ServerSocket ss =  new ServerSocket(8080);
				while (true) {
					s = ss.accept();
					socketList.add(s);

					if (s != null) {
						new Thread(new ServerThread(s, mEquipCode, mUiHandler)).start();
					}
				}
			}catch (IOException e){
				e.printStackTrace();
			}
		}
	};


	@Override
    public void onDestroy() {
        super.onDestroy();
    }
}
