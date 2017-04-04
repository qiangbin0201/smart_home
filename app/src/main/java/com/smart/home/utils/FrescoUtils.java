package com.smart.home.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.bigbang.news.Packet;
import com.bigbang.news.PacketCollector;
import com.bigbang.news.R;
import com.bigbang.news.TTYCApplication;
import com.bigbang.news.app.schema.TrafficSchema;
import com.bigbang.news.app.schema.TrafficSchemaManager;
import com.bigbang.news.model.Favorite;
import com.bigbang.news.model.News;
import com.bigbang.news.page.news.NewsType;
import com.bigbang.news.page.pic.OkHttpNetworkFetcher;
import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.internal.Supplier;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.ByteConstants;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.image.CloseableBitmap;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.producers.NetworkFetcher;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;

/**
 * Created by zhangxin on 2016/7/9.
 */
public class FrescoUtils {
    // 分配的可用内存
    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime()
            .maxMemory();
    // 使用的缓存数量
    private static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;
    // 小图极低磁盘空间缓存的最大值（特性：可将大量的小图放到额外放在另一个磁盘空间防止大图占用磁盘空间而删除了大量的小图）
    private static final int MAX_SMALL_DISK_VERYLOW_CACHE_SIZE = 10 * ByteConstants.MB;
    // 小图低磁盘空间缓存的最大值（特性：可将大量的小图放到额外放在另一个磁盘空间防止大图占用磁盘空间而删除了大量的小图）
    private static final int MAX_SMALL_DISK_LOW_CACHE_SIZE = 20 * ByteConstants.MB;
    // 小图磁盘缓存的最大值（特性：可将大量的小图放到额外放在另一个磁盘空间防止大图占用磁盘空间而删除了大量的小图）
    private static final int MAX_SMALL_DISK_CACHE_SIZE = 40 * ByteConstants.MB;
    // 默认图极低磁盘空间缓存的最大值
    private static final int MAX_DISK_CACHE_VERYLOW_SIZE = 20 * ByteConstants.MB;
    // 默认图低磁盘空间缓存的最大值
    private static final int MAX_DISK_CACHE_LOW_SIZE = 60 * ByteConstants.MB;
    // 默认图磁盘缓存的最大值
    private static final int MAX_DISK_CACHE_SIZE = 100 * ByteConstants.MB;
    // 小图所放路径的文件夹名
    private static final String IMAGE_PIPELINE_SMALL_CACHE_DIR = "imagepipeline_cache";
    // 默认图所放路径的文件夹名
    private static final String IMAGE_PIPELINE_CACHE_DIR = "imagepipeline_cache";

    public static ImagePipelineConfig getDefaultImagePipelineConfig(Context context) {
        // 内存配置
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                MAX_MEMORY_CACHE_SIZE, // 内存缓存中总图片的最大大小,以字节为单位。
                1024, // 内存缓存中图片的最大数量。
                MAX_MEMORY_CACHE_SIZE / 4, // 内存缓存中准备清除但尚未被删除的总图片的最大大小,以字节为单位。
                512, // 内存缓存中准备清除的总图片的最大数量。
                MAX_HEAP_SIZE / 8); // 内存缓存中单个图片的最大大小。

        // 修改内存图片缓存数量，空间策略（这个方式有点恶心）
        Supplier<MemoryCacheParams> mSupplierMemoryCacheParams = new Supplier<MemoryCacheParams>() {
            @Override
            public MemoryCacheParams get() {
                return bitmapCacheParams;
            }
        };

