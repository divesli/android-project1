package com.example.adapter;



import com.example.cache.JokeListQueue;
import com.example.hahajoke.R;
import com.example.joke.Joke;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class JokeAdapter extends BaseAdapter{
	private Context _context;
	private JokeListQueue _jQueue = null;
	
	public JokeAdapter(Context context) {
		_context = context;
		_jQueue = JokeListQueue.getInstance();
	}
	
	@Override
	public int getCount() {
		return _jQueue.getSize();
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public Object getItem(int position) {
		return _jQueue.get(position);
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(_context).inflate(R.layout.joke_list, null);
			viewHolder.idView = (TextView) view.findViewById(R.id.joke_id);
			viewHolder.titleView = (TextView) view.findViewById(R.id.joke_title);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		//Log.i("Adapter", "position=" + position + "");
		viewHolder.idView.setText(((Joke)_jQueue.get(position)).getJid()+".");
		viewHolder.idView.setTextSize(24.0f);
		viewHolder.idView.setTextColor(Color.RED);
		viewHolder.idView.setTypeface(null, Typeface.BOLD);
		viewHolder.titleView.setText(((Joke)_jQueue.get(position)).getTitle());
		viewHolder.titleView.setTextSize(20.0f);
		return view;
	}
	
	public class ViewHolder {
		public TextView idView;
		public TextView titleView;
	}
}
