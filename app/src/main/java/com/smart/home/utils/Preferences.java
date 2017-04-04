package com.smart.home.utils;

import android.content.Context;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.bigbang.news.page.news.activity.BaseSearchActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by admin on 15/9/7.
 */
public class Preferences {

    private static final String ZAISO_RESOURCE_VERSION = "zaiso_resource_version";
    private static final String AUTO_LOGIN_USER_ID = "auto_login_user_id";
    private static final String LOGIN_FIRST_RUNNING = "first_running";
    private static final String FIRST_ENTER_MY_COLLECT = "first_enter_my_collect";

    private static final String SEARCH_HISTORY_KEYS = "search_history_keys";

    private static final String MANAGE_UNLOGIN_CHANNELS = "manage_unlogin_channels";

    private static final String USER_SCORE_PREFIX = "user_score_";
    /**
     * 是否开启用户积分
     */
    private static final String USER_SCORE_ENABLE = "user_score_enable";

    private static final String APP_CODE = "app_code";

    /**
     * 获取未登陆时默认的用户id
     *
     * @param context
     * @return
     */
    public static String getAutoLoginUserId(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(AUTO_LOGIN_USER_ID, null);
    }

    /**
     * 设置未登陆时默认的用户id
     *
     * @param context
     * @param userId
     */
    public static void setAutoLoginUserId(Context context, String userId) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(AUTO_LOGIN_USER_ID, userId).apply();
    }


    /**
     * 获取在搜资源的版本
     *
     * @param context
     * @return
     */
    public static String getZaisoResourceVersion(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(ZAISO_RESOURCE_VERSION, "1.0.0");
    }

    /**
     * 设置在搜资源的版本
     *
     * @param context
     * @param value
     */
    public static void setZaisoResourceVersion(Context context, String value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(ZAISO_RESOURCE_VERSION, value).apply();
    }

    public static boolean isFirstLoginRunning(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(LOGIN_FIRST_RUNNING, true);
    }

    public static void setFirstLoginRunning(Context context, boolean isFirst) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(LOGIN_FIRST_RUNNING, isFirst).apply();
    }

    public static boolean isFirstEnterFavorite(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(FIRST_ENTER_MY_COLLECT, true);
    }

    public static void setFirstEnterMyCFavorite(Context context, boolean isFirst) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(FIRST_ENTER_MY_COLLECT, isFirst).apply();
    }

    /**
     * 未登录频道列表是否未被排过序
     *
     * @param context
     * @return
     */
    public static boolean isUnLoginChannelManaged(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(MANAGE_UNLOGIN_CHANNELS, false);
    }

    /**
     * 设置未登陆频道列表的排序状态
     *
     * @param context
     * @param managed true 被排过序 false 未被排过序
     */
    public static void setUnLoginChannelManage(Context context, boolean managed) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(MANAGE_UNLOGIN_CHANNELS, managed).apply();
    }


    /**
     * 设置搜索历史记录
     *
     * @param context
     * @param keys
     */
    public static void setSearchHistoryKeys(Context context, String keys, String prefix) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(prefix + SEARCH_HISTORY_KEYS, keys).apply();
    }


    /**
     * 获取搜索历史记录
     *
     * @param context
     * @return
     */
    public static List<String> getSearchHistoryKeys(Context context, String prefix) {
        String content = PreferenceManager.getDefaultSharedPreferences(context).getString(prefix + SEARCH_HISTORY_KEYS, null);
        if (!TextUtils.isEmpty(content)) {
            String[] keys = content.split(BaseSearchActivity.SPLIT);
            return new ArrayList<>(Arrays.asList(keys));
        }
        return null;
    }

    public static void clearSearchHistoryKeys(Context context, String prefix) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(prefix + SEARCH_HISTORY_KEYS, null).apply();
    }


    /**
     * 设置指定用户的金币信息
     *
     * @param context
     * @param userId
     * @return
     */
    public static String getUserScore(Context context, String userId) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(USER_SCORE_PREFIX + userId, null);
    }

    /**
     * 获取指定用户的金币信息
     *
     * @param context
     * @param userId
     * @param userScore
     */
    public static void setUserScore(Context context, String userId, String userScore) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(USER_SCORE_PREFIX + userId, userScore).apply();
    }

    /**
     * 获取是否启用用户积分
     *
     * @param context
     * @return
     */
    public static boolean isUserScoreEnabled(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(USER_SCORE_ENABLE, true);
    }

    /**
     * 设置是否开启用户积分
     *
     * @param context
     * @param userId
     */
    public static void setUserScoreEnabled(Context context, String userId) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(AUTO_LOGIN_USER_ID, userId).apply();
    }

    /**
     * 获取上次的应用版本信息
     *
     * @param context
     * @return
     */
    public static int getLastAppCode(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(APP_CODE, 0);
    }

    /**
     * 保存上次的应用版本信息
     *
     * @param context
     * @param appCode
     */
    public static void setLastAppCode(Context context, int appCode) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(APP_CODE, appCode).apply();
    }

}
