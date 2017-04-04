package com.smart.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.text.TextUtils;

import java.io.IOException;

/**
 * 图片处理
 *
 * @author haorongrong
 */
public class ImageUtils {

    /**
     * 图片旋转
     *
     * @param bmp    要旋转的图片
     * @param degree 图片旋转的角度，负值为逆时针旋转，正值为顺时针旋转
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bmp, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
    }

    /**
     * resize the bitmap to fit width size.
     *
     * @param source      bitmap to be scaled.
     * @param targetWidth target width image size of the bitmap to be scaled.
     * @return scaled bitmap image.
     */
    public static Bitmap ScaleFitX(Bitmap source, int targetWidth) {
        if (source == null || targetWidth == 0) {
            return source;
        }
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();
        int targetHeight = targetWidth * sourceHeight / sourceWidth;
        if (targetHeight < 0) {
            return source;
        }

        return resize(source, targetWidth, targetHeight);
    }

    /**
     * resize the bitmap to fit height size.
     *
     * @param source       bitmap to be scaled.
     * @param targetHeight target height image size of the bitmap to be scaled.
     * @return scaled bitmap image.
     */
    public static Bitmap ScaleFitY(Bitmap source, int targetHeight) {
        if (source == null || targetHeight == 0) {
            return source;
        }
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();
        int targetWidth = targetHeight * sourceWidth / sourceHeight;
        if (targetWidth < 0) {
            return source;
        }

        return resize(source, targetWidth, targetHeight);
    }

    public static Bitmap resize(Bitmap source, float scaleFactor) {
        if (source == null || source.isRecycled()) {
            return null;
        }
        if (0.04 < scaleFactor && scaleFactor < 0.96) {
            int originalWidth = source.getWidth();
            int orginalHeight = source.getHeight();
            return resize(source, (int) (originalWidth * scaleFactor),
                    (int) (orginalHeight * scaleFactor));
        }
        return source;
    }

    /**
     * Resizes the bitmap image.
     *
     * @param source    bitmap to be resized.
     * @param newWidth  new width of bitmap to be resized.
     * @param newHeight new height of bitmap to be resized.
     * @return
     */
    public static Bitmap resize(Bitmap source, int newWidth, int newHeight) {
        if (source == null || source.isRecycled()) {
            return null;
        }

        Matrix matrix = new Matrix();
        int originalWidth = source.getWidth();
        int orginalHeight = source.getHeight();
        matrix.postScale(((float) newWidth / originalWidth),
                ((float) newHeight / orginalHeight));

        Bitmap output = null;
        int count = 0;
        do {
            count++;
            try {
                output = Bitmap.createBitmap(source, 0, 0, originalWidth,
                        orginalHeight, matrix, false);
            } catch (OutOfMemoryError ex) {
                ex.printStackTrace();
            }
        } while (output == null && count <= 3);
        if (output != null && !source.equals(output)) {
            source.recycle();
        }
        return output;
    }

    /**
     * 图片缩放
     *
     * @param bm
     * @param scale 值小于则为缩小，否则为放大
     * @return
     */
    public static Bitmap resizeBitmap(Bitmap bm, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
    }

    /**
     * 图片缩放
     *
     * @param bm
     * @param w
     *            缩小或放大成的宽
     * @param h
     *            缩小或放大成的高
     * @return
     */
//	public static Bitmap resizeBitmap(Bitmap bm, int w, int h) {
//		if (bm == null || bm.isRecycled()) {
//			return null;
//		}
//		Bitmap BitmapOrg = bm;
//
//		int width = BitmapOrg.getWidth();
//		int height = BitmapOrg.getHeight();
//
//		float scaleWidth = ((float) w) / width;
//		float scaleHeight = ((float) h) / height;
//
//		Matrix matrix = new Matrix();
//		matrix.postScale(scaleWidth, scaleHeight);
//		return Bitmap.createBitmap(BitmapOrg, 0, 0, width, height, matrix, true);
//	}

    /**
     * 图片缩放(自适应)
     *
     * @param bm
     * @return
     */
    public static Bitmap resizeBitmap(Bitmap bm) {
        if (bm == null || bm.isRecycled()) {
            return null;
        }

        return resizeBitmap(bm, 200, 200, false);
    }

    private static final int MAX_DECODE_PICTURE_SIZE = 800 * 600;

