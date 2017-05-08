package com.smart.home.model;

import java.io.Serializable;

/**
 * Created by lenovo on 2017/4/18.
 */

public class StateDetail implements Serializable {
    //服务器返回状态
    public String state;

    public int brightness;

    //服务器返回的音量值
    public int tv_volume;

    //服务器返回的频道值
    public int tv_channel;


}
