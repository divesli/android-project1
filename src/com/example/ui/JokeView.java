package com.example.ui;


import com.example.cache.JokeListQueue;
import com.example.hahajoke.R;

import com.example.joke.Joke;
import com.example.netreader.ImageLoader;
import com.example.tool.BaseTool;


import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;


import android.support.v7.app.ActionBarActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class JokeView extends ActionBarActivity implements OnClickListener{

	private TextView _titleTextView = null;
	private TextView _contentTextView = null;
	private ImageView _imageView = null;
	private EditText _idEditView = null;
	private JokeListQueue _jQueue = null;
	private ImageLoader _imageLoader = null;
	private TextView _idTextView = null;
	private ImageView _bigImageView = null;
	private Joke _joke = null;
	private LinearLayout _big_layout = null;
	private LinearLayout _joke_layout = null;
	
	private boolean _is_big = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_joke_view);
		
		Intent intent = getIntent();
		int position = intent.getIntExtra("position", 0);

		_titleTextView = (TextView) findViewById(R.id.joke_view_title);
		_contentTextView = (TextView) findViewById(R.id.joke_view_content);
		_imageView = (ImageView) findViewById(R.id.joke_view_image);
		_idEditView = (EditText) findViewById(R.id.joke_edit_id);
		_idTextView = (TextView) findViewById(R.id.joke_view_id);
		_bigImageView = (ImageView) findViewById(R.id.joke_view_bigimg);
		_big_layout = (LinearLayout) findViewById(R.id.big_layout);
		_joke_layout = (LinearLayout) findViewById(R.id.joke_layout);
		_imageLoader = new ImageLoader(_imageView);
		
		setView(position);
		
		ImageButton btn_next = (ImageButton) findViewById(R.id.btn_next);
		btn_next.setOnClickListener(this);
		ImageButton btn_prev = (ImageButton) findViewById(R.id.btn_prev);
		btn_prev.setOnClickListener(this);
		
		ActionBar bar = getActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
		
		_imageView.setOnClickListener(imageListener);
		
		_bigImageView.setOnClickListener(bigImageListener);
				
	}
	OnClickListener imageListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String url = _joke.getImage();
			Bitmap bitmap = _imageLoader.getBitmap(url);
			if (bitmap != null) {
				//getActionBar().hide();
				_joke_layout.setVisibility(View.GONE);
				_big_layout.setBackgroundColor(Color.BLACK);
				_big_layout.setVisibility(View.VISIBLE);
				_bigImageView.setVisibility(View.VISIBLE);
				_bigImageView.setImageBitmap(bitmap);
				_bigImageView.setScaleType(ScaleType.FIT_XY);
				_is_big = true;
				// 遮盖状态栏
				//_bigImageView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
			}
		}
	};
	
	OnClickListener bigImageListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			hideBigImage();
			// 显示状态栏
			//_bigImageView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
			//_bigImageView.set
		}
	};
	
	public void hideBigImage() {
		_bigImageView.setVisibility(View.GONE);
		_big_layout.setVisibility(View.GONE);
		_joke_layout.setVisibility(View.VISIBLE);
		//getActionBar().show();
		_is_big = false;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (_is_big && event.getAction() == MotionEvent.ACTION_UP) {
			hideBigImage();
		}
		return super.onTouchEvent(event);
	}
	
	@Override 
	public void onClick(View view) {
		String id = _idEditView.getText().toString();
		int position = Integer.parseInt(id);
		if (view.getId() == R.id.btn_next) {
			position -= 1;
			if (position < 0) {
				Toast.makeText(this, "已经是最新的一个笑话了", Toast.LENGTH_LONG).show();
			} else {
				setView(position);
			}
		} 
		if (view.getId() == R.id.btn_prev){
			position += 1;
			if (position >= _jQueue.getSize()) {
				Toast.makeText(this, "已经是最后一个笑话了", Toast.LENGTH_LONG).show();
			} else {
				setView(position);
			}
		}
		
	}
	
	private void setView(int position) {
		_jQueue = JokeListQueue.getInstance();
		_joke = (Joke) _jQueue.get(position);
		
		_idEditView.setText(position+"");
		
		_idTextView.setText(_joke.getJid()+".");
		_idTextView.setTextSize(26.0f);
		_idTextView.setTextColor(Color.RED);
		
		_titleTextView.setText(_joke.getTitle());
		_titleTextView.setTextSize(26.0f);
		_titleTextView.setTextColor(Color.RED);
		_titleTextView.setTypeface(null,Typeface.BOLD);
		
		String content  = _joke.getContent();
		_contentTextView.setText(content.replace("#", "\n"));
		_contentTextView.setTextSize(18.0f);
		
		String imgsrc = _joke.getImage();
		if (imgsrc.length() > 0) {
			if (!BaseTool.isNetWordViald(this)) {
				Toast.makeText(this, "网络错误,图片下载失败", Toast.LENGTH_LONG).show();
			} else {
				_imageView.setVisibility(View.VISIBLE);
				_imageView.setImageResource(R.drawable.defimage);
				_imageLoader.load(imgsrc);
			}
		} else {
			_imageView.setVisibility(View.GONE);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.joke_list, menu);
		return true;
	}
}