    public static Bitmap resizeBitmap(Bitmap tmp, final int width, final int height, final boolean crop) {
        try {
            int outHeight = tmp.getHeight();
            int outWidth = tmp.getWidth();

            final double beY = outHeight * 1.0 / height;
            final double beX = outWidth * 1.0 / width;
            int inSampleSize = (int) (crop ? (beY > beX ? beX : beY)
                    : (beY < beX ? beX : beY));
            if (inSampleSize <= 1) {
                inSampleSize = 1;
            }

            // NOTE: out of memory error
            while (outHeight * outWidth / inSampleSize > MAX_DECODE_PICTURE_SIZE) {
                inSampleSize++;
            }

            int newHeight = height;
            int newWidth = width;
            if (crop) {
                if (beY > beX) {
                    newHeight = (int) (newWidth * 1.0 * outHeight / outWidth);
                } else {
                    newWidth = (int) (newHeight * 1.0 * outWidth / outHeight);
                }
            } else {
                if (beY < beX) {
                    newHeight = (int) (newWidth * 1.0 * outHeight / outWidth);
                } else {
                    newWidth = (int) (newHeight * 1.0 * outWidth / outHeight);
                }
            }

//			return resize(tmp, newWidth, newHeight);

            Bitmap scale = Bitmap.createScaledBitmap(tmp, newWidth,
                    newHeight, true);

            if (crop) {
                final Bitmap cropped = Bitmap.createBitmap(scale,
                        (tmp.getWidth() - width) >> 1,
                        (tmp.getHeight() - height) >> 1, width, height);
                if (cropped == null) {
                    return tmp;
                }

                scale.recycle();
                scale = cropped;
            }
            return scale;

        } catch (final OutOfMemoryError e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 使用Bitmap加Matrix来缩放
     *
     * @param bitmap
     * @param w
     * @param h
     * @return
     */
    public static Bitmap resizeBitmap(Bitmap bitmap, int w, int h) {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);
        return resizedBitmap;
    }

    /**
     * 图片反转
     *
     * @param bmp
     * @param flag 0为水平反转，1为垂直反转
     * @return
     */
    public static Bitmap reverseBitmap(Bitmap bmp, int flag) {
        float[] floats = null;
        switch (flag) {
            case 0: // 水平反转
                floats = new float[]{-1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f};
                break;
            case 1: // 垂直反转
                floats = new float[]{1f, 0f, 0f, 0f, -1f, 0f, 0f, 0f, 1f};
                break;
        }

        if (floats != null) {
            Matrix matrix = new Matrix();
            matrix.setValues(floats);
            return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        }

        return null;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 检测图片是否需要压缩
     *
     * @param path
     * @return 压缩的 sampleSize
     */
    public static int checkNeedCompress(String path) {
        int sampleSize = 2;
        int maxMemory = (int) (Runtime.getRuntime().maxMemory());
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        int picSize = imageWidth * imageHeight * 4;
        int memorySize = maxMemory / 8;
        if (picSize > memorySize) {
            sampleSize = (int) Math.ceil(picSize / memorySize);
            if (sampleSize < 2)
                sampleSize = 2;
        }
        return sampleSize;
    }

    /**
     * 从path图片文件路径转码到bitmap文件
     *
     * @param path
     * @return
     */
    public static Bitmap decodeBitmapFromPath(String path) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        //int degree = ImageUtils.readPictureDegree(path);
        int sampleSize = ImageUtils.checkNeedCompress(path);
        if (sampleSize > 0) {
            options.inSampleSize = sampleSize;
        }
        options.inJustDecodeBounds = false;
        // 调用上面定义的方法计算inSampleSize值
        // 使用获取到的inSampleSize值再次解析图片
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        //Bitmap newbitmap = ImageUtils.rotateBitmap(bitmap, degree);
        // if(!bitmap.isRecycled())
        // bitmap.recycle();
        return bitmap;
    }

    public enum ImageSize {
        SMALL,
        MIDDLE,
        ORIGIN;
    }

    public static String getImageSizeURL(ImageSize imageSize, String url) {

        if (!TextUtils.isEmpty(url)) {
            if (imageSize == ImageSize.SMALL) {
                return url.substring(0, url.length() - 1) + "1";
            } else if (imageSize == ImageSize.MIDDLE) {
                return url.substring(0, url.length() - 1) + "2";
            } else {
                return url.substring(0, url.length() - 1);
            }
        }

        return "";
    }

    public static String getGifCoverURL(String url) {
        if (!TextUtils.isEmpty(url) && url.length() > 1) {
            return url.substring(0, url.length() - 1) + "4";
        }

        return "";
    }
}

