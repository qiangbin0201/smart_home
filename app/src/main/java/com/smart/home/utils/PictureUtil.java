package com.smart.home.utils;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by leonlee on 14-6-21.
 * To better product,to better world
 */
public class PictureUtil {

    public final static String PHOTO_PARA = "code";

    public static final int REQUEST_CODE_CAPTURE_CAMEIA = 2001;
    public static final int REQUEST_CODE_PICK_IMAGE = 2002;
    public static final int REQUEST_CODE_CROP_IMAGE = 2003;

    /**
     * @Title: getImageFromAlbum
     *
     * @Description: 从相册中获取图片 @param
     */
    public static void getImageFromAlbum(Activity mActivity) {
        getImageFromAlbum(mActivity, false);
    }

    /**
     * @Title: getImageFromAlbum
     *
     * @Description: 从相册中获取图片 @param
     */
    public static void getImageFromAlbum(Activity mActivity, boolean isOnlyImage) {

        Intent intent = new Intent(Intent.ACTION_PICK);
        if (isOnlyImage) {
            intent.setType("image/*");// 相片类型
        } else {
            intent.setType("video/*;image/*");// 相片类型
        }
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivity.startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    /**
     * @Title: getImageFromCamera
     *
     * @Description: 调用相机拍照，获取url
     *
     * @return 返回保存图片路径
     *
     */
    public static String getImageFromCamera(Activity mActivity) {
        String state = Environment.getExternalStorageState();
        String fileName = StorageUtils.getDestSaveDir() + File.separator + System.currentTimeMillis() + ".jpg";
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = new File(StorageUtils.getDestSaveDir());
            if (!file.exists()) {
                file.mkdir();
            }
            File outFile = new File(fileName);
            Uri uri = Uri.fromFile(outFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            mActivity.startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMEIA);
            return fileName;
        } else {
            return null;
        }
    }

    /**
     * @Title: getImagePath
     *
     * @Description: 根据uri获取图片路径
     *
     * @throws
     */
    public static String getMediaPath(Uri uri, Activity mActivity) {
        // scheme是file说明是从文件浏览器中选中的图片，已经包含图片路径了
        if (uri.getScheme().equalsIgnoreCase("file")) {
            return uri.getPath();
        }
        // after android4.4,Image store uri changed
        // android 4.4 go to if,then else
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(mActivity, uri)) {

            if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(mActivity, contentUri, null, null);
            } else if (isExternalStorageDocument(uri)) {
                final String wholeID = DocumentsContract.getDocumentId(uri);
                final String[] split = wholeID.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isMediaDocument(uri)) {
                String wholeID = DocumentsContract.getDocumentId(uri);
                String[] split = wholeID.split(":");
                String id = split[1];
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[] { id };

                return getDataColumn(mActivity, contentUri, selection, selectionArgs);
            }
        } else {
            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }

            String[] projection = { MediaStore.Images.Media.DATA };
            Cursor cursor = mActivity.getContentResolver().query(uri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    public static int[] getIntDimension(String path) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        // 不分配内存，只获取大小
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int[] dimes = new int[2];
        dimes[0] = options.outWidth;
        dimes[1] = options.outHeight;
        return dimes;
    }

    /**
     * 转换图片成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    /**
     * 转换成方形图片
     * @param bitmap
     * @return
     */
    public static Bitmap toSquareBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRect(rectF, paint);
//        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    public static Bitmap GetRoundedCornerBitmap(Bitmap bitmap, final float roundPx) {
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight() * 2));
//            final float roundPx = 15;
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.BLACK);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

            final Rect src = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());

            canvas.drawBitmap(bitmap, src, rect, paint);
            return output;
        } catch (Exception e) {
            return bitmap;
        }
    }

    public static String obtainUrl(String url, int size_w, int size_y){
        if (StringUtil.isEmpty(url)){
            return url;
//            throw new IllegalArgumentException("the original url can not be empty");
        }

//        if (size_w<0||size_y<0){
//            throw new IllegalArgumentException();
//        }
        if (url.contains("thumbnail")){
            //do not handle the original url if the pic has been compressed
           return url;
        }
        return url.contains("imageMogr2")?url+"/thumbnail/"+size_w+"x"+size_y:url+"?imageMogr2/thumbnail/"+size_w+"x"+size_y;
    }

    public static String getFitSizeImgUrl(String url, int width, int height) {
        if (width > height) {//根据宽高自适应，保证不比例失真
            return url.contains("imageMogr2") ? url + "/thumbnail/" + width + "x" : url + "?imageMogr2/thumbnail/" + width + "x";
        } else if (width == height) {
            return url.contains("imageMogr2") ? url + "/thumbnail/" + width + "x" + height : url + "?imageMogr2/thumbnail/" + width + "x" + height;
        } else {
            return url.contains("imageMogr2") ? url + "/thumbnail/x" + height : url + "?imageMogr2/thumbnail/x" + height;
        }

    }

    public static String getNewsImageUrl(String url, int width, int height) {
       return url+"?imageMogr2/thumbnail/"+width+"x"+height;
    }

    public static Bitmap toRoundedCornerBitmap(Bitmap bitmap, final float roundPx) {
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight()));
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.BLACK);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

            final Rect src = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());

            canvas.drawBitmap(bitmap, src, rect, paint);
            return output;
        } catch (Exception e) {
            return bitmap;
        }
    }
}
