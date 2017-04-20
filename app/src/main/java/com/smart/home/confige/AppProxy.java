package com.smart.home.confige;

import com.smart.home.http.IRestAdapter;
import com.smart.home.http.RestAdapterFactory;

/**
 * Created by qiangbin on 2017/4/18.
 */

public class AppProxy {

    private static AppProxy sInstance = new AppProxy();

    public static AppProxy getInstance(){
        return sInstance;
    }

    private IRestAdapter mDataApiRestAdapter;

    public IRestAdapter getDataApiRestAdapter(){
        if(mDataApiRestAdapter == null){
            mDataApiRestAdapter = RestAdapterFactory.createRestAdapter("");

        }
        return mDataApiRestAdapter;
    }


}
