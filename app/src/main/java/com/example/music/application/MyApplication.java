package com.example.music.application;

import java.io.File;

import org.xutils.x;

import com.example.music.BuildConfig;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		x.Ext.init(this);
		initImageLoder(getApplicationContext());
		x.Ext.setDebug(BuildConfig.DEBUG); // 开启debug会影响性能
	}

	private void initImageLoder(Context context) {
		// 设置缓存图片的路径
		File cacheDir = StorageUtils.getOwnCacheDirectory(context, "imageloader/Catch");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.diskCacheSize(50 * 1024 * 1024).diskCache(new UnlimitedDiscCache(cacheDir))
				.memoryCache(new WeakMemoryCache()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
		// 单例模式就是不能New这个对象，构造方法私有，找不到这个对象，见到getInstance就是单例模式
		ImageLoader.getInstance().init(config);
	}
}
