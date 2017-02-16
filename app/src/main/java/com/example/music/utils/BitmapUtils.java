package com.example.music.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class BitmapUtils {
	/**
	 * 利用矩阵转换图片
	 * 
	 * @param context:上下文
	 * @param id:资源ID
	 * @param sx:矩阵的横向比例
	 * @param sy:矩阵的纵向比例
	 * @return bitmap
	 */
	public static Bitmap BitmapScale(Context context, int id, float sx, float sy) {
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
		Matrix matrix = new Matrix();
		matrix.postScale(sx, sy);
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return bitmap;
		
	}

	/**
	 * 网络请求图片，唉，什么年代了，需要自己请求
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap requestBitmapByGet(String path, Context context) {
		try {
			URL url = new URL(path);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			// 请求方式
			connection.setRequestMethod("GET");
			// 超时时间
			connection.setConnectTimeout(8000);
			// 请求码200
			if (connection.getResponseCode() == 200) {
				// 里面是IO,有一个输入，一个输出，一个数组，一个字符串，一个变量
				InputStream inputStream = connection.getInputStream();
				Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
				inputStream.close();
				BitmapScale(context, 0, 0, 0);
				return bitmap;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
