package com.lols.diaf.gfx;

//import java.awt.image.BufferedImage;
import android.graphics.Bitmap;

public class SpriteSheet {
	public int width, height;
	public int[] pixels;

	public SpriteSheet(Bitmap image) {
		width = image.getWidth();
		height = image.getHeight();
		
		 pixels = new int[height * width];

		image.getPixels(pixels,0,width,0,0,width,height);
		
		//pixels = image.getRGB(0, 0, width, height, null, 0, width);
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = (pixels[i] & 0xff) / 64;
		}
	}
}