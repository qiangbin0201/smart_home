package com.smart.home.http;

import java.util.HashMap;
import java.util.Map;

public class MockRepository {
    private static Map<Class, Object> mocks = new HashMap<>();

    public static void register(Class clazz, Object obj){
        mocks.put(clazz, obj);
    }

    public static void unregister(Class clazz){
        mocks.remove(clazz);
    }

    public static Object findMock(Class clazz){
        return mocks.get(clazz);
    }
}