        // 小图片的磁盘配置
        DiskCacheConfig diskSmallCacheConfig = DiskCacheConfig
                .newBuilder(context)
                .setBaseDirectoryPath(
                        context.getApplicationContext().getCacheDir())// 缓存图片基路径
                .setBaseDirectoryName(IMAGE_PIPELINE_SMALL_CACHE_DIR)// 文件夹名
                // .setCacheErrorLogger(cacheErrorLogger)//日志记录器用于日志错误的缓存。
                // .setCacheEventListener(cacheEventListener)//缓存事件侦听器。
                // .setDiskTrimmableRegistry(diskTrimmableRegistry)//类将包含一个注册表的缓存减少磁盘空间的环境。
                .setMaxCacheSize(MAX_DISK_CACHE_SIZE)// 默认缓存的最大大小。
                .setMaxCacheSizeOnLowDiskSpace(MAX_SMALL_DISK_LOW_CACHE_SIZE)// 缓存的最大大小,使用设备时低磁盘空间。
                .setMaxCacheSizeOnVeryLowDiskSpace(
                        MAX_SMALL_DISK_VERYLOW_CACHE_SIZE)// 缓存的最大大小,当设备极低磁盘空间
                // .setVersion(version)
                .build();

        // 默认图片的磁盘配置
        DiskCacheConfig diskCacheConfig = DiskCacheConfig
                .newBuilder(context)
                .setBaseDirectoryPath(
                        StorageUtils.getCacheDirectory(context))// 缓存图片基路径
                .setBaseDirectoryName(IMAGE_PIPELINE_CACHE_DIR)// 文件夹名
                // .setCacheErrorLogger(cacheErrorLogger)//日志记录器用于日志错误的缓存。
                // .setCacheEventListener(cacheEventListener)//缓存事件侦听器。
                // .setDiskTrimmableRegistry(diskTrimmableRegistry)//类将包含一个注册表的缓存减少磁盘空间的环境。
                .setMaxCacheSize(MAX_DISK_CACHE_SIZE)// 默认缓存的最大大小。
                .setMaxCacheSizeOnLowDiskSpace(MAX_DISK_CACHE_LOW_SIZE)// 缓存的最大大小,使用设备时低磁盘空间。
                .setMaxCacheSizeOnVeryLowDiskSpace(MAX_DISK_CACHE_VERYLOW_SIZE)// 缓存的最大大小,当设备极低磁盘空间
                // .setVersion(version)
                .build();

