package com.smart.home.utils;

import com.bigbang.news.http.TypedJson;

import java.io.Serializable;

/**
 * Created by admin on 15/8/20.
 */
public class RetrofitUtil {

    /**
     * @param t 实现了Serializable接口的数据结构（为什么要必须实现Serializable接口呢？主要是在Proguard中配置了实现Serializable接口的bean才不会被混淆）
     *          返回相应的Json字符串
     */
    public static <T extends Serializable> String buildJsonString(T t) {
        return JSONUtils.gson.toJson(t);
    }

    /**
     * @param t 实现了Serializable接口的数据结构（为什么要必须实现Serializable接口呢？主要是在Proguard中配置了实现Serializable接口的bean才不会被混淆）
     *          返回相应的HttpString
     */
    public static <T extends Serializable> TypedJson buildHttpString(T t) {
        return new TypedJson(buildJsonString(t));
    }
}
