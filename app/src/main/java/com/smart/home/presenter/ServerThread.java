package com.smart.home.presenter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.smart.home.model.BulbProtocol;
import com.smart.home.model.HandlerProtocol;
import com.smart.home.model.TvProtocol;
import com.smart.home.service.ServerService;
import com.smart.home.utils.RadixUtil;

import java.io.*;
import java.net.*;


public class ServerThread extends Thread
{

	//设备唯一标识符
	private String mEquipCode;

	public static Handler rvHandler;

	private boolean isSelectedEquip = true;

	private Handler mUiHandler;

	private Socket s = null;

	private BufferedReader br = null;

	private OutputStream os;

	public ServerThread(Socket s, String equipCode, Handler mUiHandler)
		throws IOException
	{
		this.s = s;
		br = new BufferedReader(new InputStreamReader(s.getInputStream() , "utf-8"));
		os = s.getOutputStream();
		this.mUiHandler = mUiHandler;
		mEquipCode = equipCode;
	}
	public void run()
	{
		//通知service已经有客户端连接
		mUiHandler.sendEmptyMessage(HandlerProtocol.NET_CONNECT);

		new Thread(){
			@Override
			public void run() {
				String content = null;
				try {
					while (s != null && s.isConnected()) {

                        while ((content = readFromClient()) != null && content.equals(mEquipCode)) {

                        }
                    }
//					mUiHandler.sendEmptyMessage(HandlerProtocol.NET_NOT_CONNECT);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}.start();
//

		Looper.prepare();

		rvHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				try {
					if (isSelectedEquip) {
						if (msg.what == HandlerProtocol.BULB_ON) {
							os.write((BulbProtocol.BULB_ON + "\n").getBytes("utf-8"));

						} else if (msg.what == HandlerProtocol.BULB_OFF) {
							os.write((BulbProtocol.BULB_OFF + "\n").getBytes("utf-8"));
						} else if (msg.what == HandlerProtocol.BULB_BRIGHTNESS_UP) {
							os.write((BulbProtocol.BRIGHTNESS_UP + "\n").getBytes("utf-8"));
						} else if (msg.what == HandlerProtocol.BULB_BRIGHTNESS_DOWN) {
							os.write((BulbProtocol.BRIGHTNESS_DOWN + "\n").getBytes("utf-8"));
						}else if(msg.what == HandlerProtocol.TV_OFF){
							os.write((TvProtocol.TV_OFF+ "\n").getBytes("utf-8"));
						}else if(msg.what == HandlerProtocol.TV_ON){
							String temp = RadixUtil.int2hexString(TvProtocol.TV_ON);
							os.write((TvProtocol.TV_ON+ "\n").getBytes("utf-8"));
							os.write((temp + "\n").getBytes("ASCII"));
							byte[] buf = RadixUtil.hexString2Bytes(temp);
							os.write(buf);
						//	os.write((TvProtocol.TV_ON + "\n").getBytes("utf-8"));
						}else if(msg.what == HandlerProtocol.TV_SOUND_UP){
							os.write((TvProtocol.TV_SOUND_UP + "\n").getBytes("utf-8"));
						}else if(msg.what == HandlerProtocol.TV_SOUND_DOWN){
							os.write((TvProtocol.TV_SOUND_DOWN + "\n").getBytes("utf-8"));
						}else if(msg.what == HandlerProtocol.TV_CHANNEL_UP){
							os.write((TvProtocol.TV_CHANNEL_UP + "\n").getBytes("utf-8"));
						}else if(msg.what == HandlerProtocol.TV_CHANNEL_DOWN){
							os.write((TvProtocol.TV_CHANNEL_DOWN + "\n").getBytes("utf-8"));
						}
						}
					isSelectedEquip = false;

				} catch (IOException e) {
					e.printStackTrace();

					}

				}
		};
		Looper.loop();
//			String content = null;
//			//while ((content = readFromClient()) != null) {
//			//synchronized (content) {
////					if (content.equals(mEquipCode)) {
//						try {
//
//							OutputStream os = s.getOutputStream();
//							os.write((mBulbProtocol + "\n").getBytes("utf-8"));
//						} catch (SocketException e) {
//							e.printStackTrace();
//							System.out.println(BulbServerService.socketList);
//						}
//					} else {
//						//销毁线程
//
//
//					}
		//	}
		//}

	}

	private String readFromClient()
	{
		try
		{
			return br.readLine();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			ServerService.socketList.remove(s);
		}
		return null;
	}
}
