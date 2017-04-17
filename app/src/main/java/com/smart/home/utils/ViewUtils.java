package com.smart.home.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.internal.Utils;

/**
 * 跟View相关的工具类
 */
public class ViewUtils {
    /**
     * px 转 dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * px转sp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @return
     */
    public static int dp2px(Context context, float dipValue) {
        //DisplayMetrics类中属性density
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        //DisplayMetrics类中属性scaledDensity
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static float getDensity(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 改变textview中局部文字颜色
     *
     * @param text
     * @param start
     * @param end
     * @param color
     * @return
     */
    public static SpannableStringBuilder changeTextPartColor(String text,
                                                             int start, int end, int color) {
        if (text == null) {
            return new SpannableStringBuilder("");
        }
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        // //设置指定位置的背景颜色
        // style.setSpan(new
        // BackgroundColorSpan(Color.RED),0,4,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        // 设置指定位置的文字颜色
        style.setSpan(new ForegroundColorSpan(color), start, end,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        return style;

    }

    public static SpannableStringBuilder changeTextPartColor(String text,
                                                             int start, int end, int start2, int end2, int color) {
        if (text == null) {
            return new SpannableStringBuilder("");
        }
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        // //设置指定位置的背景颜色
        // style.setSpan(new
        // BackgroundColorSpan(Color.RED),0,4,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        // 设置指定位置的文字颜色
        style.setSpan(new ForegroundColorSpan(color), start, end,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new ForegroundColorSpan(color), start2, end2,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        return style;

    }

    /**
     * drawable转bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);

        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());

        drawable.draw(canvas);

        return bitmap;

    }

    /**
     * 图片模糊
     *
     * @param sentBitmap
     * @param radius
     * @return
     */
    public static Bitmap fastblur(Bitmap sentBitmap, int radius) {

        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16)
                        | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return (bitmap);
    }

    /**
     * 图片缩放
     *
     * @param img
     * @param scale_width
     * @param scale_height
     * @return
     */
    public static Bitmap scaleBitmapImg(Bitmap img, int scale_width,
                                        int scale_height) {
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) scale_width / img.getWidth());
        float scaleHeight = ((float) scale_height / img.getHeight());
        float scale = scaleWidth >= scaleHeight ? scaleWidth : scaleHeight;
        matrix.postScale(scale, scale);
        Bitmap newbmp = Bitmap.createBitmap(img, 0, 0, img.getWidth(),
                img.getHeight(), matrix, true);
        img.recycle();
        img = null;
        return newbmp;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView,
                                                        boolean mIsFlag) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        int moneHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
            // sysout.println("----------高度----------"+listItem.getMeasuredHeight());
            // if(i==len-1 && (mIsFlag)){ //评论特殊处理
            // moneHeight = listItem.getMeasuredHeight()/2+10;
            // }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        totalHeight = moneHeight + totalHeight;
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // sysout.println("-----总高度----------------"+params.height);
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
        // sysout.println("---------ListView高度----------"+listView.getLayoutParams().height);
    }

    /**
     * 获取view在屏幕上的绝对坐标
     *
     * @param v
     * @return
     */
    public static int[] getViewInScreenWH(View v) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        return location;
    }

    /**
     * 获取状态栏高度
     *
     * @param activity
     * @return
     */
    public static int getStatusBarHeight(Activity activity) {
        Rect rect = new Rect();
        Window win = activity.getWindow();
        win.getDecorView().getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;
        return statusBarHeight;
    }

    /**
     * 截屏
     *
     * @param activity
     * @return
     */
    public static Bitmap getScreenShot(Activity activity) {
        // 获取windows中最顶层的view
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();
        // 获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        // 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);
        // 去掉状态栏
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0, 0, view
                .getDrawingCache().getWidth(), view.getDrawingCache()
                .getHeight());
        // 销毁缓存信息
        view.destroyDrawingCache();
        return bmp;
    }

    /**
     * 获取textview中文本长度
     *
     * @param textView
     * @param text
     * @return
     */
    public static float getTextViewLength(TextView textView, String text) {
        TextPaint paint = textView.getPaint();
        // 得到使用该paint写上text的时候,像素为多少
        float textLength = paint.measureText(text);
        return textLength;
    }

    /**
     * 动态设置图片宽高
     *
     * @param context
     * @param stander_w
     * @param stander_h
     * @param num
     * @param space_image
     * @param space_left
     * @param space_right
     * @return
     */
//    public static int[] setImageWH(Context context, int stander_w, int stander_h, int num, int space_image, int space_left, int space_right) {
//        int wh[] = new int[2];//0:w, 1:h
//        wh[0] = (getScreenWH(context)[0] - Utils.dp2px(context, space_left + space_right) - space_image * (num - 1)) / num;
//        wh[1] = wh[0] * stander_h / stander_w;
//        return wh;
//
//    }

    /**
     * 动态设置图片宽高(根据所提供的宽度)
     *
     * @param context
     * @param offsetLength 屏幕宽度去掉多少
     * @param stander_w
     * @param stander_h
     * @param num
     * @param space_image
     * @param space_left
     * @param space_right
     * @return
//     */
//    public static int[] setImageWH(Context context, int offsetLength, int stander_w, int stander_h, int num, int space_image, int space_left, int space_right) {
//        if (offsetLength <= 0) {
//            return setImageWH(context, stander_w, stander_h, num, space_image, space_left, space_right);
//        } else {
//            int wh[] = new int[2];//0:w, 1:h
//            wh[0] = (getScreenWH(context)[0] - offsetLength - Utils.dp2px(context, space_left + space_right) - space_image * (num - 1)) / num;
//            wh[1] = wh[0] * stander_h / stander_w;
//            return wh;
//        }
//
//
//    }


    /**
     * 回收imageview里面的图片资源
     *
     * @param imageView
     */
    public static void releaseImageViewResouce(ImageView imageView) {
        if (imageView == null) return;
        Drawable drawable = imageView.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

    /**
     * 屏幕像素宽高
     *
     * @param context
     * @return
     */
    public static int[] getScreenWH(Context context) {
        int[] wh = new int[2];
        DisplayMetrics metrics = context.getApplicationContext().getResources().getDisplayMetrics();
        wh[0] = metrics.widthPixels;
        wh[1] = metrics.heightPixels;
        return wh;
    }


    public static void rotateAnimView(Activity context, View view, int animResId) {



        view.clearAnimation();
        Animation anim = AnimationUtils.loadAnimation(context, animResId);
        LinearInterpolator lin = new LinearInterpolator();
        anim.setInterpolator(lin);
        anim.setFillAfter(true);
//        anim.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//                layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//                view.setLayoutParams(layoutParams);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });


        view.startAnimation(anim);
    }

    /**
     * 设置View的显示属性, Visible or Gone
     *
     * @param view
     * @param show
     */
    public static void showView(View view, boolean show) {
        setVisibility(view, show ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置view的visibile属性, 先判断是否一致
     *
     * @param view
     * @param visible
     */
    public static void setVisibility(View view, int visible) {
        if (view.getVisibility() != visible) {
            view.setVisibility(visible);
        }
    }
}
