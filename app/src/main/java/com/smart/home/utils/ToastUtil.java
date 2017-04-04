package com.smart.home.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigbang.news.R;

public class ToastUtil {

    private static Toast mToast;
    private static View mLayout;

    public static void show(Context context, String message, int iconResId) {
        //实例化一个Toast对象
        if (mToast == null) {
            mLayout = LayoutInflater.from(context).inflate(R.layout.view_toast, null);
            mToast = new Toast(context);
            mToast.setView(mLayout);
            mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        }

        //从layout中按照id查找imageView对象
        ImageView imageView = (ImageView) mLayout.findViewById(R.id.iv_toast);
        if (iconResId != -1) {
            //设置ImageView的图片
            imageView.setImageResource(iconResId);
        } else {
            imageView.setVisibility(View.GONE);
        }

        //从layout中按照id查找TextView对象,设置TextView的text内容
        if (mLayout != null) {
            ((TextView) mLayout.findViewById(R.id.tv_toast)).setText(message);
        }

        if (mToast != null) {
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.show();
        }

//        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER, 0, -200);
//        toast.onShow();
    }

    public static void clear() {
        mToast = null;
        mLayout = null;
    }

    public static void cancelToast() {
        if (mToast != null)
            mToast.cancel();
    }

    public static Toast showBottom(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 300);
        toast.show();
        return toast;
    }

    public static void show(Context context, int msgId, int iconResId) {
        show(context, context.getString(msgId), iconResId);
    }

    public static void show(Context context, int msgId) {
        show(context, context.getString(msgId), -1);
    }

    public static void show(Context context, String message) {
        show(context, message, -1);
    }

    public static Toast showCenter(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, -50);
        toast.show();
        return toast;
    }

    public static void showSuccess(Context context, int msgId){
        showSuccess(context,context.getString(msgId));
    }

    public static void showSuccess(Context context, String message){
        show(context, message, R.drawable.icon_toast_success);
    }

    public static void showFailed(Context context, int msgId){
        showFailed(context, context.getString(msgId));
    }

    public static void showFailed(Context context, String message){
        show(context, message, R.drawable.icon_toast_fail);
    }
}
