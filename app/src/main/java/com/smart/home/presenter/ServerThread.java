package com.smart.home.presenter;

import android.os.Handler;
import android.os.Message;

import com.smart.home.model.HandlerProtocol;
import com.smart.home.service.ServerService;
import com.smart.home.utils.RadixUtil;

import java.io.*;
import java.net.*;


public class ServerThread extends Thread
{

	//设备唯一标识符
	private String mEquipCode;

	public static Handler rvHandler;

	private static boolean isSelectedEquip = true;

	private Handler mUiHandler;

	private Socket s = null;

	private BufferedReader br = null;

	private static OutputStream os;

	private boolean isFirstMessage = true;

	public ServerThread(Socket s, String equipCode, Handler mUiHandler)
		throws IOException
	{
		this.s = s;
		br = new BufferedReader(new InputStreamReader(s.getInputStream()));
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
                        while ((content = readFromClient()) != null ) {
							if(isFirstMessage){
								if(!content.equals(mEquipCode)) {
									s.close();
									br.close();
									os.close();
								}else {
									isSelectedEquip = true;
									isFirstMessage = false;
								}
							}
							if(isSelectedEquip) {
								Message message = new Message();
								message.what = HandlerProtocol.CONTROL_SUCCESS;
								message.obj = content;
								mUiHandler.sendMessage(message);
							}

                        }
//					mUiHandler.sendEmptyMessage(HandlerProtocol.NET_NOT_CONNECT);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}.start();

	}

    public static void sendToClient(String content){
		if(isSelectedEquip) {
			String temp = RadixUtil.intString2hexString(content);
			byte[] buf = RadixUtil.hexString2Bytes(temp);
			try {
				os.write(buf);
			} catch (IOException e) {
				e.printStackTrace();
			}
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
			ServerService.socketList.remove(s);
		}
		return null;
	}
}
