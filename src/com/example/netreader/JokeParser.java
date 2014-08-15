package com.example.netreader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.example.joke.Joke;


public class JokeParser {
	//private String reg = "<h1 id=\"joke-title\">(.*?)</h1>.*<div id=\"joke-content\">.*<p>(.*?)</p>";
	/*
	 *  <a href="http://www.xxhh.com/160964.html"> 菇凉，这子还真是原生态 </a> </h1> <div class=cont> <p id="joke-pic"> 
	 *  <a href="http://www.xxhh.com/160964.html"> <img src="http://p0.qhimg.com/t013ef3f841a247e8fb.jpg" alt="菇凉，你这鞋子还真是原生态"> 
	 *  </a> </p> <div id="joke-content">   <p></p>  </div> </div> 
	 */
	private String reg = "<h1 id=\"joke-title\">(.*)<p class=source>";
	private Joke joke = null;
	public Joke getJokeOcject(String content) {
		String jString = getJokeHtmlContent(content);
		if (jString.length() <= 0) {
			return joke;
		}
		String title   = getTitle(jString);
		String imsrc   = getImageSrc(jString);
		String contetxt = getContent(jString);
		joke = new Joke();
		joke.setTitle(title);
		joke.setContent(contetxt);
		joke.setImage(imsrc);
		return joke;
	}
	private String getTitle(String str) {
		Pattern pattern = Pattern.compile("(.*?)</h1>"); 
		Matcher matcher = pattern.matcher(str);
		String jString = "";
		if (matcher.find()) {
			jString = matcher.group(1);
		}
		if (jString.indexOf("href") == -1) {
			return jString;
		}
		Pattern pattern1 = Pattern.compile(">(.*?)</a>"); 
		Matcher matcher1 = pattern1.matcher(jString);
		if (matcher1.find()) {
			String jString1 = matcher1.group(1);
			return jString1;
		}
		return jString;
	}
	private String getImageSrc(String str) {
		Pattern pattern = Pattern.compile("<img src=\"([^\"]*)"); 
		Matcher matcher = pattern.matcher(str);
		String jString = "";
		if (matcher.find()) {
			jString = matcher.group(1);
		}
		return jString;
	}
	private String getContent(String str) {
		Pattern pattern = Pattern.compile("<div id=\"joke-content\">.*<p>(.*?)</p>"); 
		Matcher matcher = pattern.matcher(str);
		String jString = "";
		if (matcher.find()) {
			jString = matcher.group(1);
		}
		return jString;
	}
	private String getJokeHtmlContent(String content) {
		if (content.length() <= 0) {
			return null;
		}
		Pattern pattern = Pattern.compile(reg); 
		Matcher matcher = pattern.matcher(content);
		String jString = "";
		if (matcher.find()) {
			jString = matcher.group(1);
		}
		return jString;
	}
	
}
