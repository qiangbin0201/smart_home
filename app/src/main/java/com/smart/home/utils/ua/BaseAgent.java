package com.smart.home.utils.ua;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

class BaseAgent {

    protected synchronized void onResume(Context context) {
        MobclickAgent.onResume(context);
    }

    protected synchronized void onPause(Context context) {
        MobclickAgent.onPause(context);
    }

    protected synchronized void onEvent(Context context, String arg1) {
        MobclickAgent.onEvent(context, arg1);
    }

    protected synchronized void onEvent(Context context, String arg1, String arg2) {
        MobclickAgent.onEvent(context, arg1, arg2);
    }

    protected synchronized void onEvent(Context context, String arg1, HashMap<String, String> arg2) {
        MobclickAgent.onEvent(context, arg1, arg2);
    }

    protected synchronized void onEventValue(Context context, String id, HashMap<String, String> m, int du) {
        MobclickAgent.onEventValue(context, id, m, du);
    }
}
