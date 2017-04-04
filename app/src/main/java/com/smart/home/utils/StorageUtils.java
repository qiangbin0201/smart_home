/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.smart.home.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.bigbang.news.R;
import com.bigbang.news.TTYCApplication;

import java.io.File;
import java.io.IOException;

/**
 * Provides application storage paths
 *
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @since 1.0.0
 */
public final class StorageUtils {

    private static final String INDIVIDUAL_DIR_NAME = "uil-images";

    private StorageUtils() {
    }


    public static String getDestSaveDir() {
        String appName = TTYCApplication.mContext.getString(R.string.app_name);
        String path = getDCIMDir() + File.separator + appName;
        return path;
    }


    public static String getDCIMDir() {
        try {
            return Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns application cache directory. Cache directory will be created on SD card
     * <i>("/Android/data/[app_package_name]/cache")</i> if card is mounted. Else - Android defines cache directory on
     * device's file system.
     *
     * @param context Application context
     * @return Cache {@link File directory}
     */
    public static File getCacheDirectory(Context context) {
        File appCacheDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            appCacheDir = getExternalCacheDir(context);
        }
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        return appCacheDir;
    }

    /**
     * Returns individual application cache directory (for only image caching from ImageLoader). Cache directory will be
     * created on SD card <i>("/Android/data/[app_package_name]/cache/uil-images")</i> if card is mounted. Else -
     * Android defines cache directory on device's file system.
     *
     * @param context Application context
     * @return Cache {@link File directory}
     */
    public static File getIndividualCacheDirectory(Context context) {
        File cacheDir = getCacheDirectory(context);
        File individualCacheDir = new File(cacheDir, INDIVIDUAL_DIR_NAME);
        if (!individualCacheDir.exists()) {
            if (!individualCacheDir.mkdir()) {
                individualCacheDir = cacheDir;
            }
        }
        return individualCacheDir;
    }

    /**
     * Returns specified application cache directory. Cache directory will be created on SD card by defined path if card
     * is mounted. Else - Android defines cache directory on device's file system.
     *
     * @param context Application context
     * @param cacheDir Cache directory path (e.g.: "AppCacheDir", "AppDir/cache/images")
     * @return Cache {@link File directory}
     */
    public static File getOwnCacheDirectory(Context context, String cacheDir) {
        File appCacheDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            appCacheDir = new File(Environment.getExternalStorageDirectory(), cacheDir);
        }
        if (appCacheDir == null || (!appCacheDir.exists() && !appCacheDir.mkdirs())) {
            appCacheDir = context.getCacheDir();
        }
        return appCacheDir;
    }

    private static File getExternalCacheDir(Context context) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                Log.w("SU", "Unable to create external cache directory");
                return null;
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
                Log.w("SU", "Can't create \".nomedia\" file in application external cache directory");
            }
        }
        return appCacheDir;
    }

    /**
     * 生成图片的存储文件（生成的文件支持清空功能）
     * @param context
     * @param imageUri
     * @return
     */
    public static File genDiscCacheFile(Context context, String imageUri) {
        File individualCacheDir = getIndividualCacheDirectory(context);
        String fileName = String.valueOf(imageUri.hashCode());
        File file = new File(individualCacheDir, fileName);
        Long currentTime = System.currentTimeMillis();
        file.setLastModified(currentTime);
        return file;
    }

    /**
     * 由图片路径找到本地存储的文件对象
     * @param context
     * @param imageUri
     * @return
     */
    public static File findInDiscCache(Context context, String imageUri) {
        File individualCacheDir = getIndividualCacheDirectory(context);
        String fileName = String.valueOf(imageUri.hashCode());
        File image = new File(individualCacheDir, fileName);
        return image.exists() ? image : null;
    }
}
