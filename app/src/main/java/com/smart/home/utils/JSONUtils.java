package com.smart.home.utils;

import android.content.Intent;

import com.smart.home.TTYCApplication;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smart.home.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Ponyets on 14-5-5.
 */
public class JSONUtils {
    public static final Gson gson = new GsonBuilder().create();

    /**
     * api返回json数据解析类
     */
    public static final Gson apiGson = new GsonBuilder()
            .addDeserializationExclusionStrategy(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return false;
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    //如果需要反射的类是本程序定义的类，则判断该类是不是实现了接口Serializable，如果不是则报错
                    //这是因为在Proguard中配置了如果实现接口Serializable则不混淆的规则，如果网络返回类型不实现接口Serializable，
                    //在release环境下就会获取不到数据，为了避免这种情况出现，这里做强制校验
                    if (BuildConfig.DEBUG && clazz.getName().startsWith(TTYCApplication.mContext.getPackageName())) {
                        if (!isImplementInterface(clazz, Serializable.class)) {
                            throw new SerializableCastException(String.format("类型%s没有实现接口java.io.Serializable，不允许作为api返回类型", clazz.getName()));
                        }
                    }
                    return false;
                }
            }).create();

    /**
     * 判断是否实现了某个接口
     *
     * @param entry
     * @param interfaceClass
     * @return
     */
    private static boolean isImplementInterface(Class<?> entry, Class<?> interfaceClass) {
        Class<?>[] interfaces = entry.getInterfaces();
        if (interfaces == null) {
            return false;
        }
        for (int i = 0; i < interfaces.length; i++) {
            if (interfaces[i].equals(interfaceClass)) {
                return true;
            }
        }
        return false;
    }


    public static void put(JSONObject json, String key, Object value) {
        try {
            json.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void putIntoIntent(Intent intent, JSONObject json) {
        Iterator<String> iterator = json.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            try {
                Object value = json.get(key);

                if (value instanceof Boolean) {
                    intent.putExtra(key, (Boolean) value);
                } else if (value instanceof Byte) {
                    intent.putExtra(key, (Byte) value);
                } else if (value instanceof Character) {
                    intent.putExtra(key, (Character) value);
                } else if (value instanceof Double) {
                    intent.putExtra(key, (Double) value);
                } else if (value instanceof Float) {
                    intent.putExtra(key, (Float) value);
                } else if (value instanceof Integer) {
                    intent.putExtra(key, (Integer) value);
                } else if (value instanceof Long) {
                    intent.putExtra(key, (Long) value);
                } else if (value instanceof Short) {
                    intent.putExtra(key, (Short) value);
                } else if (value instanceof String) {
                    intent.putExtra(key, (String) value);
                } else if (value instanceof JSONArray) {
                    JSONArray array = (JSONArray) value;
                    ArrayList list = new ArrayList<>();
                    for (int idx = 0; idx < array.length(); idx++) {
                        Object arrayElement = array.get(idx);
                        if (arrayElement instanceof JSONObject) {
                            continue;
                        }
                        list.add(array.get(idx));
                    }

                    if (list.size() == 0) {
                        continue;
                    }

                    if (list.get(0) instanceof String) {
                        ArrayList<String> stringList = list;
                        intent.putStringArrayListExtra(key, stringList);
                    } else if (list.get(0) instanceof Integer) {
                        ArrayList<Integer> integerList = list;
                        intent.putIntegerArrayListExtra(key, integerList);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }
}
