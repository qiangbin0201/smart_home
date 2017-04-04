package com.smart.home.utils;

import android.text.TextUtils;

import com.bigbang.news.model.Emoji;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 16/7/21.
 */
public class EmojiMap {

    private static JSONObject mCityJson;
    private static Map<String, String> mMap = new HashMap<>();

    static {
        loadEmoji();
    }

    private static void loadEmoji() {

    }

    /**
     * 根据城市名称获取城市id
     *
     * @param ch
     * @return
     */
    public static String getEmojiDrawableName(String ch) {
        if (!TextUtils.isEmpty(ch)) {
            if (mMap.containsKey(ch))
                return mMap.get(ch);
        }

        return null;
    }

    public static class EmojiWrapper implements Serializable {
        public List<Emoji> emojis;
    }
}
