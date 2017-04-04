package com.smart.home.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;

/**
 * Created by Panmingwei on 14-4-15.
 */
public class SntpClock {
    public static final String[] NTP_SERVERS = new String[]{
            "cn.pool.ntp.org",
            "hk.pool.ntp.org",
            "tw.pool.ntp.org",
            "jp.pool.ntp.org",
            "pool.ntp.org"
    };

    public static final String PREF_KEY_TIME_OFFSET = "time_offset";
    public static long offset;

    public static long currentTimeMillis() {
        return System.currentTimeMillis() + offset;
//        return System.currentTimeMillis() ;
    }

    private static AsyncTask<Void, Void, Object[]> task;

    public static void syncTime(final Context context) {
        final SharedPreferences statusPrefs = context.getSharedPreferences("status", Context.MODE_PRIVATE);
        if (statusPrefs.contains(PREF_KEY_TIME_OFFSET)) {
            offset = statusPrefs.getLong(PREF_KEY_TIME_OFFSET, 0L);
        }
        if (task == null) {
            task = new AsyncTask<Void, Void, Object[]>() {
                @Override
                protected Object[] doInBackground(Void... params) {
                    for (String host : NTP_SERVERS) {
                        SntpClient client = new SntpClient();
                        if (client.requestTime(host, 10000)) {
                            return new Object[]{host, client.getClockOffset()};
                        }
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Object[] objects) {
                    super.onPostExecute(objects);
                    if (objects != null) {
                        String host = (String) objects[0];
                        Long offset = (Long) objects[1];
                        SntpClock.offset = offset;
                        SPUtils.apply(statusPrefs.edit().putLong(PREF_KEY_TIME_OFFSET, offset));
                    }
                    task = null;
                }
            };
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                task.execute();
            }
        }
    }
}