        // 缓存图片配置
        ImagePipelineConfig.Builder configBuilder = ImagePipelineConfig
                .newBuilder(context)
                // .setAnimatedImageFactory(AnimatedImageFactory
                // animatedImageFactory)//图片加载动画
                .setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams)// 内存缓存配置（一级缓存，已解码的图片）
                // .setCacheKeyFactory(cacheKeyFactory)//缓存Key工厂
                // .setEncodedMemoryCacheParamsSupplier(encodedCacheParamsSupplier)//内存缓存和未解码的内存缓存的配置（二级缓存）
                // .setExecutorSupplier(executorSupplier)//线程池配置
                // .setImageCacheStatsTracker(imageCacheStatsTracker)//统计缓存的命中率
                // .setImageDecoder(ImageDecoder imageDecoder) //图片解码器配置
                // .setIsPrefetchEnabledSupplier(Supplier<Boolean>
                // isPrefetchEnabledSupplier)//图片预览（缩略图，预加载图等）预加载到文件缓存
                // .setSmallImageDiskCacheConfig(diskSmallCacheConfig)//磁盘缓存配置（小图片，可选～三级缓存的小图优化缓存）
                // .setMemoryTrimmableRegistry(memoryTrimmableRegistry)
                // //内存用量的缩减,有时我们可能会想缩小内存用量。比如应用中有其他数据需要占用内存，不得不把图片缓存清除或者减小
                // 或者我们想检查看看手机是否已经内存不够了。
                .setNetworkFetcher(getConfigNetworkFetcher())//自定的网络层配置：如OkHttp，Volley
                // .setPoolFactory(poolFactory)//线程池工厂配置
                // .setProgressiveJpegConfig(progressiveJpegConfig)//渐进式JPEG图
                // .setRequestListeners(requestListeners)//图片请求监听
                // .setResizeAndRotateEnabledForNetwork(boolean
                // resizeAndRotateEnabledForNetwork)//调整和旋转是否支持网络图片
                .setMainDiskCacheConfig(diskCacheConfig)
                .setDownsampleEnabled(true);//配合resize方法使用
        return configBuilder.build();
    }

    @SuppressWarnings("rawtypes")
    protected static NetworkFetcher getConfigNetworkFetcher() {
        OkHttpClient okHttpClient = new OkHttpClient();
        WifiStateNetworkFetcher fetcher = new WifiStateNetworkFetcher(okHttpClient) {
            @Override
            public void fetch(OkHttpNetworkFetchState fetchState, NetworkFetcher.Callback callback) {
                if (!forbidden) {
                    super.fetch(fetchState, callback);
                } else {
                    AppLogUtils.i("FM WifiStateNetworkFetcher[FORBIDDEN]no-fetch");
                    callback.onCancellation();
                }
            }
        };
        return fetcher;
    }

    static class WifiStateNetworkFetcher extends OkHttpNetworkFetcher {

        public WifiStateNetworkFetcher(OkHttpClient okHttpClient) {
            super(okHttpClient);
        }

    }

    /**
     * 网络切换时，获取用户设置的值
     */
    static boolean forbidden = false;

    public static boolean isImageDownloaded(Uri loadUri) {

        if (loadUri == null) {
            return false;
        }
        CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(ImageRequest.fromUri(loadUri), null);
        return ImagePipelineFactory.getInstance().getMainDiskStorageCache().hasKey(cacheKey) || ImagePipelineFactory.getInstance().getSmallImageDiskStorageCache().hasKey(cacheKey);
    }

    //return file or null
    public static File getCachedImageOnDisk(Uri loadUri) {

        File localFile = null;
        if (loadUri != null) {
            CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(ImageRequest.fromUri(loadUri), null);
            if (ImagePipelineFactory.getInstance().getMainDiskStorageCache().hasKey(cacheKey)) {
                BinaryResource resource = ImagePipelineFactory.getInstance().getMainDiskStorageCache().getResource(cacheKey);
                localFile = ((FileBinaryResource) resource).getFile();
            } else if (ImagePipelineFactory.getInstance().getSmallImageDiskStorageCache().hasKey(cacheKey)) {
                BinaryResource resource = ImagePipelineFactory.getInstance().getSmallImageDiskStorageCache().getResource(cacheKey);
                localFile = ((FileBinaryResource) resource).getFile();
            }
        }
        return localFile;

    }

    public static boolean isInMemory(Uri uri) {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        boolean inMemoryCache = imagePipeline.isInBitmapMemoryCache(uri);
        return inMemoryCache;
    }

    public static void setCollectListImage(Favorite favorite, String imageUrl, SimpleDraweeView imageView) {
        setImageByTrafficSchema(favorite.type_name, false, favorite.isInCache, imageUrl, imageView, new Runnable() {
            @Override
            public void run() {
                favorite.isInCache = true;
                Uri uri = Uri.parse(imageUrl);
                imageView.setImageURI(uri);
                // 加上后可以保证图片显示，但列表滑动会卡顿
//                AppProxy.getInstance().getBus().post(new PicInCacheEvent(favorite.item_id));
            }
        });
    }

    public static void setImage(SimpleDraweeView imageView, String imageURL){
        setImageByTrafficSchema(NewsType.VIDEO.getValue(), true, false, imageURL, imageView, new Runnable() {
            @Override
            public void run() {
                Uri uri = Uri.parse(imageURL);
                imageView.setImageURI(uri);
            }
        });
    }

    public static void setNewsListImage(News news, String imageURL, SimpleDraweeView imageView) {
        setImageByTrafficSchema(news.newsType, true, news.isInCache, imageURL, imageView, new Runnable() {
            @Override
            public void run() {
                news.isInCache = true;
                Uri uri = Uri.parse(imageURL);
                imageView.setImageURI(uri);
                // 加上后可以保证图片显示，但列表滑动会卡顿
//                AppProxy.getInstance().getBus().post(new PicInCacheEvent(news.newsId));
            }
        });
    }

    private static void setImageByTrafficSchema(String newsType, boolean isInNewsList, boolean isInCache, String imageUrl, SimpleDraweeView imageView, Runnable incacheRunnable) {
        Uri uri = Uri.parse(imageUrl);
        TrafficSchema schema = TrafficSchemaManager.getInstance().getFitTrafficSchema(TTYCApplication.mContext);
//        AppLogUtils.d("current schema is " + (schema != null ? schema.id : "null"));
        if ((schema == null || schema.isNoSchema()) && !isInCache) {
            if (FrescoUtils.isInMemory(uri)) {
                imageView.setImageURI(uri);
                incacheRunnable.run();
            } else {
                FrescoUtils.isInDiskCache(uri, incacheRunnable);
                if ((NewsType.isPicType(newsType) || NewsType.isVideoType(newsType)) && isInNewsList) {
                    imageView.setImageURI("res:///" + R.drawable.logo_placeholder_big_smart_no_pic);
                } else {
                    imageView.setImageURI("res:///" + R.drawable.logo_placeholder_small_smart_no_pic);
                }
            }
        } else {
            imageView.setImageURI(uri);
        }
    }

    public static void isInDiskCache(Uri uri, Runnable runnable) {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<Boolean> inDiskCacheSource = imagePipeline.isInDiskCache(uri);
        DataSubscriber<Boolean> subscriber = new BaseDataSubscriber<Boolean>() {
            @Override
            protected void onNewResultImpl(DataSource<Boolean> dataSource) {
                if (!dataSource.isFinished()) {
                    return;
                }
                boolean isInCache = dataSource.getResult();
                // your code here
                if (isInCache && runnable != null) {
                    runnable.run();
                }
            }

            @Override
            protected void onFailureImpl(DataSource<Boolean> dataSource) {

            }
        };
        inDiskCacheSource.subscribe(subscriber, CallerThreadExecutor.getInstance());
    }

    public static synchronized Bitmap fetchBitmapByUrl(Context appContext, String url) {
        AppLogUtils.i("FM fetchBitmapByUrl[" + url + "]");
        Bitmap bitmap = null;
        if (url == null || url.length() == 0) {
            return null;
        }
        PacketCollector packetCollector = new PacketCollector();
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(url))
                .setProgressiveRenderingEnabled(true).build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline
                .fetchDecodedImage(imageRequest, appContext);
        Packet packet = new Packet(packetCollector, url);
        dataSource.subscribe(packet, CallerThreadExecutor.getInstance());
        Packet newPacket = packetCollector.nextResult();
        if (newPacket != null && url.equalsIgnoreCase(newPacket.getUrl())) {
            bitmap = newPacket.getBitmap();
        }
        if (bitmap != null && bitmap.isRecycled()) {
            bitmap = Bitmap.createBitmap(bitmap);
        }
        return bitmap;
    }

    public static Bitmap getBitmapFromCache(String url) {
        Uri uri = Uri.parse(url);
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        ImageRequest imageRequest = ImageRequest.fromUri(uri);
        DataSource<CloseableReference<CloseableImage>> dataSource =
                imagePipeline.fetchImageFromBitmapCache(imageRequest, CallerThreadExecutor.getInstance());
        try {
            CloseableReference<CloseableImage> imageReference = dataSource.getResult();
            if (imageReference != null) {
                try {
                    CloseableBitmap image = (CloseableBitmap) imageReference.get();
                    // do something with the image
                    Bitmap loadedImage = image.getUnderlyingBitmap();
                    if (loadedImage != null) {
                        return loadedImage;
                    } else {
                        return null;
                    }
                } finally {
                    CloseableReference.closeSafely(imageReference);
                }
            }
        } finally {
            dataSource.close();
        }
        return null;
    }

    public static void loadLocalImage(SimpleDraweeView img,String path){
        Uri uri = Uri.parse("file:///" + path);
        img.setImageURI(uri);
    }

    public static void loadLocalImage(SimpleDraweeView img,int res){
        Uri uri = Uri.parse("res:///" + res);
        img.setImageURI(uri);
    }

    public static void load(SimpleDraweeView view, String url) {
        if (view == null) {
            return;
        }
        Uri uri = Uri.parse(url);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true).build();


        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setTapToRetryEnabled(false)
//                .setLowResImageRequest(ImageRequest.fromUri(lowUrl))
                .setImageRequest(request)
                .setOldController(view.getController())
                .setAutoPlayAnimations(true).build();

        view.setController(controller);
    }
}
