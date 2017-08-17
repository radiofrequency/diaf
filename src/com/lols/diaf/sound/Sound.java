package com.lols.diaf.sound;

/*import java.applet.Applet;
import java.applet.AudioClip;
*/

import com.lols.diaf.GameCanvas;
import com.lols.diaf.Logger;
import com.lols.diaf.R;

import android.app.Activity;
import android.content.Context;
import android.media.*;
import android.media.MediaPlayer.OnCompletionListener;
import android.provider.MediaStore.Audio;
public class Sound {
	public static final Sound playerHurt = new Sound(R.raw.playerhurt);
	public static final Sound playerDeath = new Sound(R.raw.death);
	public static final Sound monsterHurt = new Sound(R.raw.monsterhurt);
	public static final Sound test = new Sound(R.raw.test);
	public static final Sound pickup = new Sound(R.raw.pickup);
	public static final Sound bossdeath = new Sound(R.raw.bossdeath);
	public static final Sound craft = new Sound(R.raw.craft);

	private Audio clip;
	Activity mContext;
	int soundid;
	private MediaPlayer mp=null;
	private Sound(int id) {
		//mContext = c;
	//	if (GameCanvas.mContext)
		soundid = id;
		
	//	if (GameCanvas.mContext.sound)
	//	{
		
		  mp = MediaPlayer.create(GameCanvas.mContext, soundid);
	//	}  
			//mp = MediaPlayer.create(c, R.raw.playerhurt);
		
		//mp = new MediaPlayer();
		//mp.setDataSource(R.raw.)
	}
	///	try {
	//		clip = Applet.newAudioClip(Sound.class.getResource(name));
	//	} catch (Throwable e) {
	//		e.printStackTrace();
	//	}
//	}

	public void play() {
		try {
		if (GameCanvas.mContext.sound)
		{
			mp.start();
		}
		} catch (Exception ex)
		{
			
		}
		/*  mp.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					Logger.v("mine", "complete");
					mp.release();
					
				}
			});*/
		  
		/*
	try {	
		  new Thread(){
              public void run(){
            	  
            	 // Context m = getApplicationContext();
            	  Logger.v("mine", "playing sound");
            	 
            	  if (!mp.isPlaying())
            	  {
            		  mp = MediaPlayer.create(GameCanvas.mContext, soundid);
            			
            	  mp.start();
            	  mp.setOnCompletionListener(new OnCompletionListener() {
					
					@Override
					public void onCompletion(MediaPlayer mp) {
						// TODO Auto-generated method stub
						Logger.v("mine", "complete");
						mp.release();
						
					}
				});
            	  }
            	// mp.set
              }
            	//  mp = MediaPlayer.create(GameCanvas.this, R.raw.mysound);   
            	 // mp.start();
           //   }
            }.start();
       //}
	} catch (Throwable e)
	{
		
		//try {
			/*new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
		} catch (Throwable e) {
			e.printStackTrace();*/
		////}
	
	}
}
