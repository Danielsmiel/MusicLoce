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
		x.Ext.setDebug(BuildConfig.DEBUG); // ����debug��Ӱ������
	}

	private void initImageLoder(Context context) {
		// ���û���ͼƬ��·��
		File cacheDir = StorageUtils.getOwnCacheDirectory(context, "imageloader/Catch");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.diskCacheSize(50 * 1024 * 1024).diskCache(new UnlimitedDiscCache(cacheDir))
				.memoryCache(new WeakMemoryCache()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
		// ����ģʽ���ǲ���New������󣬹��췽��˽�У��Ҳ���������󣬼���getInstance���ǵ���ģʽ
		ImageLoader.getInstance().init(config);
	}
}
