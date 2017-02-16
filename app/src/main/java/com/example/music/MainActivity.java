package com.example.music;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.example.music.adapter.MyFragPagerAdapter;
import com.example.music.fragment.BaseFragment;
import com.example.music.fragment.FindMusicFragment;
import com.example.music.fragment.LocalMusicFragment;
import com.example.music.fragment.NetMusicFragment;
import com.example.music.service.MediaPlayerService;
import com.example.music.utils.BroadCastConstant;
import com.example.music.utils.MusicConstant;
import com.example.music.utils.MusicUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener {
	// 控件
	ViewPager vp;
	RadioGroup rg;
	RadioButton netMusRb, locMusRb, findMusRb;
	Button playBtn, nextBtn;
	LinearLayout bottomLl;
	ImageView bottomIv;
	TextView songName, singerName;
	// fragment的东东
	List<BaseFragment> list;
	NetMusicFragment netMusicFragment;
	LocalMusicFragment localMusicFragment;
	FindMusicFragment findMusicFragment;
	MyFragPagerAdapter pagerFragAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		MusicUtils.getSingerData(this);
		// 启动服务
		startService(new Intent(this, MediaPlayerService.class));
		// 接收广播的信号源以及注册
		IntentFilter filter = new IntentFilter();
		filter.addAction(BroadCastConstant.MUSIC_UPDATA_BROADCAST);
		registerReceiver(new MainBroadCastReceiver(), filter);
		initView();
		setVpData();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	/**
	 * 当前Activity可视的时候执行这个方法，重新赋值
	 */
	@Override
	protected void onResume() {
		super.onResume();
		if (MusicConstant.IS_PLAYING) {
			playBtn.setBackgroundResource(R.drawable.icon_pause_normal);
			songName.setText(MusicConstant.PLAY_LIST.get(MusicConstant.PLAY_ITEM).songname);
			singerName.setText(MusicConstant.PLAY_LIST.get(MusicConstant.PLAY_ITEM).singer);
		} else if (MusicConstant.PLAY_LIST.size() > 0 && MusicConstant.PLAY_ITEM > 0) {
			playBtn.setBackgroundResource(R.drawable.icon_play_normal);
			songName.setText(MusicConstant.PLAY_LIST.get(MusicConstant.PLAY_ITEM).songname);
			singerName.setText(MusicConstant.PLAY_LIST.get(MusicConstant.PLAY_ITEM).singer);
		}
	}

	/**
	 * 实例化各种控件
	 */
	private void initView() {
		vp = (ViewPager) findViewById(R.id.main_vp);
		// 顶栏
		rg = (RadioGroup) findViewById(R.id.main_rg);
		rg.setOnCheckedChangeListener(listener);
		netMusRb = (RadioButton) findViewById(R.id.main_netmusic_rb);
		netMusRb.setChecked(true);
		locMusRb = (RadioButton) findViewById(R.id.main_localmusic_rb);
		findMusRb = (RadioButton) findViewById(R.id.main_findmusic_rb);

		// 底栏
		playBtn = (Button) findViewById(R.id.main_play_btn);
		nextBtn = (Button) findViewById(R.id.main_next_btn);
		bottomLl = (LinearLayout) findViewById(R.id.main_bottom_ll);
		bottomLl.setOnClickListener(this);
		playBtn.setOnClickListener(this);
		nextBtn.setOnClickListener(this);
		bottomIv = (ImageView) findViewById(R.id.main_music_icon_iv);
		songName = (TextView) findViewById(R.id.main_music_title_tv);
		singerName = (TextView) findViewById(R.id.main_music_singer_tv);
	}

	// 底部三个按钮的监听事件
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		intent.setAction(BroadCastConstant.MUSIC_SERVICE_BROADCAST);
		switch (v.getId()) {
		case R.id.main_bottom_ll:
			startActivity(new Intent(MainActivity.this, MusicActivity.class));
			overridePendingTransition(R.anim.music_in, R.anim.main_out);
			break;
		case R.id.main_play_btn:
			if (MusicConstant.IS_PLAYING && MusicConstant.PLAY_ITEM != -1) {
				intent.putExtra("type", MusicConstant.MEDIA_PAUSE);
				playBtn.setBackgroundResource(R.drawable.icon_play_normal);
			} else {
				if (MusicConstant.PLAY_LIST.size() > 0) {
					intent.putExtra("type", MusicConstant.MEDIA_PLAY);
					intent.putExtra("musicItem", MusicConstant.PLAY_ITEM);
					intent.putExtra("isPause", true);
					playBtn.setBackgroundResource(R.drawable.icon_pause_normal);
				} else {
					Toast.makeText(this, "还没选择歌曲呢", 0).show();
				}
			}
			break;
		case R.id.main_next_btn:
			intent.putExtra("type", MusicConstant.MEDIA_NEXT);
			break;
		}
		sendBroadcast(intent);
	}

	// VP的数据设置，设置三个Fragment
	private void setVpData() {
		list = new ArrayList<BaseFragment>();
		netMusicFragment = new NetMusicFragment();
		localMusicFragment = new LocalMusicFragment();
		findMusicFragment = new FindMusicFragment();
		list.add(netMusicFragment);
		list.add(localMusicFragment);
		list.add(findMusicFragment);
		pagerFragAdapter = new MyFragPagerAdapter(getSupportFragmentManager(), list);
		vp.setAdapter(pagerFragAdapter);
		vp.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				if (arg0 == 0) {
					netMusRb.setChecked(true);
				} else if (arg0 == 1) {
					locMusRb.setChecked(true);
				} else if (arg0 == 2) {
					findMusRb.setChecked(true);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	// RaidoGroup的监听事件
	OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.main_netmusic_rb:
				vp.setCurrentItem(0);
				netMusRb.setTextSize(20);
				locMusRb.setTextSize(16);
				findMusRb.setTextSize(16);
				break;
			case R.id.main_localmusic_rb:
				vp.setCurrentItem(1);
				locMusRb.setTextSize(20);
				netMusRb.setTextSize(16);
				findMusRb.setTextSize(16);
				break;
			case R.id.main_findmusic_rb:
				vp.setCurrentItem(2);
				findMusRb.setTextSize(20);
				netMusRb.setTextSize(16);
				locMusRb.setTextSize(16);
				break;
			}
		}
	};

	/**
	 * 收到广播则改变底栏的文字
	 * 
	 * @param intent
	 */
	public void changeBottom(Intent intent) {

		songName.setText(intent.getStringExtra("songName"));
		singerName.setText(intent.getStringExtra("singer"));
		if (MusicConstant.IS_PLAYING) {
			playBtn.setBackgroundResource(R.drawable.icon_pause_normal);
		} else {
			playBtn.setBackgroundResource(R.drawable.icon_play_normal);
		}
	}

	/**
	 * 广播内部类，收到广播后执行这里的方法
	 * 
	 * @author user
	 *
	 */
	public class MainBroadCastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			changeBottom(intent);
		}

	}

	public void swichFragment(BaseFragment fragment) {
		this.getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment).addToBackStack(null)
				.commit();
	}
}
