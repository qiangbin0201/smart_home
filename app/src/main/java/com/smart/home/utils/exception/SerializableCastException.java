package com.smart.home.utils.exception;

/**
 * Created by hesc on 15/08/25.
 * <p>api返回数据必须实现接口Serializable，否则报错</p>
 * <p>这是因为在Proguard中配置了实现接口Serializable则不混淆的规则，如果网络返回类型不实现接口Serializable，
 * 在release环境下就会获取不到数据，为了避免这种情况出现，就需要返回数据做json解析时报错</p>
 */
public class SerializableCastException extends RuntimeException {

    public SerializableCastException(String message){
        super(message);
    }

}
