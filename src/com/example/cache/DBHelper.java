package com.example.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private Cursor _cursor = null;
	private static final String _TABLE = "joke_list";
	private static final String _CREATE_TAB = "CREATE TABLE " + _TABLE 
			+ "(_id integer primary key autoincrement,_jid varchar(10) not null,_title varchar(200) not null,_content varchar(2000),_imagesrc varchar(200))";
	private SQLiteDatabase _database = null;
	
	public DBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int arg0, int arg1) {
		// TODO
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		_database = db;
		_database.execSQL(_CREATE_TAB);
	}
	
	public void insert(ContentValues values) {
		SQLiteDatabase _db = getWritableDatabase();
		_db.insert(_TABLE, null, values);
		_db.close();
	}
	
	public void update(ContentValues values, int id) {
		SQLiteDatabase _db = getWritableDatabase();
		_db.update(_TABLE, values, "_id="+id, null);
		_db.close();
	}
	
	public void delete(int id) {
		SQLiteDatabase _db = getWritableDatabase();
		_db.delete(_TABLE, "_id=?", new String[]{String.valueOf(id)});
		_db.close();
	}
	
	public Cursor query() {
		SQLiteDatabase _db = getReadableDatabase();
		_cursor = _db.query(_TABLE, null, null, null, null, null, "_id desc", null);
		return _cursor;
	}
	
	public Cursor rawquery(String sql, String[] where) {
		SQLiteDatabase _db = getReadableDatabase();
		_cursor = _db.rawQuery(sql, where);
		return _cursor;
	}
	
	public void dropTable() {
		_database.execSQL("DROP TBALE " + _TABLE);
	}
	
	public void deleteTable() {
		SQLiteDatabase _db = getWritableDatabase();
		_db.execSQL("DELETE FROM " + _TABLE);
		_db.execSQL("VACUUM");
		_db.close();
	}
	
	@Override
	public void close() {
		if (_database != null) {
			_database.close();
			_database = null;
		}
		if (_cursor != null) {
			_cursor.close();
			_cursor = null;
		}
	}
	
}
