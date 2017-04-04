package com.smart.home.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;

import com.bigbang.news.R;
import com.bigbang.news.TTYCApplication;
import com.bigbang.news.page.home.activity.HomeActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by leonlee on 14-9-9.
 * To better product,to better world
 */
public class UpdateService extends Service {

    //文件存储
    private File updateDir = Environment.getExternalStorageDirectory();
    private File updateFile = null;

    //通知栏
    private NotificationManager mNotificationManager = null;
    private NotificationCompat.Builder builder;
    //通知栏跳转Intent
    private PendingIntent mPendingIntent = null;
    private String url;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //获取传值
        url = intent.getStringExtra("url");

        //创建文件
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            updateFile = new File(TTYCApplication.getAppDir(this) + "/update/", "bbnews.apk");
        }

        this.mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.icon);
        builder.setContentTitle(getString(R.string.app_label));
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, HomeActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pi);
        builder.setProgress(100, 0, false);
        builder.setTicker("开始下载");
        builder.setDefaults(Notification.DEFAULT_SOUND);
        //发出通知
        mNotificationManager.notify(0, builder.build());

        //开启一个新的线程下载，如果使用Service同步下载，会导致ANR问题，Service本身也会阻塞
        new Thread(new updateRunnable()).start();//这个是下载的重点，是下载的过程

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class updateRunnable implements Runnable {
        Message message = updateHandler.obtainMessage();

        public void run() {
            message.what = DOWNLOAD_COMPLETE;
            try {
                if (!updateFile.exists()) {
                    updateFile.createNewFile();
                }
                long downloadSize = downloadUpdateFile(url, updateFile);
                if (downloadSize > 0) {
                    //下载成功
                    updateHandler.sendMessage(message);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                message.what = DOWNLOAD_FAIL;
                //下载失败
                updateHandler.sendMessage(message);
            }
        }
    }

    public long downloadUpdateFile(String downloadUrl, File saveFile) throws Exception {
        int downloadCount = 0;
        int currentSize = 0;
        long totalSize = 0;
        int updateTotalSize = 0;

        HttpURLConnection httpConnection = null;
        InputStream is = null;
        FileOutputStream fos = null;

        try {
            URL url = new URL(downloadUrl);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestProperty("User-Agent", "PacificHttpClient");
            if (currentSize > 0) {
                httpConnection.setRequestProperty("RANGE", "bytes=" + currentSize + "-");
            }
            httpConnection.setConnectTimeout(10000);
            httpConnection.setReadTimeout(20000);
            updateTotalSize = httpConnection.getContentLength();
            if (httpConnection.getResponseCode() == 404) {
                throw new Exception("fail!");
            }
            is = httpConnection.getInputStream();
            fos = new FileOutputStream(saveFile);
            byte buffer[] = new byte[1024];
            int readsize = 0;
            while ((readsize = is.read(buffer)) > 0) {
                fos.write(buffer, 0, readsize);
                totalSize += readsize;
                //为了防止频繁的通知导致应用吃紧，百分比增加10才通知一次
                if ((downloadCount == 0) || (int) (totalSize * 100 / updateTotalSize) - 4 > downloadCount) {
                    downloadCount += 4;
                    int progress = (int) totalSize * 100 / updateTotalSize;
                    builder.setContentText("下载进度：" + progress + "%");
                    builder.setProgress(100, progress, false);
                    builder.setDefaults(0);
                    Notification notification = builder.build();
                    notification.flags = Notification.FLAG_NO_CLEAR;
                    mNotificationManager.notify(0, notification);
                }
            }
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
            if (is != null) {
                is.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
        return totalSize;
    }

    //下载状态
    private final static int DOWNLOAD_COMPLETE = 0;
    private final static int DOWNLOAD_FAIL = 1;

    private Handler updateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD_COMPLETE: {
                    //点击安装PendingIntent --> 直接安装
                    Uri uri = Uri.fromFile(updateFile);
                    Intent installIntent = new Intent(Intent.ACTION_VIEW);
                    installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    installIntent.setDataAndType(uri, "application/vnd.android.package-archive");

                    mPendingIntent = PendingIntent.getActivity(UpdateService.this, 0, installIntent, 0);
                    builder.setContentIntent(mPendingIntent);
                    builder.setProgress(0, 0, true);
                    builder.setContentText("下载完成,点击安装");
                    Notification notification = builder.build();
                    notification.flags = Notification.FLAG_AUTO_CANCEL;
                    notification.defaults = Notification.DEFAULT_SOUND;
                    mNotificationManager.notify(0, notification);

                    startActivity(installIntent);
                    break;
                }
                case DOWNLOAD_FAIL:
                    //下载失败
                    builder.setProgress(0, 0, true);
                    builder.setContentText("下载失败");
                    mNotificationManager.notify(0, builder.build());
                    break;
                default:
                    break;
            }
            //停止服务
            stopSelf();
        }

    };

}
