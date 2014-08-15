package com.example.netreader;


import com.example.cache.JokeDB;
import com.example.cache.JokeListQueue;
import com.example.hahajoke.MainActivity;
import com.example.joke.Joke;
import com.example.tool.BaseTool;
import com.example.ui.JokeList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class JokeLoader{
	public static final int INIT = 0;
	public static final int REFRESH = 1;
	
	private static final String _PREFS_NAME = "JOKE_POSITION";
	private static final String _KEY_NAME = "joke_current_position";
	
	private Context _context = null;
	private boolean _running = false;
	
	private JokeListQueue _jQueue = null;
	private String _base_url = "http://xiaohua.hao.360.cn/tj?id=";
	private int _seed_id = 0;
	private int _type = 0;
	
	public JokeLoader(Context context, int type) {
		_context = context;
		_type = type;
		_seed_id = getPositionId();
	}
	
	public void start() {
		_running = true;
		Thread thread = new Thread(runnable);
		thread.start();
	}
	
	private void stop() {
		savePositionId(_seed_id);
		if (_jQueue == null) {
			_jQueue = JokeListQueue.getInstance();
		}
		_jQueue.sort();
		_running = false;
		//thread.stop();
	}
		
	private void startJokeActivity() {
		Intent intent = new Intent(_context,JokeList.class);
		((MainActivity)_context).finish();
		_context.startActivity(intent);	
	}
	
	private void refresh() {
		((JokeList)_context).updateAdapter();
	}
	
	private int getPositionId() {
		SharedPreferences share = _context.getSharedPreferences(_PREFS_NAME, Activity.MODE_PRIVATE);
		return share.getInt(_KEY_NAME, 1);
	}
	
	private void savePositionId(int id) {
		SharedPreferences share = _context.getSharedPreferences(_PREFS_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = share.edit();
		editor.putInt(_KEY_NAME, id);
		editor.commit();
	}
	
	private Handler handler = new Handler() {
		@Override
	    public void handleMessage(Message msg) {
	        super.handleMessage(msg);
	        if (msg.what == 1) {
	        	stop();
	        	if (_type == INIT) {
	        		startJokeActivity();
	        	}
	        	if (_type == REFRESH) {
	        		refresh();
	        	}
	        }
	        if (msg.what == 2) {
	        	BaseTool.openWifi(_context);
	        }
	    }
	};
	
	private Runnable runnable = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message msg = new Message();
			while(_running) {
				msg.what = 1;
				JokeDB jdb = new JokeDB(_context);
				if (_type == INIT && jdb.getCount() > 0) {
					jdb.initJQueue();
					break;
				}
				if (!BaseTool.isNetWordViald(_context)) {
					msg.what = 2;
					break;
				}
				NetWorkReader netReader = new NetWorkReader();
				_jQueue = JokeListQueue.getInstance();
				JokeParser jParser = new JokeParser();
				
				int max = _seed_id + 10;
				
				for (; _seed_id < max; _seed_id++) {
					String id = String.valueOf(_seed_id);
					String htmlContent = netReader.getNetContent(_base_url + id);
					Joke joke = jParser.getJokeOcject(htmlContent);
					if (joke == null || joke.vaild() == false) {
						continue;
					}
					joke.setJid(id);
					Log.i("Loader","title=" + joke.getTitle());
					_jQueue.add(joke);
					jdb.insert(joke);
				}
				jdb.close();
				break;
			}
			handler.sendMessage(msg);
		}
	};
}
