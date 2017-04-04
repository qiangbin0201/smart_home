package com.smart.home.utils;

import android.text.TextUtils;

import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by leonlee on 14-6-19.
 * To better product,to better world
 */
public class StringUtil {

    /**
     * 验证邮箱地址是否正确
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            /*
            1、长度：6~50位
            2、@前至少1位
            3、@与后面第一个点之间至少1位
            4、@后只能有1到2个点
            5、@后的点的后面必须为2~3位的字母
            */
            String check = "(?=^[\\w.@]{6,50}$)\\w+@\\w+(?:\\.[\\w]{2,3}){1,2}";

            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            //Log.e("验证邮箱地址错误", e.getMessage());
            flag = false;
        }

        return flag;
    }

    /**
     * 验证手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^((1[0-9]))\\d{9}$");
            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        } catch (Exception e) {
            //Log.e("验证手机号码错误", e.getMessage());
            flag = false;
        }
        return flag;
    }

    /**
     * 验证中文
     *
     * @param chinese
     * @return
     */
    public static boolean isChinese(String chinese) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^[\\u4E00-\\u9FFF]+$");
            Matcher m = p.matcher(chinese);
            flag = m.matches();
        } catch (Exception e) {
            //Log.e("验证中文错误", e.getMessage());
            flag = false;
        }
        return flag;
    }

    /**
     * 验证兑换码格式
     *
     * @param code
     * @return
     */
    public static boolean checkCode(String code) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^[a-zA-Z0-9]+$");
            Matcher m = p.matcher(code);
            flag = m.matches();
        } catch (Exception e) {
            //Log.e("验证兑换码错误", e.getMessage());
            flag = false;
        }
        return flag;
    }

    /**
     * 只允中文输入
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static String stringChineseFilter(String str) throws PatternSyntaxException {
        String regEx = "[^\u4e00-\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 只允许字母和数字输入
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static String stringFilter(String str) throws PatternSyntaxException {
        String regEx = "[^a-zA-Z0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0) {
            return true;
        } else {
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isNotEmpty(CharSequence str) {
        return !StringUtil.isEmpty(str);
    }

    public static boolean isPhoneNumber(String str) {
        if (str.length() != 11)
            return false;

        if (str.startsWith("1") || str.startsWith("999") || str.startsWith("8")) {
            return true;
        }
        return false;
    }

    public static String NumberFilter(String str) throws PatternSyntaxException {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0
     *
     * @param version1
     * @param version2
     * @return
     */
    public static int compareVersion(String version1, String version2) {
        String[] versionArray1 = version1.split("\\.");//注意此处为正则匹配，不能用"."；
        String[] versionArray2 = version2.split("\\.");
        int idx = 0;
        int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值
        int diff = 0;
        while (idx < minLength
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
            ++idx;
        }
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }

    /**
     * 检查给定的字符串内容是否全部为a-zA-Z的字符
     *
     * @param str 内容
     * @return
     */
    public static boolean checkOnlyChar(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }

        return str.matches("[a-zA-Z]+");
    }

    public static boolean checkOnlyNumber(String str) {
        return str.matches("[0-9]+");
    }


    public static boolean checkOnlyChineseChar(String str) {
        return str.matches("[一-龥]+");
    }

    public static boolean checkOnlyLargeChar(String str) {
        return str.matches("[A-Z]+");
    }

    public static boolean checkOnlyLowerChar(String str) {
        return str.matches("[a-z]+");
    }

    public static boolean checkOnlyNumerAndChar(String str) {
        return str.matches("[A-Za-z0-9]+");
    }

    public static boolean checkOnlyNumberCharAndUnderline(String str) {
        return str.matches("\\w+");
    }

    public static boolean checkEmailAddress(String str) {
        return str.matches("([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}");
    }

    private static Random randGen = new Random();

    private static char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
            + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();

    private static final String GOOD_IRI_CHAR = "a-zA-Z0-9\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF";

    private static final String TOP_LEVEL_DOMAIN_STR_FOR_WEB_URL = "(?:"
            + "(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])"
            + "|(?:biz|b[abdefghijmnorstvwyz])"
            + "|(?:cat|com|coop|c[acdfghiklmnoruvxyz])"
            + "|d[ejkmoz]"
            + "|(?:edu|e[cegrstu])"
            + "|f[ijkmor]"
            + "|(?:gov|g[abdefghilmnpqrstuwy])"
            + "|h[kmnrtu]"
            + "|(?:info|int|i[delmnoqrst])"
            + "|(?:jobs|j[emop])"
            + "|k[eghimnprwyz]"
            + "|l[abcikrstuvy]"
            + "|(?:mil|mobi|museum|m[acdeghklmnopqrstuvwxyz])"
            + "|(?:name|net|n[acefgilopruz])"
            + "|(?:org|om)"
            + "|(?:pro|p[aefghklmnrstwy])"
            + "|qa"
            + "|r[eosuw]"
            + "|s[abcdeghijklmnortuvyz]"
            + "|(?:tel|travel|t[cdfghjklmnoprtvwz])"
            + "|u[agksyz]"
            + "|v[aceginu]"
            + "|w[fs]"
            + "|(?:xn\\-\\-0zwm56d|xn\\-\\-11b5bs3a9aj6g|xn\\-\\-80akhbyknj4f|xn\\-\\-9t4b11yi5a|xn\\-\\-deba0ad|xn\\-\\-g6w251d|xn\\-\\-hgbk6aj7f53bba|xn\\-\\-hlcj6aya9esc7a|xn\\-\\-jxalpdlp|xn\\-\\-kgbechtv|xn\\-\\-zckzah)"
            + "|y[etu]" + "|z[amw]))";

    public static String randomString(int length) {
        if (length < 1) {
            return null;
        }
        // Create a char buffer to put random letters and numbers in.
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
        }
        return new String(randBuffer);
    }

    public static String encode(final String content, final String encoding) {
        try {
            return URLEncoder.encode(content, encoding != null ? encoding
                    : HTTP.DEFAULT_CONTENT_CHARSET);
        } catch (UnsupportedEncodingException problem) {
            throw new IllegalArgumentException(problem);
        }
    }

    public static String encodeHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder(bytes.length * 2);

        for (byte aByte : bytes) {
            if (((int) aByte & 0xff) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toString((int) aByte & 0xff, 16));
        }

        return hex.toString();
    }

    public static boolean isWebUrl(CharSequence input) {
        if (input == null || input.length() == 0) {
            return false;
        }
        return Pattern
                .compile(
                        "((?:(http|https|Http|Https|HTTP|HTTPS|rtsp|Rtsp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)"
                                + "\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_"
                                + "\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?"
                                + "((?:(?:["
                                + GOOD_IRI_CHAR
                                + "]["
                                + GOOD_IRI_CHAR
                                + "\\-]{0,64}\\.)+" // named host
                                + TOP_LEVEL_DOMAIN_STR_FOR_WEB_URL
                                + "|(?:(?:25[0-5]|2[0-4]" // or ip address
                                + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(?:25[0-5]|2[0-4][0-9]"
                                + "|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1]"
                                + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                                + "|[1-9][0-9]|[0-9])))"
                                + "(?:\\:\\d{1,5})?)" // plus option port number
                                + "(\\/(?:(?:["
                                + GOOD_IRI_CHAR
                                + "\\;\\/\\?\\:\\@\\&\\=\\#\\~" // plus option
                                // query params
                                + "\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?"
                                + "(?:\\b|$)").matcher(input).matches();
    }

    public static boolean isPhoneNumber(CharSequence input) {
        if (input == null || input.length() == 0) {
            return false;
        }
        return Pattern.compile( // sdd = space, dot, or dash
                "(\\+[0-9]+[\\- \\.]*)?" // +<digits><sdd>*
                        + "(\\([0-9]+\\)[\\- \\.]*)?" // (<digits>)<sdd>*
                        + "([0-9][0-9\\- \\.][0-9\\- \\.]+[0-9])")
                .matcher(input).matches();
    }

    public static boolean isEmailAddress(CharSequence input) {
        if (input == null || input.length() == 0) {
            return false;
        }
        return Pattern
                .compile(
                        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
                                + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "("
                                + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}"
                                + ")+").matcher(input).matches();
    }

    public static boolean isAlphaNumeric(CharSequence input) {
        if (input == null || input.length() == 0) {
            return false;
        }
        return Pattern.compile("[a-zA-Z0-9 \\./-]*").matcher(input).matches();
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static String decodeStr(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        } else {
            try {
                String strType = "UTF-8";
                AppLogUtils.i("originStr:" + str);
                String dsc = str.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
                AppLogUtils.i("dsc:" + dsc);
                String decodeStr = URLDecoder.decode(dsc, strType);
                AppLogUtils.i("encodedStr:" + decodeStr);
                return decodeStr;
            } catch (Throwable t) {
                return str;
            }
        }
    }

    public static boolean isStrEncoded(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        } else {
            try {
                String strType = "UTF-8";
                AppLogUtils.e("originStr:" + str);
                String encodedStr = URLEncoder.encode(str, strType);
                AppLogUtils.e("encodedStr:" + encodedStr);
                String decodeStr = URLDecoder.decode(str, strType);
                AppLogUtils.e("decodedStr:" + decodeStr);
                if (encodedStr.equalsIgnoreCase(decodeStr)) {
                    return false;
                } else {
                    return true;
                }
            } catch (Throwable t) {
                return false;
            }
        }
    }
}
