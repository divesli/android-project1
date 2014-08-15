package com.example.tool;

import android.graphics.Bitmap;

public class GifFrame {
	public GifFrame(Bitmap im, int del) {
        image = im;
        delay = del;
    }
 
    /** Õº∆¨ */
    public Bitmap image;
    /** —” ± */
    public int delay;
    /** œ¬“ª÷° */
    public GifFrame nextFrame = null;
}
