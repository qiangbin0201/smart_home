package com.smart.home.utils;

/**
 * Created by qiangbin on 2017/5/18.
 */

public class RadixUtil {

    // 将十进制整数字符串转换成16进制字符串（2字节）
    public static String intString2hexString(String nStr) {
        int n = Integer.parseInt(nStr);
        String hexString = Integer.toHexString(n);
        if (hexString.length() == 1) {
            hexString = '0' + hexString;
        }
        return hexString.toUpperCase();
    }

    // 16进制字符串转换成byte数组（16进制）
    public static byte[] hexString2Bytes(String hexStr) {
        int len = hexStr.length() / 2;
        byte[] buf = new byte[len];
        for (int i = 0; i < len; i++) {
            buf[i] = Integer.valueOf(hexStr.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return buf;
    }

    public static String int2hexString(int n) {
        String hexString = Integer.toHexString(n);
        if (hexString.length() == 1) {
            hexString = '0' + hexString;
        }
        return hexString.toUpperCase();
    }

    //将字符串转化为ASCll码
    public static String stringToAscii(String value) {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                sbu.append((int) chars[i]).append(",");
            } else {
                sbu.append((int) chars[i]);
            }
        }
        return sbu.toString();
    }

    //将字符串转换成二进制字符串，以空格相隔
    private String StrToBinstr(String str) {
        char[] strChar=str.toCharArray();
        String result="";
        for(int i=0;i<strChar.length;i++){
            result +=Integer.toBinaryString(strChar[i])+ " ";
        }
        return result;
    }
}
