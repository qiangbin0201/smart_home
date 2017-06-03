package com.smart.home.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import java.util.Properties;

/**
 * 跟网络相关的工具类
 */
public class NetWorkUtil {
	/**
	 * 获取IP
	 * 
	 * @param con
	 * @return
	 */
	public static String getIp(Context con) {
		WifiManager wifiManager = (WifiManager) con
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();

		// 格式化IP address，例如：格式化前：1828825280，格式化后：192.168.1.109
		String ip = String.format("%d.%d.%d.%d", (ipAddress & 0xff),
				(ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff),
				(ipAddress >> 24 & 0xff));
		return ip;

	}

	/**
	 * 获得网络状态信息
	 * 
	 * @param context
	 * @return
	 */
	public static NetworkInfo getActiveNetwork(Context context) {
		if (context == null)
			return null;
		ConnectivityManager mConnMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (mConnMgr == null)
			return null;
		NetworkInfo aActiveInfo = mConnMgr.getActiveNetworkInfo(); // 获取活动网络连接信息
		return aActiveInfo;
	}

	

	/**
	 * 判断是否连接网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 判断是否连接Mobile网络
	 *
	 * @param context
	 * @return
	 */
	public static Boolean isMobile(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager == null) {
			return false;
		}
		NetworkInfo info = connManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (info == null) {
			return false;
		}
		State state = info.getState();
		if (State.CONNECTED == state) {
			return true;
		}
		return false;
	}
	/**
	 * 判断当前网络是否WIFI
	 * @param context
	 * @return
	 */
	public static boolean isWIFI(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connManager.getAllNetworkInfo();
        if (networkInfos != null) {

            for (NetworkInfo info : networkInfos) {
                NetworkInfo.State state = null;
                state = info.getState();

                if (state == NetworkInfo.State.CONNECTED && info.getType() == ConnectivityManager.TYPE_WIFI) {
                    return true;
                }
            }
        }
        return false;
    }

	/**
	 * 获得当前网络状态
	 * 
	 * @param context
	 * @return
	 */
	public static Boolean getNetWorkType(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivity.getActiveNetworkInfo();
		if (info != null && info.getType() == ConnectivityManager.TYPE_MOBILE) {
			switch (info.getSubtype()) {
			case TelephonyManager.NETWORK_TYPE_1xRTT:
				return false; // ~ 50-100 kbps
			case TelephonyManager.NETWORK_TYPE_CDMA:
				return false; // ~ 14-64 kbps
			case TelephonyManager.NETWORK_TYPE_EDGE:
				return false; // ~ 50-100 kbps
			case TelephonyManager.NETWORK_TYPE_EVDO_0:
				return true; // ~ 400-1000 kbps
			case TelephonyManager.NETWORK_TYPE_EVDO_A:
				return true; // ~ 600-1400 kbps
			case TelephonyManager.NETWORK_TYPE_GPRS:
				return false; // ~ 100 kbps
			case TelephonyManager.NETWORK_TYPE_HSDPA:
				return true; // ~ 2-14 Mbps
			case TelephonyManager.NETWORK_TYPE_HSPA:
				return true; // ~ 700-1700 kbps
			case TelephonyManager.NETWORK_TYPE_HSUPA:
				return true; // ~ 1-23 Mbps
			case TelephonyManager.NETWORK_TYPE_UMTS:
				return true; // ~ 400-7000 kbps
			case TelephonyManager.NETWORK_TYPE_UNKNOWN:
				return false;
			default:
				return false;
			}
			// if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_GPRS
			// || info.getSubtype() == TelephonyManager.NETWORK_TYPE_CDMA
			// || info.getSubtype() == TelephonyManager.NETWORK_TYPE_EDGE) {
			// System.out.println("mobile connected");
			// } else {
			// System.out.println("type:" + info.getSubtype());
			// System.out.println("not mobile");
			// }
		} else
			System.out.println("not mobile connected");
		return false;
	}
	
	 
    private static Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");


}
