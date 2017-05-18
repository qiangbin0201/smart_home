package com.smart.home.model;

/**
 * Created by lenovo on 2017/5/16.
 */

public class HandlerProtocol {

    public static int NET_CONNECT = 0x1111;

    public static int NET_NOT_CONNECT = 0x0000;

    public static int BULB_OFF = 0x1000;

    public static int BULB_ON = 0x1100;

    public static int BULB_BRIGHTNESS_UP = 0x1110;

    public static int BULB_BRIGHTNESS_DOWN = 0x1101;

    public static int TV_OFF = 0x200000;

    public static int TV_ON = 0x210000;

    public static int TV_SOUND_UP = 0x211000;

    public static int TV_SOUND_DOWN = 0x210100;

    public static int TV_CHANNEL_UP = 0x210010;

    public static int TV_CHANNEL_DOWN = 0x210001;



}
