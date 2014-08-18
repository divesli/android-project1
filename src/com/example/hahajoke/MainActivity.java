package com.example.hahajoke;


import com.example.netreader.JokeLoader;

import android.support.v7.app.ActionBarActivity;

import android.os.Bundle;


import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private JokeLoader _jLoader = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		//GifView gifView = (GifView) findViewById(R.id.imageview);
		//gifView.setGifImage(R.drawable.open_image);
		//gifView.setGifImageType(GifImageType.SYNC_DECODER);
		//gifView.showAnimation();
		_jLoader = new JokeLoader(this,JokeLoader.INIT);
		_jLoader.start();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			Toast.makeText(this, "稍安勿躁,请耐心等待", Toast.LENGTH_LONG).show();
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			Toast.makeText(this, "关键是你的网络太不给力", Toast.LENGTH_LONG).show();
		}
		return super.onTouchEvent(event);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		/*if (_retest_count < 3) {
			_retest_count += 1;
			_jLoader.start();
		}*/
	}
	
	@Override
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		Toast.makeText(this, "Do long press me!", Toast.LENGTH_LONG).show();
		return super.onKeyLongPress(keyCode, event);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
