package com.smart.home.model;

/**
 * Created by qiangbin on 2017/5/4.
 */

public class BulbProtocol {

    public static final String BULB_OFF = "000";

    public static final String BULB_ON = "100";

    public static final String BRIGHTNESS_UP = "110";

    public static final String BRIGHTNESS_DOWN = "101";

    public static final String INFRARED_OFF = "S 0 0 0 0 0 C";

    public static final String INFRARED_ON = "S 0 0 1 0 0 C";

    public static final String INFRARED_BRIGHTNESS_UP = "S 0 0 1 1 0 C";

    public static final String INFRARED_BRIGHTNESS_DOWN = "S 0 0 1 0 1 C";

}
