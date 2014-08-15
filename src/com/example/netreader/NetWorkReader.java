package com.example.netreader;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;



public class NetWorkReader {
	HttpResponse httpResponse = null;
	
	public String getNetContent(String url) {
		String content = "";
		if (url.length() <= 0) {
			return content;
		}
		
		try {
			HttpGet httpGet = new HttpGet(url);
			httpResponse = new DefaultHttpClient().execute(httpGet);
			
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				content = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}
	
	public Bitmap getBitmapContent(String url) {
		if (url.length() <= 0) {
			return null;
		}
		try {
			HttpGet httpGet = new HttpGet(url);
			httpResponse = new DefaultHttpClient().execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				 return null;
			}
			HttpEntity entity = httpResponse.getEntity();
			if (entity == null) {
				return null;
			}
			InputStream inputStream = entity.getContent();
			Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// To-Do
		}
		return null;
	}
}
