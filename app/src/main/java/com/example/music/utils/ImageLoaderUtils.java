package com.example.music.utils;

import com.example.music.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * 设置图片加载规则 设置是否使用缓存
 * 
 * @author user
 *
 */
public class ImageLoaderUtils {
	public static void getImageByLoader(final String url, ImageView iv) {
		DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.bg_nophoto)
				.showImageForEmptyUri(R.drawable.bg_nophoto)
				// 错误则显示
				.showImageOnFail(R.drawable.bg_nophoto)
				// 设置下载的图片是否显示在内存中
				.cacheInMemory(true)
				// 是否显示在SD中
				.cacheOnDisk(true)
				// 图片适配
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
				// 图片质量
				.bitmapConfig(Bitmap.Config.RGB_565)
				// 渐入时间
				// .displayer(new FadeInBitmapDisplayer(100))
				// 圆角图片
				// .displayer(new RoundedBitmapDisplayer(20))
				.build();

		ImageLoader.getInstance().displayImage(url, iv, options, new ImageLoadingListener() {

			@Override
			public void onLoadingStarted(String arg0, View arg1) {
			}

			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
			}

			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
			}

			@Override
			public void onLoadingCancelled(String arg0, View arg1) {

			}
		}, new ImageLoadingProgressListener() {

			@Override
			public void onProgressUpdate(String arg0, View arg1, int arg2, int arg3) {
			}
		});
	}
}
