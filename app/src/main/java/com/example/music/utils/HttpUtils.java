package com.example.music.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
	public static String requestStringByGet(String path) {
		// �ַ���ת����URL
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
