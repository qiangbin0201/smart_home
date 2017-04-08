package com.smart.home;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;


import java.io.File;
import java.util.Timer;
import java.util.TimerTask;


public class TTYCApplication extends Application {
    private static final String TAG = TTYCApplication.class.getSimpleName();
    private static TTYCApplication sInstance = null;
    public static Context mContext;
//    private LogInterf mLogInterf;

    public static TTYCApplication getInstance() {
        return sInstance;
    }

    //3天检查一次
    private long period = 1000 * 60 * 60 * 24 * 3;

    @Override
    public void onCreate() {
        super.onCreate();
//        mContext = this;
//        initPush();
//        String processName = getCurProcessName(this);
//        AppLogUtils.w("processName->" + processName);
//        if (!TextUtils.isEmpty(processName)) {
//            //主进程初始化，初始化有前后依赖关系，语句的顺序不要修改
//            if (processName.equalsIgnoreCase(getPackageName())) {
//                sInstance = this;
//                AppLogUtils.i("程序开始");
////                if (BuildConfig.DEBUG) {
////                    CrashHandler.getInstance();
////                }
//                //执行初始化第三方控件操作
//                initThirdPart();
//                //执行初始化业务操作类
//                initBusiness();
//                AppLogUtils.i("完成应用程序初始化操作");
//                //初始化广告库
////                AdSdk.init(mContext, "appid1487215363693");
////                //初始化广告流
////                FolderConfig.init(mContext, "appid1481598327822");
////                if (TextUtils.equals(getString(R.string.need_icon), "0")) {
////                    timer.schedule(task, 1000, period);
//                }
            }
        }
//        mContext.startService(new Intent(mContext.getApplicationContext(), InterAdChargeService.class));
//        sendBroadcast(getShortcutToDesktopIntent(mContext));



    /**
     * 执行初始化业务操作类
     */
