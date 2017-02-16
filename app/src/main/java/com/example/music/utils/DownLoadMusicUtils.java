package com.example.music.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownLoadMusicUtils {

	public static void downLoadMusicByGet(String path, String songName) {
		URL url;
		try {
			url = new URL(path);
			LogUtils.LogI("≤‚ ‘Õ¯¬Á¿≠1", path + "");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(8000);
			connection.setRequestMethod("GET");
			if (connection.getResponseCode() == 200) {
				InputStream inputStream = connection.getInputStream();
				byte[] buffer = new byte[1024];
				int lenth = 0;
				File file = new File("/sdcard/", songName + ".mp3");
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				while ((lenth = (inputStream.read(buffer))) != -1) {
					fileOutputStream.write(buffer, 0, lenth);
				}
				fileOutputStream.close();
				inputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
