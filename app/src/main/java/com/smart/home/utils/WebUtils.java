package com.smart.home.utils;

import android.os.Build;
import android.webkit.WebView;

/**
 * Created by admin on 16/8/19.
 */
public class WebUtils {

    public static void executeJavascript(WebView webView, String js) {
        if (webView == null) {
            return;
        }
        try {
            // 4.4版本之前
            if (Build.VERSION.SDK_INT < 19) {
                js = "javascript:" + js;
                webView.loadUrl(js);
            } else {
                webView.evaluateJavascript(js, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
