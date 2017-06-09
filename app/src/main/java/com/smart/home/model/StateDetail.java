package com.smart.home.model;

import java.io.Serializable;

/**
 * Created by lenovo on 2017/4/18.
 */

public class StateDetail implements Serializable {
    //服务器返回状态
    public String state;

    //服务器返回的设备开关状态
    public boolean equipOpen;

    public int brightness;

    //服务器返回的音量值
    public int tv_volume;

    //服务器返回的频道值
    public int tv_channel;

    //服务器返回的音量值
    public int air_temp;

    //服务器返回的对应的空调模式
    public int mode;

    //服务器返回风扇的当前速度
    public int speed;

}
