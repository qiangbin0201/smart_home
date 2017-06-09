package com.smart.home.model;

/**
 * Created by lenovo on 2017/5/9.
 */

public class AirConditionProtocol {

    public static final String AIR_CONDITION_OFF = "00000";

    public static final String AIR_CONDITION_ON = "10000";

    public static final String AIR_CONDITION_TEMP_UP = "11000";

    public static final String AIR_CONDITION_TEMP_DOWN = "10100";

    public static final String AIR_CONDITION_MODE_UP= "10010";

    public static final String AIR_CONDITION_MODE_DOWN= "10001";

    public static final String INFRARED_OFF = "S 1 0 0 0 0 0 0 C";

    public static final String INFRARED_ON = "S 1 0 1 0 0 0 0 C";

    public static final String INFRARED_TEMP_UP = "S 1 0 1 1 0 0 0 C";

    public static final String INFRARED_TEMP_DOWN = "S 1 0 1 0 1 0 0 C";

    public static final String INFRARED_MODE_UP = "S 1 0 1 0 0 1 0 C";

    public static final String INFRARED_MODE_DOWN = "S 1 0 1 0 0 0 1 C";

}
