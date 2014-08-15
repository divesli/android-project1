package com.example.netreader;

import java.io.File;
import java.lang.ref.SoftReference;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;


public class ImageLoader {
	private ImageView _imageView = null;
	private boolean _running = false;
	private String  _url = null;
	private MemoryCache _mCache = null;
	private String _imgId = null;
	
	public ImageLoader(ImageView imageView) {
		_imageView = imageView;
	}
	public void load(String url) {
		if (url.length() > 0) {
			if (_mCache != null ) {
				_mCache.clear();
			} else {
				_mCache = new MemoryCache();
			}
			_running = true;
			_url = url;
			_imgId = String.valueOf(url.hashCode());
			new Thread(imageLoaderRunnable).start();
		}
	}
	
	public Bitmap getBitmap(String url) {
		if (_mCache == null) {
			return null;
		}
		Bitmap bitmap = _mCache.getBitmap(String.valueOf(url.hashCode()));
		return bitmap;
	}
	
	private Handler handler = new Handler() {
		
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				Bitmap bitmap = (Bitmap) msg.obj;
				_running = false;
				if (bitmap != null) {
					_imageView.setImageBitmap(bitmap);
				}
			}
		}
	};
	
	private Runnable imageLoaderRunnable = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message msg = new Message();
	        msg.what = 1;
			if (_running) {
				try {
					Bitmap bitmap = _mCache.getBitmap(_imgId);
					if (bitmap == null) {
						NetWorkReader netReader = new NetWorkReader();
						bitmap = netReader.getBitmapContent(_url);
						_mCache.putBitmap(_imgId, bitmap);
					}
					msg.obj = bitmap;
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			handler.sendMessage(msg);
		}
	};
	
	public class MemoryCache {
		
		private Map<String, SoftReference<Bitmap>> _cache = 
				Collections.synchronizedMap(new HashMap<String, SoftReference<Bitmap>>());
		
		public Bitmap getBitmap(String id) {
			if (!_cache.containsKey(id)) {
				return null;
			}
			SoftReference<Bitmap> reference = _cache.get(id);
			return reference.get();
		}
		
		public void putBitmap(String id, Bitmap bitmap) {
			clear();
			_cache.put(id, new SoftReference<Bitmap>(bitmap));
		}
		
		public void clear() {
			_cache.clear();
		}
	}
	
	public class FileCache {
		private File _cache = null;
		public FileCache() {
			_cache = new File(Environment.getExternalStorageDirectory(),"_joke_image_cache");
			if (!_cache.exists()) {
				_cache.mkdirs();
			}
		}
		public Bitmap getBitmap() {
			return null;
		}
	}
}


