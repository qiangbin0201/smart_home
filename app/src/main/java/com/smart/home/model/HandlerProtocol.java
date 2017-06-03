package com.smart.home.model;

/**
 * Created by lenovo on 2017/5/16.
 */

public class HandlerProtocol {

    public static int NET_CONNECT = 0x1111;

    public static int NET_NOT_CONNECT = 0x0000;

    public static int CONTROL_SUCCESS = 0x1010;

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

    public static int AIR_CONDITION_OFF = 0x300000;

    public static int AIR_CONDITION_ON = 0x310000;

    public static int AIR_CONDITION_TEMP_UP = 0x311000;

    public static int AIR_CONDITION_TEMP_DOWN = 0x310100;

    public static int AIR_CONDITION_MODE_UP = 0x310010;

    public static int AIR_CONDITION_MODE_DOWN = 0x310001;

    public static int FAN_OFF = 0x4000;

    public static int FAN_ON = 0x4100;

    public static int FAN_SPEED_UP= 0x4110;

    public static int FAN_SPEED_DOWN = 0x4101;

}
