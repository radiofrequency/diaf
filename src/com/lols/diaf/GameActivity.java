package com.lols.diaf;


import android.accounts.OnAccountsUpdateListener;
import android.app.Activity;
import android.os.Handler;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;


//import com.adwhirl.AdWhirlLayout.AdWhirlInterface;
//import com.inmobi.androidsdk.IMSDKUtil;
import com.lols.diaf.R;

public class GameActivity extends Activity  
 //AdWhirlInterface 
{
	GameCanvas game=null;
	GameCanvas mycanvas = null;
	boolean hasfocus = false;
	Handler mHandler=null;
	public boolean sound;
	Activity mContext;
    //WakeLock wakeLock;
	Handler handler;
	public static final int SHOW_BUTTONS = 0;
	public static final int HIDE_BUTTONS = 1;
	public static final int SHOW_DPAD = 2;
	public static final int HIDE_DPAD = 3;

    /** Called when the activity is first created. */
	  public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        setContentView(R.layout.main);
	        Logger.v("mine", "onCreate");
	         game = (GameCanvas)findViewById(R.id.SurfaceView01);
		        game.setContext(this);
		        
	       // com.lols.diaf.Game = (Game) findViewById(R.id.SurfaceView01);
	        handler = new Handler() {
	              public void handleMessage(Message msg) {
	            	//  game.loadGame(1);
	                  // process incoming messages here
	            	  Logger.v("mine", String.format("handle message: %d", msg.what));
	            	  switch(msg.what)
	            	  {
	            	  	case HIDE_BUTTONS:
	            	  		hidebuttons();
	            	  	break;
	            	  	
	            		case SHOW_BUTTONS:
	            	  		showbuttons();
	            	  	break;
	            	  	
	            		case HIDE_DPAD:
	            	  		hidedpad();
	            	  	break;
	            	  		
	            		case SHOW_DPAD:
	            	  		showdpad();
	            	  	break;
	            	  }
	              }
	          };
	 
		        game.setHandler(handler);
		    SharedPreferences prefs = getPreferences(4);
	    	String prefix = String.format("game12-0");
	  		if (prefs.getBoolean(String.format(prefix+"setting-0"),true) == true)  //dpad
	  		{
  			game.mTouchDpad = true;
	  			showdpad();
	  		}
	  		else
	  		{
	  			game.mTouchDpad = false;
	  			hidedpad();
	  		}
	  			
	    	//String prefix = String.format("game12-1");
	  		if (prefs.getBoolean(String.format("game12-1"+"setting-1"),true) == true)  //buttons
	  		{
	  			showbuttons();
	  			
	  		}
	  		else
	  		{
	  			hidebuttons();
	  		}
	    
	    
	        getWindow().addFlags(524288);//WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
	        
	      
	       final Button bSound = (Button)findViewById(R.id.soundbutton);
	       
	       
	 //  	SharedPreferences prefs = this.getPreferences(4);
		sound = prefs.getBoolean("soundon", false);
		if (sound)
			bSound.setBackgroundResource(R.drawable.ic_lock_ringer_on);
		else
			bSound.setBackgroundResource(R.drawable.ic_lock_ringer_off);
		
		mContext = this;
	   	//wonTimer = prefs.getInt(prefix+ "wontimer", 0);
		//playerDeadTime = prefs.getInt(prefix+"playerDeadTime", 0);

		//game.setOnTouchListener()
		final Button bSave = (Button) findViewById(R.id.savebutton);
		bSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				game.showSaveMenu();
				// TODO Auto-generated method stub
				
			}
		});
	       bSound.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			  	SharedPreferences.Editor prefs = mContext.getPreferences(4).edit();
				//sound = prefs.getBoolean("soundon", false);
			
			sound= sound ? false : true;
				//sound = sound ? false : true;
				
				if (sound)
				{
					bSound.setBackgroundResource(R.drawable.ic_lock_ringer_on);
				}
					else
						
					bSound.setBackgroundResource(R.drawable.ic_lock_ringer_off);
				
				prefs.putBoolean("soundon", sound);
				prefs.commit();
				// TODO Auto-generated method stub
				
			}

			
		});
	       
	       
	        Configuration c = getResources().getConfiguration();
	        boolean hasDpad = false;
			//if(c.navigation == Configuration.NAVIGATION_DPAD)
	         //     hasDpad = true;
		    
	        //Button b = (Button) findViewById(R.id.go);
			
			
	        /*if (hasDpad)
	        {
	        	
	        	hidecontrols();
	        }
	        else
	        {
	        	showcontrols();
	        }*/
	        /*
	        b.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//game.resumegame();
					
					game.saveGame(1);
					
					return;
				}
			});*/
	        
	  	 // SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		
	   /*    if (savedInstanceState != null)
	       {
	    	   Logger.v("mine", "session still in memory");
	    	   //postdelay
	    	   //game.mLoaded =true;
	    	   
	    	   game.mLoaded=true;
	    	   game.resumegame();
	    	   
	       }
	       else
	       {*/
	    	   game.loadGame(-1);
	       }
	  	  
	
	       // PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
	      //  wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");
	        
	        
	  //game.start();
	      //  game.startDrawImage();
	        
	        

	        //game.setOnKeyListener(InputHandler.Key);
	        
	     //   new Thread() {
	        
	        	
	     //   }). OnUiThread(nll);
	        
	      /*game.setOnTouchListener( new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				  // if(!isDrawn) {
		                 GameCanvas mycanvas = (GameCanvas)findViewById(R.id.SurfaceView01);
		                mycanvas.startDrawImage();
		                //isDrawn = true;
		          //  }
				// TODO Auto-generated method stub
				return false;
			}
		});*/
	      
	    //  g.run();
	      
	  

	  
	  private void hidecontrols() {
		  
		  ((RelativeLayout)findViewById(R.id.dpad)).setVisibility(View.GONE);
		  ((RelativeLayout)findViewById(R.id.buttons)).setVisibility(View.GONE);
		  //findViewById(R.id.buttons);
		// TODO Auto-generated method stub
		
	}
	  public void hidedpad() {
		 // ((RelativeLayout)findViewById(R.id.dpad)).setVisibility(View.VISIBLE);
		  
		  ((ImageView)findViewById(R.id.up)).setBackgroundResource(R.drawable.trans);
		  ((ImageView)findViewById(R.id.left)).setBackgroundResource(R.drawable.trans);
		  ((ImageView)findViewById(R.id.right)).setBackgroundResource(R.drawable.trans);
		  ((ImageView)findViewById(R.id.down)).setBackgroundResource(R.drawable.trans);
		  
		  
		  
	  }
	  public void hidebuttons() {
		  //((RelativeLayout)findViewById(R.id.buttons)).setVisibility(View.VISIBLE);
		  ((ImageView)findViewById(R.id.x)).setBackgroundResource(R.drawable.trans);
		  ((ImageView)findViewById(R.id.c)).setBackgroundResource(R.drawable.trans);
		  
	  }
	  
	  public void showdpad() {
		 // ((RelativeLayout)findViewById(R.id.dpad)).setVisibility(View.VISIBLE);
		  ((ImageView)findViewById(R.id.up)).setBackgroundResource(R.drawable.up);
		  ((ImageView)findViewById(R.id.left)).setBackgroundResource(R.drawable.left);
		  ((ImageView)findViewById(R.id.right)).setBackgroundResource(R.drawable.right);
		  ((ImageView)findViewById(R.id.down)).setBackgroundResource(R.drawable.down);
	  }
	  public void showbuttons() {
		  //((RelativeLayout)findViewById(R.id.buttons)).setVisibility(View.VISIBLE);
		  ((ImageView)findViewById(R.id.x)).setBackgroundResource(R.drawable.xbutton);
		  ((ImageView)findViewById(R.id.c)).setBackgroundResource(R.drawable.cbutton);
		  
	  }
 private void showcontrols() {
		  
		  ((RelativeLayout)findViewById(R.id.dpad)).setVisibility(View.GONE);
		  ((RelativeLayout)findViewById(R.id.buttons)).setVisibility(View.VISIBLE);
		  //findViewById(R.id.buttons);
		// TODO Auto-generated method stub
		
	}
 
 @Override
 public boolean onKeyDown(int keyCode, KeyEvent event) {
     if (keyCode == KeyEvent.KEYCODE_MENU) {
        // Do your own menu here
    	 if(game!=null)
    	 {
    		 game.showSaveMenu();
    		 }
        return true;
     }
     return false;
 }

	/*@Override public void onOptionsMenuClosed(Menu menu) {
		  game.resumegame();
		  
	  };*/

	/*  @Override public boolean onOptionsItemSelected(MenuItem item) {
		 // if (item.)
		  if (item.getItemId() == R.id.save1)
		  {
			  game.saveGame(0);
		  }
		  if (item.getItemId() == R.id.save2)
		  {
			  game.saveGame(1);
		  }
		  if (item.getItemId() == R.id.save3)
		  {
			  game.saveGame(2);
		  }
		  if (item.getItemId() == R.id.save4)
		  {
			  game.saveGame(3);
		  }
		  
		  if (item.getItemId() == R.id.exit)
		  {
			  System.exit(0);
			  
		  }
		  if (item.getItemId() == R.id.cheat)
		  {
			  game.cheat();
			  //System.exit(0);
			  
		  }
		  return true;
	  };
	  
	  */
	/*	public  boolean onOptionsItemSelected(Activity a, final MenuItem item) {
			Intent i = null;
		
			if (item.getItemId() == R.id.settings) {
				i = new Intent(a, Settings.class);
				a.startActivityForResult(i, 0);
			} else if (item.getItemId() == R.id.expiresession) {
				PofSession.Session().expire();
			} else if (item.getItemId() == R.id.invalidatesession) {
				PofSession.Session().setSID("xxxxxxxxxx");
			}
			// } else if (item.getItemId() == R.id.logout) {
			// RF swap these 2 for 1.2
			// Home.Exit(this);

			// pHttpGet.logout(this);

			// }
			// } else if (item.getItemId() == R.id.exit) {

			// Home.Exit(c);
			// }

			return true;
		}*/
 	//@Override 

