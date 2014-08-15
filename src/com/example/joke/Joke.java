package com.example.joke;

public class Joke {
	private String _jid = null;
	private String _title = null;
	private String _content = null;
	private String _imageUrl = null;
	private boolean _valid = true;
	
	public String getJid() {
		return _jid;
	}
	
	public String getTitle() {
		return _title;
	}
	public String getContent() {
		return _content;
	}
	public String getImage() {
		return _imageUrl;
	}
	public boolean vaild() {
		return _valid;
	}
	public void setJid(String jid) {
		_jid = jid;
	}
	public void setTitle(String title) {
		if (title.length() <= 0) {
			_valid = false;
		}
		_title = title;
	}
	public void setContent(String content) {
		_content = content;
	}
	public void setImage(String url) {
		_imageUrl = url;
	}
}
