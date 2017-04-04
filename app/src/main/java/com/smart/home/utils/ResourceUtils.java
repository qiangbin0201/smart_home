package com.smart.home.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public class ResourceUtils {

	/**
	 * 获取图片数组ID
	 * 
	 * @param context
	 * @param rid
	 * @return
	 * @author lei.qu
	 */
	public static int[] convertDrawableNamesToIds(Context context, int rid) {
		Resources res = context.getResources();
		String[] array = res.getStringArray(rid);
		int length = array.length;
		int[] result = null;

		if (length > 0) {
			result = new int[length];
			String packageName = context.getPackageName();
			for (int i = 0; i < length; i++) {
				// 资源名称的字符串数组,资源类型,R类所在的包名
				int id = res.getIdentifier(array[i], "drawable", packageName);
				result[i] = id;
			}
		}
		return result;
	}

	/**
	 * 获取id数组ID
	 * 
	 * @param context
	 * @param rid
	 * @return
	 * @author lei.qu
	 */
	public static int[] convertIdNamesToIds(Context context, int rid) {
		Resources res = context.getResources();
		String[] array = res.getStringArray(rid);
		int length = array.length;
		int[] result = null;
		if (length > 0) {
			result = new int[length];
			String packageName = context.getPackageName();
			for (int i = 0; i < length; i++) {
				// 资源名称的字符串数组,资源类型,R类所在的包名
				int id = res.getIdentifier(array[i], "id", packageName);
				result[i] = id;
			}
		}
		return result;
	}

	/**
	 * @param mContext
	 * @param name
	 * @param resType
	 * @return
	 */
	public static int convertToResId(Context mContext, String name, String resType) {
		return mContext.getResources().getIdentifier(name, resType, mContext.getPackageName());
	}

	public static int convertToDrawableId(Context mContext, String name) {
		return mContext.getResources().getIdentifier(name, "drawable", mContext.getPackageName());
	}

	public static void settIntrinsicBounds(Drawable drawable) {
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
	}
}
