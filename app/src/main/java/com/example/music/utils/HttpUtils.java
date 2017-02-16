package com.example.music.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
	public static String requestStringByGet(String path) {
		// 字符串转换成URL
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
				ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = inputStream.read(buffer)) != -1) {
					arrayOutputStream.write(buffer, 0, len);
				}
				String result = new String(arrayOutputStream.toByteArray());
				inputStream.close();
				arrayOutputStream.close();
				return result;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
