package com.smart.home.presenter;

import android.os.Handler;

import android.os.Looper;
import android.os.Message;

import com.smart.home.model.BulbProtocol;
import com.smart.home.service.BulbServerService;

import java.io.*;
import java.net.*;


public class ServerThread extends Thread
{

	//设备唯一标识符
	private String mEquipCode;

	public static Handler rvHandler;

	private boolean isSelectedEquip = true;

	private Socket s = null;

	private BufferedReader br = null;
	private OutputStream os;

	public ServerThread(Socket s, String equipCode)
		throws IOException
	{
		this.s = s;
		br = new BufferedReader(new InputStreamReader(
			s.getInputStream() , "utf-8"));
		os = s.getOutputStream();

		mEquipCode = equipCode;
	}
	public void run()
	{
//		String content = null;
//		while ((content = readFromClient()) != null && content.equals("qb")) {
//			isSelectedEquip = true;
//		}

		Looper.prepare();

		rvHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				try {
					if (isSelectedEquip) {
						if (msg.what == 0x111) {
							os.write((BulbProtocol.BULB_ON + "\n").getBytes("utf-8"));
						} else if (msg.what == 0x112) {
							os.write((BulbProtocol.BULB_OFF + "\n").getBytes("utf-8"));
						} else if (msg.what == 0x113) {
							os.write((BulbProtocol.BRIGHTNESS_UP + "\n").getBytes("utf-8"));
						} else if (msg.what == 0x114) {
							os.write((BulbProtocol.BRIGHTNESS_DOWN + "\n").getBytes("utf-8"));
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
			BulbServerService.socketList.remove(s);
		}
		return null;
	}
}
