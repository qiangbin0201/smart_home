package com.smart.home.utils;

import android.util.Base64;

import java.io.InputStream;
import java.security.MessageDigest;

/**
 * Created by hesc on 15/11/11.
 * <p>提供64位编码及解码、md5加密等</p>
 */
public class SignUtils {
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final int STREAM_BUFFER_LENGTH = 1024;

    public static byte[] decodeBase64(String s) {
        return Base64.decode(s, Base64.DEFAULT);
    }

    public static byte[] safeDecodeBase64(String s) {
        return Base64.decode(s.replace('-', '+').replace('_', '/'), Base64.DEFAULT);
    }

    public static byte[] encodeBase64(byte[] bytes) {
        return Base64.encode(bytes, Base64.NO_PADDING | Base64.NO_WRAP);
    }

    public static String encodeBase64String(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.NO_PADDING | Base64.NO_WRAP);
    }

    public static String safeEncodeBase64String(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.NO_PADDING | Base64.NO_WRAP).replace('/', '_').replace('+', '-');
    }

    public static String encodeHexString(byte[] bytes) {
        int l = bytes.length;
        char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS_LOWER[(0xF0 & bytes[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0x0F & bytes[i]];
        }
        return new String(out);
    }

    public static String md5(String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] md5 = digest.digest(s.getBytes("UTF-8"));
            return encodeHexString(md5);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String md5(InputStream stream) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");

            byte[] buffer = new byte[STREAM_BUFFER_LENGTH];
            int read = stream.read(buffer, 0, STREAM_BUFFER_LENGTH);

            while (read > -1) {
                digest.update(buffer, 0, read);
                read = stream.read(buffer, 0, STREAM_BUFFER_LENGTH);
            }

            byte[] md5 = digest.digest();
            return encodeHexString(md5);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
