package com.example.music.fragment;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import com.example.music.MainActivity;
import com.example.music.R;
import com.example.music.adapter.MyFindMusicLvAdapter;
import com.example.music.javabean.FindSong;
import com.example.music.utils.FormatUtils;
import com.example.music.utils.HttpUtils;
import com.example.music.utils.NetPathUtils;
import com.example.music.utils.PaseJsonUtils;

import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class FindMusicFragment extends BaseFragment {
	View findView;
	EditText findEt;
	Button findBtn;
	ListView findLv;
	String findStr;
	List<String> strList;
	MyFindMusicLvAdapter adapter;
	FindMusicDetailFragment findDetailFragment;

	@Override
	public View initView() {
		findView = View.inflate(getActivity(), R.layout.frag_find_music, null);
		findEt = (EditText) findView.findViewById(R.id.frag_find_music_find_et);
		findBtn = (Button) findView.findViewById(R.id.frag_find_music_find_btn);
		findLv = (ListView) findView.findViewById(R.id.frag_find_music_lv);
		findBtn.setOnClickListener(btnListener);
		findLv.setOnItemClickListener(lvListener);
		findEt.addTextChangedListener(watcher);
		setLvData();
		return findView;
	}

	/**
	 * 按钮点击事件
	 */
	OnClickListener btnListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			findStr = findEt.getText().toString();
			new FindAsync().execute(findStr);
			findDetailFragment = new FindMusicDetailFragment(findStr);
			((MainActivity) getActivity()).swichFragment(findDetailFragment);
			findEt.setText("");
		}
	};

	/**
	 * LvItem点击事件
	 */
	OnItemClickListener lvListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			findEt.setText("");
			findDetailFragment = new FindMusicDetailFragment(strList.get(position));
			((MainActivity) getActivity()).swichFragment(findDetailFragment);

		}
	};

	/**
	 * 文本改变事件
	 */
	TextWatcher watcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			new FindAsync().execute(s.toString());
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void afterTextChanged(Editable s) {

		}
	};

	/**
	 * 网络请求
	 * 
	 * @param findStr
	 */
	private void getNetSongIdDataByMine(String findStr) {
		String result = HttpUtils.requestStringByGet(NetPathUtils.MUSIC_FIND_PATH + findStr);
		strList = PaseJsonUtils.parseSongJson(result);

	}

	private void setLvData() {
		strList = new ArrayList<String>();
		adapter = new MyFindMusicLvAdapter(getActivity(), strList);
		findLv.setAdapter(adapter);
	}

	class FindAsync extends AsyncTask<String, String, List<String>> {

		@Override
		protected List<String> doInBackground(String... params) {
			getNetSongIdDataByMine(params[0]);
			return strList;
		}

		@Override
		protected void onPostExecute(List<String> result) {
			super.onPostExecute(result);
			adapter.setList(result);
		}
	}

	@Override
	public void setData() {

	}

}
