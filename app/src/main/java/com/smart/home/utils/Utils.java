package com.smart.home.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.bigbang.news.BuildConfig;
import com.bigbang.news.TTYCApplication;

import org.apache.http.util.EncodingUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Created by 冯子杰(fengzijie@machine.com)
 * Date: 14-9-4
 */
public class Utils {
    /**
     * 隐藏软键盘
     *
     * @param context context
     */
    public static boolean hideKeyboard(Activity context) {
        return hideKeyboard(context, context.getCurrentFocus());
    }

    public static boolean hideKeyboard(Context context, View currentFocus) {
        InputMethodManager localInputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (currentFocus != null) {
            IBinder localIBinder = currentFocus.getWindowToken();
            if (localIBinder != null) {
                return localInputMethodManager.hideSoftInputFromWindow(localIBinder, 0);
            } else {
                AppLogUtils.e("localIBinder is null");
                return false;
            }
        } else {
            AppLogUtils.e("current focus is null");
            return false;
        }
    }

    //隐藏虚拟键盘
    public static void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
            /**SHOW_IMPLICIT Flag for showSoftInput(View, int) to indicate that this is an implicit request to onShow the input window, not as the result of a direct request by the user. The window may not be shown in this case.**/
            /**HIDE_NOT_ALWAYS Flag for hideSoftInputFromWindow(IBinder, int) to indicate that the soft input window should normally be hidden, unless it was originally shown with SHOW_FORCED.**/
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } else {
            AppLogUtils.e("hideKeyboard imm is not active");
        }
    }


    /**
     * 显示软键盘
     *
     * @param context context
     * @param edit    控件
     */
    public static void showKeyboard(Context context, EditText edit) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edit, InputMethodManager.RESULT_SHOWN);
        /**SHOW_FORCED Flag for showSoftInput(View, int) to indicate that the user has forced the input method open (such as by long-pressing menu) so it should not be closed until they explicitly do so.**/
        /**HIDE_IMPLICIT_ONLY Flag for hideSoftInputFromWindow(IBinder, int) to indicate that the soft input window should only be hidden if it was not explicitly shown by the user.**/
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public static void toggleKeyboard(Context context) {
        InputMethodManager m = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private static long lastClickTime = 0;

    public static boolean isFastDoubleClick() {
        long currentClickTime = System.currentTimeMillis();
        long duration = currentClickTime - lastClickTime;
        if (0 < duration && duration < 800) {
            return true;
        }
        lastClickTime = currentClickTime;
        return false;
    }

    public static int dpToPx(Resources res, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
    }

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 对手机号中间4位数打*
     *
     * @param mobile
     * @return
     */
    public static String markMobile(String mobile) {
        if (TextUtils.isEmpty(mobile)) return "";
        if (mobile.length() < 8) return mobile;
        return mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length());
    }

    public static String loadCachedDeviceId() {
        File f = TTYCApplication.mContext.getFilesDir();
        String androidId = loadCachedDeviceIdFromDirectory(f);
        if (androidId != null && androidId.length() > 10) return androidId;

        f = Environment.getExternalStorageDirectory();
        androidId = loadCachedDeviceIdFromDirectory(f);
        if (androidId != null && androidId.length() > 10) return androidId;

        f = Environment.getRootDirectory();
        androidId = loadCachedDeviceIdFromDirectory(f);
        if (androidId != null && androidId.length() > 10) return androidId;

        f = Environment.getDataDirectory();
        androidId = loadCachedDeviceIdFromDirectory(f);
        if (androidId != null && androidId.length() > 10) return androidId;

        f = Environment.getDownloadCacheDirectory();
        androidId = loadCachedDeviceIdFromDirectory(f);
        if (androidId != null && androidId.length() > 10) return androidId;

        f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        androidId = loadCachedDeviceIdFromDirectory(f);
        if (androidId != null && androidId.length() > 10) return androidId;

        f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS);
        androidId = loadCachedDeviceIdFromDirectory(f);
        if (androidId != null && androidId.length() > 10) return androidId;

        f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES);
        androidId = loadCachedDeviceIdFromDirectory(f);
        if (androidId != null && androidId.length() > 10) return androidId;

        f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS);
        androidId = loadCachedDeviceIdFromDirectory(f);
        if (androidId != null && androidId.length() > 10) return androidId;

        f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS);
        androidId = loadCachedDeviceIdFromDirectory(f);
        if (androidId != null && androidId.length() > 10) return androidId;

        f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        androidId = loadCachedDeviceIdFromDirectory(f);
        if (androidId != null && androidId.length() > 10) return androidId;

        f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        androidId = loadCachedDeviceIdFromDirectory(f);
        if (androidId != null && androidId.length() > 10) return androidId;

        f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        androidId = loadCachedDeviceIdFromDirectory(f);
        if (androidId != null && androidId.length() > 10) return androidId;

        f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        androidId = loadCachedDeviceIdFromDirectory(f);
        if (androidId != null && androidId.length() > 10) return androidId;

        return null;
    }

    public static void cacheDeviceId(String androidId) {
        File f = TTYCApplication.mContext.getFilesDir();
        cacheDeviceIdToDirectory(f, androidId);

        f = Environment.getExternalStorageDirectory();
        cacheDeviceIdToDirectory(f, androidId);

        f = Environment.getRootDirectory();
        cacheDeviceIdToDirectory(f, androidId);

        f = Environment.getDataDirectory();
        cacheDeviceIdToDirectory(f, androidId);

        f = Environment.getDownloadCacheDirectory();
        cacheDeviceIdToDirectory(f, androidId);

        f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        cacheDeviceIdToDirectory(f, androidId);

        f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS);
        cacheDeviceIdToDirectory(f, androidId);

        f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES);
        cacheDeviceIdToDirectory(f, androidId);

        f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS);
        cacheDeviceIdToDirectory(f, androidId);

        f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS);
        cacheDeviceIdToDirectory(f, androidId);

        f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        cacheDeviceIdToDirectory(f, androidId);

        f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        cacheDeviceIdToDirectory(f, androidId);

        f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        cacheDeviceIdToDirectory(f, androidId);

        f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        cacheDeviceIdToDirectory(f, androidId);
    }

    private static String loadCachedDeviceIdFromDirectory(File dir) {
        try {
            String filePath = dir.getAbsolutePath() + "/.machineid";
            File file = new File(filePath);
            if (!file.exists()) return null;

            FileInputStream fis = new FileInputStream(file);

            int length = fis.available();

            byte[] buffer = new byte[length];
            int readLength = fis.read(buffer);
            fis.close();

            if (length == 0 || length != readLength) {
                return null;
            }

            return EncodingUtils.getString(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void cacheDeviceIdToDirectory(File dir, String androidId) {
        try {
            if (dir == null || !dir.exists()) return;

            String filePath = dir.getAbsolutePath() + "/.machineid";
            File file = new File(filePath);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(EncodingUtils.getBytes(androidId, "UTF-8"));
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getStringFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(TTYCApplication.getInstance().getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            bufReader.close();
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getCurrentFunctionName(Class<?> classType) {
        if (classType != null) {
            return getCurrentFunctionName(classType.getSimpleName(), 5);
        } else {
            if (BuildConfig.DEBUG) {
                Log.e("getCurrentFunctionName", " classType is null");
            }
            return "getCurrentFunctionName classType is null";
        }
    }

    /**
     * default use 4 is okay
     *
     * @param clazzName
     * @param stackIndex
     * @return
     */
    public static String getCurrentFunctionName(String clazzName, int... stackIndex) {
        String funcName = "error";
        if (BuildConfig.DEBUG) {
            try {
                java.util.Map<Thread, StackTraceElement[]> ts = Thread
                        .getAllStackTraces();
                StackTraceElement[] ste = ts.get(Thread.currentThread());
                if (stackIndex == null || stackIndex.length == 0) {
                    funcName = clazzName + " " + ste[4].getMethodName();
                } else {
                    funcName = clazzName + " " + ste[stackIndex[0]].getMethodName();
                }
            } catch (Throwable t) {
                t.printStackTrace();
                AppLogUtils.e("getCurrentFunctionName error");
            } finally {
                return funcName;
            }
        } else {
            return "not in debug";
        }
    }

    public static void showLongLog(String tag, String responseBody) {
        try {
            String responseStr = responseBody == null ? "" : responseBody;
            int maxShow = 2500;
            int len = responseStr.length();
            int i = 0;
            for (; i < len / maxShow; i++) {
                if (BuildConfig.DEBUG) {
                    Log.i(tag, responseStr.substring(maxShow * i, maxShow * (i + 1)));
                }
            }
            if (BuildConfig.DEBUG) {
                Log.i(tag, responseStr.substring(maxShow * i));
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static void showLongLogAlways(String tag, String responseBody) {
        try {
            String responseStr = responseBody == null ? "" : responseBody;
            int maxShow = 2500;
            int len = responseStr.length();
            int i = 0;
            for (; i < len / maxShow; i++) {
                Log.e(tag, responseStr.substring(maxShow * i, maxShow * (i + 1)));
            }
            Log.e(tag, responseStr.substring(maxShow * i));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
