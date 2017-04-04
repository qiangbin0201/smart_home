package com.smart.home.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by 37X21=777 on 2014/9/20.
 */
public class PicPathUtil {

    /**
     * 保存图片到本地
     * @param context
     * @param bitmap
     * @param imagePath
     * @param showInMediaStore
     * @throws Exception
     */
    public static File storeBitmap(Context context, Bitmap bitmap, String imagePath,
                                   boolean showInMediaStore) throws Exception {
        if(bitmap == null) return null;
        BufferedOutputStream out = null;
        try {
            File imageFile = new File(imagePath);
            out = new BufferedOutputStream(new FileOutputStream(imageFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            // 通知媒体库更新相册，否则相册不能马上显示出已经保存的图片
            if(showInMediaStore)
                notifyMediaStore(context, imageFile);
            return imageFile;
        } finally {
            if(out != null)
                out.close();
        }
    }

    /**
     * 通知媒体库更新相册
     * @param  context
     * @param imageFile
     */
    private static void notifyMediaStore(Context context, File imageFile){
        if(context == null) return;
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(imageFile));
        context.sendBroadcast(intent);
    }
}