/*	  @Override public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
		  //	if (keyCode == 0)
		  //	{
		  		Logger.v("mine", String.format("keyup %d", keyCode));
		  //	}
		  return true;
	  }*/
	  
	//	@Override
	//	public boolean onCreateOptionsMenu(final Menu menu) {
//	/	//	final MenuInflater   inflater = getMenuInflater();
		//	Logger.v("mine", "oncreateoptionsmenu");
		//	return true;
		//}
			
			/*if (this instanceof Home) {
				inflater.inflate(R.menu.pof_menu_home, menu);
			} else 	if (this instanceof Debug) {
				inflater.inflate(R.menu.pof_menu_debug, menu);
			} else {
				inflater.inflate(R.menu.pof_menu, menu);
			}*/
			
		/*	if (PofApplication.debugEnabled(this))
			{
				inflater.inflate(R.menu.pof_menu_debug, menu);
			}
			else
			{
				inflater.inflate(R.menu.pof_menu, menu);
			}
		*/
			//if (game.mStarted )
		//	{
		//	inflater.inflate(R.menu.menu, menu);
		//	Logger.v("mine", "create menu");
			
		//	}
			//game.pause();
		//	return true;
		//}*/
	 @Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Logger.v("mine","activity stop");
		game.stop();
		
	} 
	 
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
    	Logger.v("mine", "resume");
		game.resumegame();
	
	}
	 
	 @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	
	  Logger.v("mine", "pause");
	  game.pause();
	  Logger.v("mine", "pause done");

	}
	 @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		//	Logger.v("mine","activity destroy");
			super.onDestroy();
	
			  Logger.v("mine", "Destroy");
		// game.stop();
	}
	 
		@Override
		public void onRestoreInstanceState(Bundle savedInstanceState) {
		  super.onRestoreInstanceState(savedInstanceState);
		  Logger.v("mine", "restore state");
	
		}
		@Override
		public void onSaveInstanceState(Bundle savedInstanceState) {
			  super.onSaveInstanceState(savedInstanceState);
				Logger.v("mine", "save state");
			}


		/*@Override
		public void adWhirlGeneric() {
			Logger.v("mine", "adWhirlGeneric");
			// TODO Auto-generated method stub
			
		}*/
	 
}