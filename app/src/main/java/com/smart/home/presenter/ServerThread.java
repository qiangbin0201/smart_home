package com.smart.home.presenter;

import com.smart.home.service.BulbServerService;

import java.io.*;
import java.net.*;


public class ServerThread implements Runnable
{

	//设备唯一标识符
	private String mEquipCode;

	//传输指令
	private String mBulbProtocol;

	Socket s = null;
	BufferedReader br = null;
	public ServerThread(Socket s, String equipCode, String bulbProtocol)
		throws IOException
	{
		this.s = s;
		br = new BufferedReader(new InputStreamReader(
			s.getInputStream() , "utf-8"));

		mEquipCode = equipCode;
		mBulbProtocol = bulbProtocol;
	}
	public void run()
	{
		try
		{
			String content = null;
				while ((content = readFromClient()) != null) {
					synchronized (content) {
					if (content.equals(mEquipCode)) {
						try {

							OutputStream os = s.getOutputStream();
							os.write((mBulbProtocol + "\n").getBytes("utf-8"));
						} catch (SocketException e) {
							e.printStackTrace();
							System.out.println(BulbServerService.socketList);
						}
					} else {
						//销毁线程

					}
				}
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
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
