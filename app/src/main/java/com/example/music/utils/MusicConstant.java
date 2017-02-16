package com.example.music.utils;

import java.util.ArrayList;
import java.util.List;

import com.example.music.javabean.Song;

public class MusicConstant {
	/**
	 * ����ģʽ
	 */
	public static final int MEDIA_PLAY = 0;
	/**
	 * ��ͣģʽ
	 */
	public static final int MEDIA_PAUSE = 1;
	public static int MODE;
	/**
	 * ��������
	 */
	public static final int MEDIA_MODLE_SINGLE = 3;
	/**
	 * ѭ������
	 */
	public static final int MEDIA_MODLE_LOOP = 4;
	/**
	 * �������
	 */
	public static final int MEDIA_MODLE_RANDOM = 5;
	/**
	 * �϶�ģʽ
	 */
	public static final int MEDIA_SEEK = 6;
	/**
	 * ��һ��
	 */
	public static final int MEDIA_NEXT = 7;
	/**
	 * ��һ��
	 */
	public static final int MEDIA_LAST = 8;
	/**
	 * ȫ�ֵ�һ�������б�
	 */
	public static List<Song> PLAY_LIST = new ArrayList<Song>();
	/**
	 * ȫ�ֵ���Ҫ����ĸ����б�
	 */
	public static List<Song> PLAY_LIST_X = new ArrayList<Song>();
	/**
	 * ��ǰ���ŵĵڼ��׸�
	 */
	public static int PLAY_ITEM = -1;
	/**
	 * SeekBar�ĵ�ǰ����
	 */
	public static int MEDIA_PROGRASS = 0;
	/**
	 * �����ܳ���
	 */
	public static int MEDIA_MAX = 0;
	/**
	 * �Ƿ��ڲ���
	 */
	public static boolean IS_PLAYING = false;
	/**
	 * �Ƿ�������ȡ
	 */
	public static boolean IS_FROM_NET = false;
	/**
	 * ����ͼƬ
	 */
	public static String SINGER_RUL_PIC_PATH;
}
