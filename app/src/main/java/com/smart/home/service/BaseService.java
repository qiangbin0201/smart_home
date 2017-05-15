package com.smart.home.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.smart.home.presenter.ServerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class BaseService extends Service {

    public static ArrayList<Socket> socketList = new ArrayList<Socket>();
    protected Socket s;

    protected static final String SELECT_EQUIP_CODE = "equipCode";

    protected String mEquipCode;


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
       // new Thread(networkTask).start();

    }

    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            try {
                ServerSocket ss =  new ServerSocket(8080);
                while (true) {
                    s = ss.accept();
                    socketList.add(s);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onDestroy() {

        if(socketList != null){
            socketList = null;
        }
        super.onDestroy();

    }
}
