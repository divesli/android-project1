package com.example.tool;

import android.graphics.Bitmap;

public class GifFrame {
	public GifFrame(Bitmap im, int del) {
        image = im;
        delay = del;
    }
 
    /** ͼƬ */
    public Bitmap image;
    /** ��ʱ */
    public int delay;
    /** ��һ֡ */
    public GifFrame nextFrame = null;
}
