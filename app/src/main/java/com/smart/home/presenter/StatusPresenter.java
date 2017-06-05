package com.smart.home.presenter;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by qiangbin on 2017/6/3.
 */

public class StatusPresenter {
    private static StatusPresenter mStatusPresenter;

    private static SharedPreferences mSharedPreferences;

    public static StatusPresenter getInstance(Context context, String spFileName){
        if(mStatusPresenter == null){
            mStatusPresenter = new StatusPresenter();
            mSharedPreferences = context.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        }
        return mStatusPresenter;
    }

    /**
     *  保存int数据
     * @param key
     * @param value
     */
    public  void putInt(String key,int value){
        mSharedPreferences.edit().putInt(key, value).apply();
    }

    /**
     * 获取int数据
     * @param key
     * @param defValue 默认值
     * @return int
     */
    public  int getInt(String key, int defValue) {
        return mSharedPreferences.getInt(key, defValue);
    }

    /**
     * 保存long型数据
     * @param key
     * @param value
     */
    public  void putLong(String key, long value){
        mSharedPreferences.edit().putLong(key, value).apply();
    }

    /**
     * 获取long型数据
     * @param key
     * @param defValue 默认值
     * @return int
     */
    public  long getLong(String key, long defValue){
        return mSharedPreferences.getLong(key, defValue);
    }

    /**
     * 保存string型数据
     * @param key
     * @param value
     */
    public  void putString(String key, String value){
        mSharedPreferences.edit().putString(key, value).apply();
    }

    /**
     * 获取string型数据
     * @param key
     * @param defValue
     * @return
     */
    public  String getString(String key,String defValue){
        return mSharedPreferences.getString(key, defValue);
    }

    /**
     * 保存boolean型数据
     * @param key
     * @param status
     */
    public void putBoolean(String key, Boolean status){
        mSharedPreferences.edit().putBoolean(key, status).apply();

    }

    /**
     * 获取boolean型数据
     * @param key
     * @param defValue
     * @return
     */
    public Boolean getBoolan(String key, Boolean defValue){
        return mSharedPreferences.getBoolean(key, defValue);
    }

}
