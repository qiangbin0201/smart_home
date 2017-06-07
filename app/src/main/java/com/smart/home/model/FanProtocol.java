package com.smart.home.model;

/**
 * Created by lenovo on 2017/5/9.
 */

public class FanProtocol {

    public static final String FAN_OFF = "000";

    public static final String FAN_ON = "100";

    public static final String FAN_SPEED_UP= "110";

    public static final String FAN_SPEED_DOWN = "101";

    public static final String INFRARED_OFF = "S 1 1 0 0 0 C";

    public static final String INFRARED_ON = "S 1 1 1 0 0 C";

    public static final String INFRARED_SPEED_UP = "S 1 1 1 1 0 C";

    public static final String INFRARED_SPEED_DOWN = "S 1 1 1 0 1 C";

}
