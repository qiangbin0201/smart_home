package com.smart.home.model;

/**
 * Created by lenovo on 2017/5/4.
 */

public class TvProtocol {

    //电视关机
    public static final String TV_OFF = "00000";
    //电视开机
    public static final String TV_ON = "10000";
    //音量加
    public static final String TV_SOUND_UP = "11000";
    //音量减
    public static final String TV_SOUND_DOWN = "10100";
    //频道加
    public static final String TV_CHANNEL_UP = "10010";
    //频道减
    public static final String TV_CHANNEL_DOWN = "10010";

    public static final String INFRARED_OFF = "S 0 1 0 0 0 0 0 C";

    public static final String INFRARED_ON = "S 0 1 1 0 0 0 0 C";

    public static final String INFRARED_SOUND_UP = "S 0 1 1 1 0 0 0 C";

    public static final String INFRARED_SOUND_DOWN= "S 0 1 1 0 1 0 0 C";

    public static final String INFRARED_CHANNEL_UP = "S 0 1 1 0 0 1 0 C";

    public static final String INFRARED_CHANNEL_DOWN = "S 0 1 1 0 0 0 1 C";



}
