package com.example.ui;

import com.example.adapter.JokeAdapter;
import com.example.hahajoke.R;
import com.example.netreader.JokeLoader;
import com.example.ui.PullDownListView.OnRefreshListener;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class JokeList extends ActionBarActivity implements OnItemClickListener,OnRefreshListener{
	
	private JokeAdapter _adapter = null;
	private JokeLoader jokeLoader = null;
	private PullDownListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_joke_list);
		listView = (PullDownListView) findViewById(R.id.joke_listView);
		_adapter = new JokeAdapter(this);
		listView.setAdapter(_adapter);
		listView.setonRefreshListener(this);
		
		listView.setOnItemClickListener(this);
	}
	
	public void onRefresh() { 
		Log.i("JokeList","onRefresh!");
		jokeLoader = new JokeLoader(this, JokeLoader.REFRESH);
		jokeLoader.start();
	}
	
	public void updateAdapter() {
		Log.i("Joke_list","update updateAdapter");
		_adapter.notifyDataSetChanged();
		listView.onRefreshComplete();
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		if (position >= 0) {
			Intent intent = new Intent(this,JokeView.class);
			intent.putExtra("position", position -1);
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.joke_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			new AlertDialog.Builder(this).setTitle("点毛点,还没做呢").setPositiveButton("你是不是傻货", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}
			}).show();
		}
		if (id == R.id.home) {
			Toast.makeText(this, "sfafa", Toast.LENGTH_LONG).show();
			listView.setSelection(0);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
