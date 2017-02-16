package com.example.music.utils;

import java.util.ArrayList;
import java.util.List;

import com.example.music.javabean.Song;

public class MusicConstant {
	/**
	 * 播放模式
	 */
	public static final int MEDIA_PLAY = 0;
	/**
	 * 暂停模式
	 */
	public static final int MEDIA_PAUSE = 1;
	public static int MODE;
	/**
	 * 单曲播放
	 */
	public static final int MEDIA_MODLE_SINGLE = 3;
	/**
	 * 循环播放
	 */
	public static final int MEDIA_MODLE_LOOP = 4;
	/**
	 * 随机播放
	 */
	public static final int MEDIA_MODLE_RANDOM = 5;
	/**
	 * 拖动模式
	 */
	public static final int MEDIA_SEEK = 6;
	/**
	 * 下一曲
	 */
	public static final int MEDIA_NEXT = 7;
	/**
	 * 上一曲
	 */
	public static final int MEDIA_LAST = 8;
	/**
	 * 全局的一个歌曲列表
	 */
	public static List<Song> PLAY_LIST = new ArrayList<Song>();
	/**
	 * 全局的主要不变的歌曲列表
	 */
	public static List<Song> PLAY_LIST_X = new ArrayList<Song>();
	/**
	 * 当前播放的第几首歌
	 */
	public static int PLAY_ITEM = -1;
	/**
	 * SeekBar的当前长度
	 */
	public static int MEDIA_PROGRASS = 0;
	/**
	 * 歌曲总长度
	 */
	public static int MEDIA_MAX = 0;
	/**
	 * 是否在播放
	 */
	public static boolean IS_PLAYING = false;
	/**
	 * 是否从网络获取
	 */
	public static boolean IS_FROM_NET = false;
	/**
	 * 歌手图片
	 */
	public static String SINGER_RUL_PIC_PATH;
}
