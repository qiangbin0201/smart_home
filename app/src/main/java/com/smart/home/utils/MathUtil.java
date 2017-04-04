package com.smart.home.utils;

import android.text.TextUtils;

import java.math.BigDecimal;

/**
 * Created by leonlee on 14-5-24.
 * To better product,to better world
 */
public class MathUtil {
    /**
     * //long型去掉.0   string型去掉00 以精确至元,注释部分为精确至分的方法
     *
     * @param price
     * @return
     */
    public static String addPointToPrice(String price) {
        if (null == price || price.length() == 0 || price.equals("0")) {
//            return "0.00";
            return "0";
        }
        int length = price.length();
        if (length == 2) {
            return "0." + price;
        } else if (length == 1) {
            return "0.0" + price;
        } else {
            String after = price.substring(length - 2);
            String before = price.substring(0, length - 2);
//            return before + "." + after;
            return before;
        }
    }

    /**
     * 将米转换为公里
     *
     * @param driverdistance
     * @return
     */
    public static String addPointToDistance(int driverdistance) {
        String distance = driverdistance + "";
        int length = distance.length();
        switch (length) {
            case 0:
                return "0";
            case 1:
                return "0.00" + distance;
            case 2:
                return "0.0" + distance;
            case 3:
                return "0." + distance;
            default:
                String after = distance.substring(length - 3);
                String before = distance.substring(0, length - 3);
                return before + "." + after;
        }
    }

    public static float selectPoingStars(float stars) {
        int starDouble = (int) (stars * 2);
        float star = starDouble;
        return star / 2;
    }

    public static int addPointToPrice(int price) {
        return (int) (0.5 + price / 100);
    }

    public static int addPointToPrice(double price) {
        return (int) (0.5 + price / 100);
    }

    public static String changeMoney(long price) {
        if (price % 100 == 0) {
            return (price / 100) + "";
        }

        if (price % 10 == 0) {
            return String.format("%.1f", ((float) price) / 100);
        }

        return String.format("%.2f", ((float) price) / 100);
    }

    public static String changeMoney(int price) {
        if (price % 100 == 0) {
            return (price / 100) + "";
        }

        if (price % 10 == 0) {
            return String.format("%.1f", ((float) price) / 100);
        }

        return String.format("%.2f", ((float) price) / 100);
    }

    /**
     * 将人民币由元转换为分
     *
     * @param yuan
     * @return
     */
    public static int convertMoneyFromYuanToFen(float yuan) {
        return (int) (yuan * 100);
    }

    /**
     * 将人民币由分转换为元
     *
     * @param fen
     * @return
     */
    public static int converMoneyFromFenToYuan(int fen) {
        return fen / 100;
//        float yuan = (float) fen / 100;
//        BigDecimal b = new BigDecimal(String.valueOf(yuan));
//        return b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 返回每三位为一体，以逗号连接的字符串
     *
     * @param fen
     * @return
     */
    public static String getCombinedMoney(long fen) {
        return getCombinedMoney(fen, false);
    }

    /**
     * 返回每三位为一体，以逗号连接的字符串
     *
     * @param fen
     * @param needDecimal
     * @return
     */
    public static String getCombinedMoney(long fen, boolean needDecimal) {
        if (fen == 0) {
            return "0";
        }
//        不是整数 元
        if (fen < 100) {
            return "" + getRatio(fen / 100f);
        }
        String str = String.valueOf(fen / 100);
        StringBuffer sb = new StringBuffer();
        // 获取循环次数
        int count = str.length() % 3 == 0 ? str.length() / 3 : 1 + str.length() / 3;

        for (int i = 0; i < count; i++) {
            String temp;
            if (str.length() > 3) {
                temp = "," + str.substring(str.length() - 3);
                str = str.substring(0, str.length() - 3);
                sb.insert(0, temp);
            } else {
                sb.insert(0, str);
            }
        }
        if (needDecimal) {
            String value = getRatio(fen / 100f);
//            拼接小数
            sb.append(!TextUtils.isEmpty(value) && value.contains(".") ? value.substring(value.indexOf(".")) : "");
        }
        return sb.toString();
    }

    /**
     * 或得年化收益率和本金总和
     *
     * @param fen
     * @param days
     * @return
     */
    public static long getAnnualProfit(long fen, int days, float profit) {
        if (fen == 0) {
            return 0;
        }
        long value = (long) (fen + profit * days * fen / 360);
        return value;
    }

    /**
     * 返回回报率
     * 保持两位数字
     *
     * @param radio
     * @return
     */
    public static String getRatio(double radio) {
        String result = String.format("%.2f", radio);
        if (result.endsWith(".0") || result.endsWith(".00")) {
            result = String.valueOf((int) radio);
        }
        return result;
    }

    /**
     * 分成展示
     *
     * @param fencheng
     * @return
     */
    public static String getFenCheng(int fencheng) {
        if (fencheng == 1) {
            return "一九分成";
        } else if (fencheng == 2) {
            return "二八分成";
        } else if (fencheng == 3) {
            return "三七分成";
        } else if (fencheng == 4) {
            return "四六分成";
        } else if (fencheng == 5) {
            return "五五分成";
        } else if (fencheng == 6) {
            return "六四分成";
        } else if (fencheng == 7) {
            return "七三分成";
        } else if (fencheng == 8) {
            return "八二分成";
        } else if (fencheng == 9) {
            return "九一分成";
        } else {
            return "";
        }
    }

    public static float decimal(float f) {
        BigDecimal b = new BigDecimal(String.valueOf(f));
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }
}