//    private void initBusiness() {
//        try {
//            AppLogUtils.i("执行初始化业务操作类");
//            //初始化基础信息
//            BaseInfo.init(this);
//
//            //初始化业务辅助类
//            AppProxy.getInstance().setup(this);
//
//            UserScoreManager.getInstance().setContext(this);
//            // boolean isEnabled = Preferences.isUserScoreEnabled(this);
//            UserScoreManager.setUserScoreEnabled(false);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 执行初始化第三方控件操作
//     */
//    private void initThirdPart() {
//        try {
//            AppLogUtils.i("执行初始化第三方控件操作");
//
//            //初始化bugly
////            CrashReport.initCrashReport(this);
//
//            //初始化Rxjava异常hook,必须在RxJava第一次使用之前调用，否则hook无效
////            ObservableErrorHook.init();
//            //同步服务器时间
//            SntpClock.syncTime(this);
//
//            initImageLoader(this);
//
//            initFresco();
//
//            initUmeng();
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void initFresco() {
////        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
//////                .setBitmapMemoryCacheParamsSupplier(bitmapCacheParamsSupplier)
//////                .setCacheKeyFactory(cacheKeyFactory)
//////                .setEncodedMemoryCacheParamsSupplier(encodedCacheParamsSupplier)
//////                .setExecutorSupplier(executorSupplier)
//////                .setImageCacheStatsTracker(imageCacheStatsTracker)
//////                .setMainDiskCacheConfig(mainDiskCacheConfig)
//////                .setMemoryTrimmableRegistry(memoryTrimmableRegistry)
//////                .setNetworkFetchProducer(networkFetchProducer)
//////                .setPoolFactory(poolFactory)
//////                .setProgressiveJpegConfig(progressiveJpegConfig)
//////                .setRequestListeners(requestListeners)
//////                .setSmallImageDiskCacheConfig(smallImageDiskCacheConfig)
////                .build();
////        Fresco.initialize(this, confi);
//
////        if (BuildConfig.DEBUG) {
////            // for fresco loading log
////            Set<RequestListener> requestListeners = new HashSet<>();
////            requestListeners.add(new RequestLoggingListener());
////            ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
////                    // other setters
////                    .setRequestListeners(requestListeners)
////                    .build();
////            Fresco.initialize(this, config);
////            FLog.setMinimumLoggingLevel(FLog.VERBOSE);
////        } else {
////        Fresco.initialize(this);
////        }
//    }
//
////    @Override
////    public void onConfigurationChanged(Configuration newConfig) {
////        super.onConfigurationChanged(newConfig);
////        BaseInfo.initDisplay(this);
////    }
//
//    String getCurProcessName(Context context) {
//        int pid = android.os.Process.myPid();
//        ActivityManager mActivityManager = (ActivityManager) context
//                .getSystemService(Context.ACTIVITY_SERVICE);
//        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
//                .getRunningAppProcesses()) {
//            if (appProcess.pid == pid) {
//                return appProcess.processName;
//            }
//        }
//        return null;
//    }
//
//
//    public static void initImageLoader(Context context) {
//        // This configuration tuning is widget. You can tune every option, you may tune some of them,
//        // or you can create default configuration by
//        //  ImageLoaderConfiguration.createDefault(this);
//        // method.
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
//                .threadPriority(Thread.NORM_PRIORITY - 2)
//                .denyCacheImageMultipleSizesInMemory()
//                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
//                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
//                .tasksProcessingOrder(QueueProcessingType.LIFO)
//                .build();
//
//        // Initialize ImageLoader with configuration.
//        ImageLoader.getInstance().init(config);
//    }
//
//
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(newBase);
//        MultiDex.install(this);
//        DaemonUtils.initDaemonClient(newBase);
//    }
//
//
//    private void initUmeng() {
//        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
//
//        /**
//         * 1. MobclickAgent.onResume()  和MobclickAgent.onPause()  方法是用来统计应用时长的(也就是Session时长,当然还包括一些其他功能)
//         * 2.MobclickAgent.onPageStart() 和MobclickAgent.onPageEnd() 方法是用来统计页面跳转的
//         * 在仅有Activity的应用中，SDK 自动帮助开发者调用了 2  中的方法，并把Activity 类名作为页面名称统计。
//         * 但是在包含fragment的程序中我们希望统计更详细的页面，所以需要自己调用方法做更详细的统计。
//         * 首先，需要在程序入口处，调用 MobclickAgent.openActivityDurationTrack(false)
//         * 禁止默认的页面统计方式，这样将不会再自动统计Activity。
//         */
//        // MobclickAgent.openActivityDurationTrack(false);
//    }
//
//    public static String getConfigDir(Context context) {
//        String dir = getAppDir(context) + File.separator + "config";
//        FileUtils.createDir(dir);
//        return dir;
//    }
//
//    public static String getAppDir(Context context) {
//        StringBuilder sb = new StringBuilder();
//        if (FileUtils.checkSDCard()) {
//            sb.append(Environment.getExternalStorageDirectory().getPath());
//        } else {
//            sb.append(context.getCacheDir().getPath());
//        }
//
//        String configDir = sb.append(File.separator).append(context.getResources().getString(R.string.app_dir)).toString();
//        FileUtils.createDir(configDir);
//        return configDir;
//    }
//
//    public static String getPicDir(Context context) {
//        StringBuilder sb = new StringBuilder();
//        if (FileUtils.checkSDCard()) {
//            sb.append(Environment.getExternalStorageDirectory().getPath());
//        } else {
//            sb.append(context.getCacheDir().getPath());
//        }
//
//        String configDir = sb.append(File.separator).append(context.getResources().getString(R.string.picture_dir))
//                .append(File.separator).append(context.getResources().getString(R.string.app_dir)).toString();
//        FileUtils.createDir(configDir);
//        return configDir;
//    }
//
//    public static String getZaisoResourceDir(Context context) {
//        String dir = new StringBuilder()
//                .append(getConfigDir(context))
//                .append(File.separator)
//                .append("zaiso").toString();
//        FileUtils.createDir(dir);
//        FileUtils.createNoMedia(dir);
//        return dir;
//    }
//
//    public static String getCacheDir(Context context) {
//        String dir = getAppDir(context) + File.separator + "cache";
//        FileUtils.createDir(dir);
//        return dir;
//    }
//
//    public static String getPhotoStoreDir(Context context) {
//        String dir = getAppDir(context) + File.separator + "photo";
//        FileUtils.createDir(dir);
//        return dir;
//    }
//
//    public static String getLocalWebURL() {
//        return "file://" + TTYCApplication.getZaisoResourceDir(TTYCApplication.getInstance());
////        return "file:///android_asset/zaiso";
//    }
//
//    private void initPush() {
//        PushAgent mPushAgent = PushAgent.getInstance(this);
//        //注册推送服务 每次调用register都会回调该接口
//        mPushAgent.register(new IUmengRegisterCallback() {
//            @Override
//            public void onSuccess(String deviceToken) {
//                UmLog.i(TAG, "device token: " + deviceToken);
////                sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//                UmLog.i(TAG, "register failed: " + s + " " + s1);
////                sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
//            }
//        });
//    }
//
//    public Intent getShortcutToDesktopIntent(Context context) {
//        Intent intent = new Intent();
//        intent.setClass(context, SplashscreenActivity.class);
//        /*以下两句是为了在卸载应用的时候同时删除桌面快捷方式*/
//        intent.setAction("android.intent.action.MAIN");
//        intent.addCategory("android.intent.category.LAUNCHER");
//
//        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
//        // 不允许重建
//        shortcut.putExtra("duplicate", false);
//        // 设置名字
//        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(R.string.app_name));
//        // 设置图标
//        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, R.drawable.icon));
//        // 设置意图和快捷方式关联程序
//        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
//
//        return shortcut;
//
//    }
//
//    public boolean hasInstallShortcut(Context context) {
//        boolean hasInstall = false;
//
//        String AUTHORITY = "com.android.launcher.settings";
//        int systemversion = Build.VERSION.SDK_INT;
//         /*大于8的时候在com.android.launcher2.settings 里查询（未测试）*/
//        if (systemversion >= 8) {
//            AUTHORITY = "com.android.launcher2.settings";
//        }
//        Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/favorites?notify=true");
//
//        Cursor cursor = context.getContentResolver().query(CONTENT_URI,
//                new String[]{"title"}, "title=?",
//                new String[]{context.getString(R.string.app_name)}, null);
//
//        if (cursor != null && cursor.getCount() > 0) {
//            hasInstall = true;
//        }
//
//        return hasInstall;
//    }
//
//    TimerTask task = new TimerTask() {
//        @Override
//        public void run() {
//            if (!hasInstallShortcut(mContext)) {
//                sendBroadcast(getShortcutToDesktopIntent(mContext));
//            }
//        }
//    };
//    Timer timer = new Timer();

