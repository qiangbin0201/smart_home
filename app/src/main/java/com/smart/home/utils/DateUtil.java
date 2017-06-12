package com.smart.home.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by qiangbin on 17-6-8.
 *
 */
public class DateUtil {

    public static int getTimeZoneMilliseconds() {
        return TimeZone.getDefault().getRawOffset();
    }

    /**
     * 格式化日期时间
     *
     * @param time    时间戳（单位为秒）
     * @param pattern 格式，如yyyy-MM-dd HH:mm 就代表格式化为2015-05-23 22:40
     * @return
     */
    public static String formatDateTime(long time, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(new Date(time));
    }

    /**
     * 向上取整
     *
     * @param time
     * @return
     */
    public static String formatDateForDay(long time) {
        String day = (int) Math.ceil(time / (24 * 60 * 60)) + "";
        return day;
    }

    public static String getCommentTime(long time) {

        String turnResult = "";
        long subTime = System.currentTimeMillis() - time;
        if (subTime < 0) {
            turnResult = getDateString(time);
        } else {
            if (subTime < 10 * 1000) {
                turnResult = "刚刚";
            } else if (subTime < 1000 * 60) {//一分钟内
                turnResult = subTime / (1000) + "秒前";
            } else if (subTime < 1000 * 60 * 60) {//一小时内
                turnResult = subTime / (1000 * 60) + "分钟前";
            } else if (subTime < 1000 * 60 * 60 * 24) {//一天内
                turnResult = subTime / (1000 * 60 * 60) + "小时前";
            } else {
                turnResult = getDateString(time);
            }
        }
        return turnResult;
    }

    /**
     * 时间戳转换成时间:带时分
     *
     * @param time
     * @return
     */
    public static String getDateString(Long time) {
        long curr_time = System.currentTimeMillis();
        Date date = new Date(time);
        Date curr_date = new Date(curr_time);

        int year = date.getYear();
        int curr_year = curr_date.getYear();
        DateFormat sdf = new SimpleDateFormat("MM月dd日");
        if (year < curr_year) {
            sdf = new SimpleDateFormat("yyyy年MM月dd日");
        }
        String sd = sdf.format(time);
        return sd;
    }

    /**
     * 是否是同一天
     *
     * @param lastTime
     * @param curTime
     * @return
     */
    public static boolean isSameDayByTimeStamp(long lastTime, long curTime) {
        return isDayGap(lastTime, curTime, 0);
    }

    /**
     * 是否相差一天
     *
     * @param lastTime
     * @param curTime
     * @return
     */
    public static boolean isYesterdayByTimeStamp(long lastTime, long curTime) {
        return isDayGap(lastTime, curTime, 1);
    }

    /**
     * lastTime对应的时间是否比curTime早gapDay
     *
     * @param lastTime
     * @param curTime
     * @param gapDay
     * @return
     */
    private static boolean isDayGap(long lastTime, long curTime, int gapDay) {
        boolean result = false;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(lastTime);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);

            cal.setTimeInMillis(curTime);
            cal.add(Calendar.DAY_OF_YEAR, -1 * gapDay);
            if (year == cal.get(Calendar.YEAR)) {
                if (month == cal.get(Calendar.MONTH) + 1) {
                    if (day == cal.get(Calendar.DAY_OF_MONTH)) {
                        result = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }


}
