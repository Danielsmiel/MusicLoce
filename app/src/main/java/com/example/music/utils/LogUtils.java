package com.example.music.utils;

import android.util.Log;

public class LogUtils {
	public final static boolean ISLOG = true;

	public static void LogI(String tag, String msg) {
		if (ISLOG) {
			if (msg != null) {
				Log.i(tag, "--------------" + msg + "--------------");
			} else {
				Log.i("´òÓ¡", "MSGÎª¿Õ");
			}
		}
	}

	public static void LogV(String tag, String msg) {
		Log.v(tag, "==============" + msg + "==============");
	}
}
