package com.smart.home.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.text.TextUtils;
import android.view.View;
import android.view.View.MeasureSpec;

import com.common.auththirdsdk.utils.LogUtil;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtils {
	public static Bitmap formateViewtoBitmap(View view) {
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		return bitmap;
	}

	public static Bitmap convertViewToBitmap(View view, int bitmapWidth,
											 int bitmapHeight) {
		Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight,
				Config.ARGB_8888);
		view.draw(new Canvas(bitmap));

		return bitmap;
	}

	public static boolean saveImage(Bitmap bitmap, String absPath) {
		return saveBitmap(bitmap, absPath, 100);
	}

	public static boolean saveBitmap(Bitmap bitmap, String absPath,
									 int quality) {
		try {
			FileOutputStream fos = new FileOutputStream(absPath);
			bitmap.compress(CompressFormat.JPEG, quality, fos);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}


	public final static Bitmap getImage(String imagePath) {
		return getImageThumbnail(imagePath, 0, 0);
	}

	public final static Bitmap getImageThumbnail(String imagePath, int width,
												 int height) {
		if (width == 0 || height == 0) {
			return BitmapFactory.decodeFile(imagePath);
		}
		Options options = createDecodeBoundsOptions();
		BitmapFactory.decodeFile(imagePath, options);
		calcSizeOptions(options, width, height);
		try {
			Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
			Bitmap result = ThumbnailUtils.extractThumbnail(bitmap, width,
					height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
			System.gc();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		return null;
	}

	//
	// /**
	// * 将byte数组解析成目标宽高的图片
	// *
	// * @param data
	// * @param desireWidth
	// * @param desireHeight
	// * @return IntKeyEntry(key表示缩放倍数)
	// */
	// public static Bitmap decodeByteArray(byte[] data,
	// int desireWidth, int desireHeight) {
	// if (desireWidth == 0 || desireHeight == 0) {
	// return BitmapFactory.decodeByteArray(data, 0, data.length);
	// }
	// BitmapFactory.Options options = calcSizeOptions(data, desireWidth,
	// desireHeight);
	// Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,
	// options);
	// return bitmap;
	// }

	/**
	 * 根据byte数组和目标宽高计算并返回Options
	 * 
	 * @param data
	 * @param desireWidth
	 * @param desireHeight
	 * @return
	 */
	public static Options calcSizeOptions(byte[] data, int desireWidth,
										  int desireHeight) {
		Options options = createDecodeBoundsOptions();
		BitmapFactory.decodeByteArray(data, 0, data.length, options);
		calcSizeOptions(options, desireWidth, desireHeight);
		return options;
	}

	/**
	 * 根据options计算图片所需字节数量
	 * 
	 * @param options
	 * @return
	 */
	public static int calcSizeByOptions(Options options) {
		return (options.outWidth * options.outHeight * BYTE_PER_PIXEL)
				/ options.inSampleSize;
	}

	/**
	 * 生成一个方形的bitmap
	 * 
	 * @param source
	 * @return
	 */
	public static Bitmap squareBitmap(Bitmap source) {
		int width = source.getWidth();
		int height = source.getHeight();
		int size = width < height ? width : height;
		if (width == height) {
			return source;
		}
		int x = (width - size) / 2;
		int y = (height - size) / 2;
		return Bitmap.createBitmap(source, x, y, size, size);
	}

	/**
	 * 根据最小边(取宽高中较小的值为基准)压缩图片
	 * 
	 * @param srcPath
	 * @param destPath
	 * @param destWidth
	 * @param maxFileSize
	 * @param quarity
	 *            当>0且maxFileSize比宽高压缩后的文件大小小时只做一次质量的压缩
	 * @return
	 */
	public final static boolean compressByMinSize(String srcPath,
												  String destPath, int destWidth, int maxFileSize, int quarity) {
		return compress(srcPath, destPath, destWidth, Integer.MAX_VALUE,
				maxFileSize, true, quarity);
	}

	/**
	 * <pre>
	 * &#64;time Apr 3, 2014
	 * 
	 * &#64;param srcPath
	 *            path of local file
	 * &#64;param destPath
	 *            dest path when is compressed
	 * &#64;param maxFileSize
	 *            max size of dest file to compress
	 * &#64;param maxMinSize
	 *            max size of min from(destWidth, destHeight)
	 * &#64;param maxLargeSize
	 * 			  max size of max from(destWidth, destHeight)
	 * &#64;param override
	 *            if destPath is already exists file, if override, it will
	 *            override it.if not do nothing
	 * &#64;throws it's invalid and do nothing when both maxWidth and maxHeight is -1
	 * </pre>
	 */
	public final static boolean compress(String srcPath, String destPath,
										 int maxMinSize, int maxLargeSize, long maxFileSize,
										 boolean override, int targetQuality) {
		if (maxMinSize <= 0 || maxFileSize <= 0 || TextUtils.isEmpty(srcPath)) {
			return false;
		}
		File srcFile = new File(srcPath);
		if (!srcFile.exists()) {
			return false;
		}
		if (FileUtils.exists(destPath)) {
			if (!override) {
				return false;
			}
			FileUtils.deleteDir(destPath);
		}
		final long srcFileSize = srcFile.length();

		// read image with and height
		Options options = getOptions(srcPath);
		final int srcWidth = options.outWidth;
		final int srcHeight = options.outHeight;

		// the min size is maxMin
		float destRatio = 1.0f;
		float minSize = 0;
		float maxSize = 0;
		if (srcWidth > srcHeight) {
			minSize = srcHeight;
			maxSize = srcWidth;
		} else {
			minSize = srcWidth;
			maxSize = srcHeight;
		}
		if (minSize > maxMinSize) {
			destRatio = ((float) maxMinSize) / minSize;
		}
		if (maxSize > maxLargeSize) {
			destRatio = Math.min((float) maxLargeSize / maxSize, destRatio);
		}
		int destWidth = (int) (srcWidth * destRatio);
		int destHeight = (int) (srcHeight * destRatio);

		// read bitmap from sdcard
		Bitmap bitmap = getBitmap(srcPath, destWidth, destHeight);
		if (bitmap == null) {
			return false;
		}

		destWidth = bitmap.getWidth();
		destHeight = bitmap.getHeight();
		File outputFile = new File(destPath);
		FileOutputStream out = null;
		try {
			int quality = targetQuality;
			float ratio = 0;
			// calculate quality
			ratio = ((float) (srcWidth * srcHeight)) / (destWidth * destHeight);
			long losssLessSize = (long) (srcFileSize / ratio);
			if (maxFileSize >= losssLessSize) {
				// 当前文件大小已经满足要求的文件大小
				quality = 100;
			} else if (targetQuality <= 0) {
				quality = (int) Math
						.floor(100.00 * maxFileSize / losssLessSize);
				if (quality <= 0) {
					quality = 1;
				} else if (quality > 100) {
					quality = 100;
				}
			}
			long start = System.currentTimeMillis();
			long curSize = 0;
			int ceilQuality = quality > 70 ? 100 : quality + 30;
			int floorQuality = quality < 30 ? 0 : quality - 30;
			while (true) {
				if (out != null) {
					out.close();
				}
				out = new FileOutputStream(outputFile);
				BufferedOutputStream stream = new BufferedOutputStream(out);
				bitmap.compress(CompressFormat.JPEG, quality, stream);
				stream.flush();
				stream.close();
				curSize = FileUtils.size(destPath);
				if (targetQuality > 0) {
					// 指定了图片压缩质量， 对质量只压缩一次
					break;
				}
				if (curSize - maxFileSize <= 5 * 1024
						|| Math.abs(ceilQuality - floorQuality) <= 1) {
					// 压缩的文件大小与要求的文件大小之间相差很小, 或者质量已不能再小
					break;
				}
				if (curSize < maxFileSize) {
					floorQuality = quality;
				} else {
					ceilQuality = quality;
				}
				quality = (ceilQuality + floorQuality) / 2;
			}
			long duration = System.currentTimeMillis() - start;
//			if (LogUtils.isDebug()) {
//				LogUtils.d(TAG,
//						"compress-dest-file-size " + srcFileSize + "|"
//								+ maxFileSize + " ratio=" + ratio + " quality="
//								+ quality + " duration=" + duration + " size:"
//								+ destWidth + "x" + destHeight + "|" + srcWidth
//								+ "x" + srcHeight + " finalFileSize="
//								+ FileUtils.size(destPath));
//			}
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		FileUtils.deleteDir(destPath);

		return false;

	}

	public static Bitmap getBitmap(String absPath, int width, int height) {
		if (width <= 0 && height <= 0 || !FileUtils.exists(absPath)) {
			return null;
		}

		LogUtil.d(TAG, "decode bitmap " + absPath + " width " + width
				+ " height " + height);
		Bitmap bitmap = null;
		Options options = getOptions(absPath);
		calcSizeOptions(options, width, height);
		bitmap = BitmapFactory.decodeFile(absPath, options);
		if (bitmap == null) {
			return null;
		}

		float ratioW = ((float) width) / bitmap.getWidth();
		float ratioH = ((float) height) / bitmap.getHeight();
		float ratio = Math.min(ratioW, ratioH);
		if (ratio > 1 || ratio <= 0) {
			ratio = 1;
		}

		LogUtil.d(TAG, "decode bitmap final=" + width + "x" + height
				+ " ratio=" + ratio);

		Matrix matrix = new Matrix();

		int rotate = getRotate(absPath);
		if (rotate > 0) {
			matrix.setRotate(rotate);
		}

		matrix.postScale(ratio, ratio);

		Bitmap rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);

		if (rotated == null) {
			return bitmap;
		}
//		LogUtil.d(TAG, "decode bitmap final=%d x %d", rotated.getWidth(),
//				rotated.getHeight());
		return rotated;
	}
 

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap){
	        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
	        Canvas canvas = new Canvas(outBitmap);
//	        final int color =0xff424242;
	        final Paint paint = new Paint();
	        final Rect rect = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
	        final RectF rectF = new RectF(rect);
	        final float roundPX = bitmap.getWidth()/2;
	        paint.setAntiAlias(true);
	        canvas.drawARGB(0,0,0,0);
//	        paint.setColor(color);
	        canvas.drawRoundRect(rectF, roundPX, roundPX, paint);
	        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	        canvas.drawBitmap(bitmap, rect, rect, paint);
	        return outBitmap;
	    }
	
	private static final String TAG = "BitmapUtils";

	/**
	 * 
	 * @param sourceBitmap
	 * @param destWidth
	 * @param destHeight
	 * @param uniform
	 *            是否等比缩放
	 * @return
	 */
	public final static Bitmap zoom(Bitmap sourceBitmap, int destWidth,
									int destHeight, boolean uniform) {
		if (sourceBitmap == null) {
			return null;
		}
		if (destHeight <= 0 || destWidth <= 0) {
			LogUtil.d(TAG, "invalid paraments destWidth = " + destWidth
					+ ";  destHeight=" + destHeight);
			return sourceBitmap;
		}
		float srcWidth = sourceBitmap.getWidth();
		float srcHeight = sourceBitmap.getHeight();
		float widthRatio = destWidth / srcWidth;
		float heightRatio = destHeight / srcHeight;
		if (widthRatio >= 1 || heightRatio >= 1) {
			return sourceBitmap;
		}
		Matrix matrix = new Matrix();
		matrix.reset();
		if (uniform) {
			float resultScale = widthRatio > heightRatio ? heightRatio
					: widthRatio;
			matrix.postScale(resultScale, resultScale);
		} else {
			matrix.postScale(widthRatio, heightRatio);
		}

		Bitmap resizedBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0,
				(int) srcWidth, (int) srcHeight, matrix, true);
		// recycle(sourceBitmap);
		return resizedBitmap;
	}

	public static int getRotate(String absPath) {
		if (!FileUtils.exists(absPath)) {
			LogUtil.e(TAG, "invalid file path");
			return 0;
		}

		ExifInterface exifInterface = null;
		try {
			exifInterface = new ExifInterface(absPath);
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}

		int orientation = exifInterface.getAttributeInt(
				ExifInterface.TAG_ORIENTATION,
				ExifInterface.ORIENTATION_UNDEFINED);
		int rotate = 0;
		switch (orientation) {
		case ExifInterface.ORIENTATION_ROTATE_90:
			rotate = 90;
			break;
		case ExifInterface.ORIENTATION_ROTATE_180:
			rotate = 180;
			break;
		case ExifInterface.ORIENTATION_ROTATE_270:
			rotate = 270;
			break;
		default:
			break;
		}
		LogUtil.e(TAG, "image rotate " + rotate);
		return rotate;
	}

	// 每个像素点所占用字节数
	private static final int BYTE_PER_PIXEL = 4;

	public static Options getOptions(String filePath) {
		Options options = createDecodeBoundsOptions();
		BitmapFactory.decodeFile(filePath, options);
		return options;
	}

	/**
	 * 创建用于获取图片宽高的options
	 * 
	 * @return
	 */
	private static Options createDecodeBoundsOptions() {
		Options options = new Options();
		options.inPreferredConfig = Config.ALPHA_8;
		options.inJustDecodeBounds = true;
		return options;
	}

	/**
	 * 根据已取得的图片实际宽高计算缩放到目标宽高的缩放倍数
	 * 
	 * @param options
	 * @param desireWidth
	 * @param desireHeight
	 */
	private static void calcSizeOptions(Options options, int desireWidth,
										int desireHeight) {
		if (desireWidth <= 0 || desireHeight <= 0) {
			options.inSampleSize = 1;
		} else {
			options.inSampleSize = findBestSampleSize(options.outWidth,
					options.outHeight, desireWidth, desireHeight);
		}
		options.inJustDecodeBounds = false;
	}

	/**
	 * Returns the largest power-of-two divisor for use in downscaling a bitmap
	 * that will not result in the scaling past the desired dimensions.
	 * 
	 * @param actualWidth
	 *            Actual width of the bitmap
	 * @param actualHeight
	 *            Actual height of the bitmap
	 * @param desiredWidth
	 *            Desired width of the bitmap
	 * @param desiredHeight
	 *            Desired height of the bitmap
	 */
	static int findBestSampleSize(int actualWidth, int actualHeight,
			int desiredWidth, int desiredHeight) {
		double wr = (double) actualWidth / desiredWidth;
		double hr = (double) actualHeight / desiredHeight;
		double ratio = Math.min(wr, hr);
		float n = 1.0f;
		while ((n * 2) <= ratio) {
			n *= 2;
		}

		return (int) n;
	}

	/**
	 * 压缩图片bmp，使之小于限定值。
	 * 
	 * @param image
	 * @param maxKb
	 *            最大值 ，单位kb
	 */
	public static Bitmap compressImage(Bitmap image, int maxKb) {
		long bytes = image.getRowBytes() * image.getHeight();
		if ((bytes * 1f / 1024) <= maxKb) {
			// 图片本身就小本规定大小
			return image;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(CompressFormat.JPEG, 100, baos);
		int options = 100;
		while ((baos.toByteArray().length / 1024) > maxKb) { // 循环判断如果压缩后图片是否大于maxKb,大于继续压缩
			baos.reset();
			image.compress(CompressFormat.JPEG, options, baos);
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(
				baos.toByteArray());
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
		return bitmap;
	}

	/**
	 * 将View转换成bitmap
	 * 
	 * @param v
	 * @return
	 */
	public static Bitmap generateBitmapByView(View v) {
		Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
				Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		v.draw(canvas);
		return bitmap;
	}

}
