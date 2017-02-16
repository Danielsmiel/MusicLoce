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
	 * ���þ���ת��ͼƬ
	 * 
	 * @param context:������
	 * @param id:��ԴID
	 * @param sx:����ĺ������
	 * @param sy:������������
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
	 * ��������ͼƬ������ʲô����ˣ���Ҫ�Լ�����
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap requestBitmapByGet(String path, Context context) {
		try {
			URL url = new URL(path);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			// ����ʽ
			connection.setRequestMethod("GET");
			// ��ʱʱ��
			connection.setConnectTimeout(8000);
			// ������200
			if (connection.getResponseCode() == 200) {
				// ������IO,��һ�����룬һ�������һ�����飬һ���ַ�����һ������
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
