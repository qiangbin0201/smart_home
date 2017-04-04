package com.smart.home.utils.ua;

import android.content.Context;

public class UserAction {

    private static final Object SINGLE_LOCK = new Object();
    private static UserAction instance = null;
    private UserActionIn mUserActionProxy = null;

    private UserAction(Context context) {
        boolean isNeedUpload = true;//new CommonPreferenceProvider(context).isStatUserBehavior();
        this.mUserActionProxy = (UserActionIn) new UserActionProxyFactory(isNeedUpload).newProxyInstance(new UserActionImpl());
    }

    public static UserAction getInstance(Context context) {
        if (instance == null) {
            synchronized (SINGLE_LOCK) {
                if (instance == null) {
                    instance = new UserAction(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public UserActionIn getUserAction() {
        return this.mUserActionProxy;
    }
}