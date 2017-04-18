package com.smart.home.http;

/**
 * Created by lenovo on 2017/4/18.
 */

public interface IRestAdapter {
    <T> T create(Class<T> service);
}
