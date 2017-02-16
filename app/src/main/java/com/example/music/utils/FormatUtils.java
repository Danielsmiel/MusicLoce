package com.example.music.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * ��ʽ���������ݣ�����Str
 * 
 * @author Daniel
 *
 */
public class FormatUtils {
	/**
	 * ��ʽ������
	 * 
	 * @param time
	 * @return
	 */
	public static String formatTime(int time) {
		if (time / 1000 % 60 < 10) {
			return time / 1000 / 60 + ":0" + time / 1000 % 60;
		} else {
			return time / 1000 / 60 + ":" + time / 1000 % 60;
		}
	}

	public static float formatSize(int size) {

		return Math.round((size / 1024 / 1024) * 100) / 100;
	}

	/**
	 * ȥ���ո�URL����
	 * 
	 * @param str
	 * @return
	 */
	public static String formatStr(String str) {
		try {
			str = URLEncoder.encode(str.replaceAll(" ", ""), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
}
