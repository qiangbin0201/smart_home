package com.smart.home.presenter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by qiangbin on 2017/4/24.
 */

public class SocketPresenter {

    private String mHost = "192.168.1.123";

    private int mSockectPort = 8888;

    private Socket socket;

    private boolean isConnect;

    private PrintWriter outStream;

    private static final String KEEPALIVE = "keep_alive";

    private static SocketPresenter mSocketPresenter;

    public static SocketPresenter getInstance(){
        if(mSocketPresenter == null){
            mSocketPresenter = new SocketPresenter();
        }
        return mSocketPresenter;
    }

    public void connect() {
        try {
            socket = new Socket(mHost, mSockectPort);
            isConnect = true;
            ReceiveThread mReceiveThread = new ReceiveThread();
            mReceiveThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg){
        if(!checkIsAlive()){
            return;
        }
        try {
            if(socket != null && socket.isConnected() && socket.isOutputShutdown()){
                outStream = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())), true);

                outStream.print(msg);
                outStream.flush();


            }
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            outStream.close();
        }
    }

    public boolean checkIsAlive() {
        if (socket == null)
            return false;
        try {
            socket.sendUrgentData(0xFF);
        } catch (IOException e) {
            return false;
        }
        return true;

    }

    class ReceiveThread extends Thread{

        @Override
        public void run() {
            while (true){
                try{
                    if(socket != null && socket.isConnected() && !socket.isInputShutdown()){
                        BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String content = inputStream.readLine();


                        int spacePos = content.indexOf(" ");
                        if (spacePos == -1)
                            continue;

                        String cmd = content.substring(4, spacePos);
                        //心跳时是监测到异常，请重新登陆
                        if(cmd.equals(KEEPALIVE)){
                            socket = null;
                            keepAlive();

                        }


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void keepAlive(){
        if(!checkIsAlive()){
            connect();
            String content = KEEPALIVE;
            sendMessage(content);

        }

    }

}
