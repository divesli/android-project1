package com.example.cache;

import com.example.joke.Joke;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class JokeDB {
	private Cursor _cursor = null;
	private DBHelper _dbHelper = null;
	private String _dbname = "joke.db";
	public JokeDB(Context context) {
		_dbHelper = new DBHelper(context, _dbname, null, 1);
	}
	
	public int getCount() {
		if (_cursor == null) {
			_cursor = _dbHelper.query();
		}
		return _cursor.getCount();
	}
	
	public boolean insert(Joke joke) {
		if (joke == null || joke.vaild() == false) {
			return false;
		}
		ContentValues values = new ContentValues();
		values.put("_jid", joke.getJid());
		values.put("_title", joke.getTitle());
		values.put("_content", joke.getContent());
		values.put("_imagesrc", joke.getImage());
		_dbHelper.insert(values);
		return true;
	}
	public boolean initJQueue() {
		JokeListQueue jQueue = JokeListQueue.getInstance();
		if (jQueue.getSize() <= 0) {
			if (_cursor == null) {
				_cursor = _dbHelper.query();
			}
			int iJid = _cursor.getColumnIndex("_jid");
			int iTitle = _cursor.getColumnIndex("_title");
			int iImage = _cursor.getColumnIndex("_imagesrc");
			int iContent = _cursor.getColumnIndex("_content");
			for(_cursor.moveToFirst(); !(_cursor.isAfterLast()); _cursor.moveToNext()) {
				Joke joke = new Joke();
				joke.setJid(_cursor.getString(iJid));
				joke.setTitle(_cursor.getString(iTitle));
				joke.setImage(_cursor.getString(iImage));
				joke.setContent(_cursor.getString(iContent));
				jQueue.add(joke);
				if (jQueue.getSize() >= jQueue.getMax()) {
					break;
				}
			}
		}
		close();
		return true;
	}
	public boolean dropDatabase() {
		_dbHelper.dropTable();
		return true;
	}
	public Cursor select() {
		Cursor _cursor = _dbHelper.query();
		return _cursor;
	}
	public void close() {
		if (_cursor != null) {
			_cursor = null;
		}
		_dbHelper.close();
	}
	
}
