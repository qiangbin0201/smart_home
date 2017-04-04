package com.smart.home.utils;

/**
 * Created by lish on 16/8/17.
 */
public class ShakeUtils {

    private static long mLastClickTime = 0;
    private final static int SPACE_TIME = 500;


    public synchronized static boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        boolean isClick = false;
        if (currentTime - mLastClickTime <= SPACE_TIME) {
            isClick = true;
        }
        mLastClickTime = currentTime;
        return isClick;
    }
}
