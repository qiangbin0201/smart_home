package com.smart.home.http;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.smart.home.BuildConfig;
import com.smart.home.TTYCApplication;
import com.smart.home.utils.JSONUtils;

import retrofit.MockRestAdapter;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;


public class RestAdapterFactory {


    private static class RestAdapterWrapper1 implements IRestAdapter {
        private RestAdapter mRestAdapter;

        public RestAdapterWrapper1(String endPoint) {
            mRestAdapter = internalCreateRestAdapter1(endPoint);
        }

        @Override
        public <T> T create(Class<T> service) {
            return mRestAdapter.create(service);
        }
    }

    private static class RestAdapterWrapper implements IRestAdapter {
        private RestAdapter mRestAdapter;

        public RestAdapterWrapper(String endPoint) {
            mRestAdapter = internalCreateRestAdapter(endPoint);
        }

        @Override
        public <T> T create(Class<T> service) {
            return mRestAdapter.create(service);
        }
    }

    private static class MockRestAdapterWrapper implements IRestAdapter {
        private RestAdapter mRestAdapter;
        private MockRestAdapter mMockRestAdapter;

        public MockRestAdapterWrapper(String endPoint) {
            mRestAdapter = internalCreateRestAdapter(endPoint);
            mMockRestAdapter = MockRestAdapter.from(mRestAdapter);
            mMockRestAdapter.setDelay(500);  //延迟执行
            mMockRestAdapter.setErrorPercentage(0); //设置不会人为出现随机错误
        }

        @Override
        public <T> T create(Class<T> service) {
            T obj = (T) MockRepository.findMock(service);
            if (obj != null) {
                return mMockRestAdapter.create(service, obj);
            } else {
                return mRestAdapter.create(service);
            }
        }
    }

    /**
     * 是否开启http的mock功能
     *
     * @return
     */
    private static boolean isEnableHttpMock() {
        try {
            ApplicationInfo info = TTYCApplication.mContext.getPackageManager()
                    .getApplicationInfo(TTYCApplication.mContext.getPackageName(), PackageManager.GET_META_DATA);
            return info.metaData.getBoolean("ENABLE_HTTP_MOCK");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static IRestAdapter createRestAdapter(String endPoint) {
        if (BuildConfig.DEBUG && isEnableHttpMock())
            return new MockRestAdapterWrapper(endPoint);
        else
            return new RestAdapterWrapper(endPoint);
    }

    public static IRestAdapter createRestAdapter(String endPoint, boolean noBody) {
        if (noBody){
            return new RestAdapterWrapper1(endPoint);
        }else{
            return createRestAdapter(endPoint);
        }
    }

    private static RestAdapter internalCreateRestAdapter(String endPoint) {
        return new RestAdapter.Builder()
                .setClient(new OkClient())
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String s) {
                        if (BuildConfig.DEBUG) {
                        }
                    }
                })
                .setEndpoint(endPoint)
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .setConverter(new GsonConverter(JSONUtils.apiGson))
                .build();
    }

    private static RestAdapter internalCreateRestAdapter1(String endPoint) {
        return new RestAdapter.Builder()
                .setClient(new OkClient())
//                .setClient(new JWTClientForm())
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String s) {
                        if (BuildConfig.DEBUG) {
                        }
                    }
                })
                .setEndpoint(endPoint)
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .setConverter(new GsonConverter(JSONUtils.apiGson))
                .build();
    }
}
