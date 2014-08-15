package com.example.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import com.example.joke.Joke;

public class JokeListQueue {
	
	private List<Joke> _list = null;
	private int MAX_SIZE = 100;
	private double LEFT_RATIO = 0.9;
	
	private static JokeListQueue _instance = null;

	public static JokeListQueue getInstance() {
		if (_instance == null) {
			_instance = new JokeListQueue();
		}
		return _instance;
	}
	
	private JokeListQueue() {
		_list = new ArrayList<Joke>();
	}
	
	public int getMax() {
		return MAX_SIZE;
	}
	
	public boolean add(Joke joke) {
		if (getSize() >= MAX_SIZE) {
			rebuild();
		}
		_list.add(joke);
		return true;
	}
	
	public int getSize() {
		return _list.size();
	}
	
	public Object get(int position) {
		if (position > getSize()) {
			return false;
		}
		return _list.get(position);
	}
	
	public void rebuild() {
		sort();
		int total = getSize();
		int move_count = (int) (total * LEFT_RATIO);
		for (int i = total-1; i >= move_count; i--) {
			_list.remove(i);
		}
	}
	public void sort() {
		JokeComparator comparator = new JokeComparator();
		Collections.sort(_list,comparator);
	}
	
	public class JokeComparator implements Comparator<Joke> {
		@Override
		public int compare(Joke joke1, Joke joke2) {
			 int jid1 = Integer.parseInt(joke1.getJid());
	         int jid2 = Integer.parseInt(joke2.getJid());
	         if (jid1 > jid2) { 
                return -1;  
             } else if (jid1 == jid2) { 
                return 0;  
             } else if (jid1 < jid2) {  
                return 1;  
             }
             return 0;
		}
	}
}
