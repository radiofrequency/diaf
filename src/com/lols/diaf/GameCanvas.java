package com.lols.diaf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
/*
import com.adwhirl.AdWhirlLayout;
import com.adwhirl.AdWhirlManager;
import com.adwhirl.AdWhirlTargeting;
import com.adwhirl.adapters.AdWhirlAdapter;*/
import com.lols.diaf.entity.Bed;
import com.lols.diaf.entity.Lantern;
import com.lols.diaf.entity.Player;
import com.lols.diaf.entity.Torch;
import com.lols.diaf.entity.Workbench;
import com.lols.diaf.gfx.Color;
import com.lols.diaf.gfx.Font;
import com.lols.diaf.gfx.Screen;
import com.lols.diaf.gfx.SpriteSheet;
import com.lols.diaf.item.FurnitureItem;
import com.lols.diaf.item.ToolItem;
import com.lols.diaf.item.ToolType;
import com.lols.diaf.level.Level;
import com.lols.diaf.level.tile.Tile;
import com.lols.diaf.screen.ContextMenu;
import com.lols.diaf.screen.DeadMenu;
import com.lols.diaf.screen.LevelTransitionMenu;
import com.lols.diaf.screen.Menu;
import com.lols.diaf.screen.SaveMenu;
import com.lols.diaf.screen.SettingsMenu;
import com.lols.diaf.screen.TitleMenu;
import com.lols.diaf.screen.WonMenu;
import com.lols.diaf.R;

import android.R.color;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable.Callback;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import android.content.SharedPreferences;
/*import com.adwhirl.AdWhirlLayout;
import com.adwhirl.AdWhirlLayout.AdWhirlInterface;
import com.adwhirl.AdWhirlManager;
import com.adwhirl.adapters.AdWhirlAdapter;
import com.adwhirl.AdWhirlTargeting;
*/

public class GameCanvas extends SurfaceView implements Callback, SurfaceHolder.Callback {
    private CanvasThread canvasThread;
    private SurfaceHolder surfaceHolder;
    private boolean gamestarted =false;
    //AdWhirlLayout adWhirlLayout;
    static private loadTask loadtask = null;
    static private saveTask savetask = null;
	static  boolean showingAd = false;
    public Handler mHandler;
    private boolean paused = false;
    
    final Object monitor = new Object();
	long lastTime; //= System.nanoTime();
	double unprocessed;// = 0;
	double nsPerTick;// = 1000000000.0 / 60;
	int frames = 0;
	int ticks = 0;
	long lastTimer1; // = System.currentTimeMillis();
	
	
	private Object mPauseLock = new Object();  
	public boolean mPaused = true;
	
	public boolean mTouchDpad = true;
	
	private Object mLoadedLock = new Object();
	public boolean mLoaded = false;
	
	public  boolean mStarted = false;
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
	//	canvasThread.
		
	}
	 @Override
	    public void surfaceCreated(SurfaceHolder arg0) {
	        // TODO Auto-generated method stub
		 Logger.v("mine", "surface created");
		 this.surfaceHolder = getHolder();
		 if (canvasThread != null)
		 {
			// canvasThread.destroy();
		 		   canvasThread = new CanvasThread(getHolder());
	       // running=true;
		 }  
		   init();
		 
		   Logger.v("mine", "start thread");
	       canvasThread.setRunning(true);
	       canvasThread.start();
	        
	       Logger.v("mine", "thread started");
	       // notify();
	    }
	    
	    
	    @Override
	    public void surfaceDestroyed(SurfaceHolder arg0) {
	        // TODO Auto-generated method stub
			  Logger.v("mine", "surface destroyed");
	      /*  boolean retry = true;
	        //canvasThread.setRunning(false);
	        running = false;
	        while(retry) {
	            try {
	                canvasThread.join();
	            	canvasThread.destroy();
	                retry = false;
	            } catch (InterruptedException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	        }*/
	    }
	
    public GameCanvas(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
 
    public GameCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
 
       // this.getHolder().addCallback(this);
        this.surfaceHolder = getHolder();
        this.surfaceHolder.addCallback(this);
       this.canvasThread = new CanvasThread(surfaceHolder);
        this.setFocusable(true);
        
        /*this.setOnTouchListener( new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Logger.v("mine", String.format("on touch x %d y %d", event.getX(), event.getY()));
				
				
				return false;
			}
		});*/
      //  mContext = context.getApplicationContext();
    }
 
 
    public GameCanvas(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
 
    }
 
    public void resumegame() {
    	/*while(canvasThread== null)
    	{
    		Logger.v("mind", "waiting for surface to be created");
    	}*/
    	//init();
    	synchronized (mLoadedLock)
    	{
    		if (mLoaded)
    		{
    				mLoadedLock.notifyAll();
    				
    				synchronized (mPauseLock) {
    	    	        mPaused = false;
    	    	        mPauseLock.notifyAll();
    	    	    }
    			 
    			 /*if (canvasThread!=null)
    			 {
    				 if (!canvasThread.isAlive())
    				 {
    					 Logger.v("mine", "starting thread");
    					 canvasThread.run();
    				 }
    				 //if (canvasThread.)
    			 }*/
    		}
    		else
    		{
    			Logger.v("mine", "game not loaded ignoring");
    		}
    	}
    	//if (gameloaded)
    //	{
    	
    	//}
    	 
    	 
    	/*/synchronized(monitor)
        {
			  Logger.v("mine", "notify start");
            //canvasThread.notify();
			  
			  monitor.notify();
          //  monitor.
          
        }*/
    }
    public void start() {
    	//Logger.v("mine", "startDrawImage");
    //    canvasThread.setRunning(true);
    //if (canvasThread !=null)
    //	{
    	//	new Thread(this).start();
    		//canvasThread.setRunning(true);
    		//if (!canvasThread.isRun)
    		
    		if (!gamestarted)
    		{
    			loadGame(-1);
    			
   
    			gamestarted = true;
    		}
    
    }
 
   
 
    @Override
    protected void onDraw(Canvas canvas) {
   
        image.setPixels(pixels, 0, WIDTH, 0, 0, WIDTH, HEIGHT);        
        canvas.drawBitmap(image, new Rect(0,0,WIDTH,HEIGHT), new Rect(0,0,screenWidth, screenHeight),null);
    //	canvas.drawBitmap
      //  canvas.drawBitmap(pixels,0,WIDTH,0,0,screenWidth, screenHeight,false, null);
    	
       // canvas.d
        
    }
 
    public class CanvasThread extends Thread {
        private boolean isRun = false;
        private SurfaceHolder surfaceHolder;
        Handler mHandler;
        public CanvasThread(SurfaceHolder holder) {
            this.surfaceHolder = holder;
        }
 
        public void setRunning(boolean run) {
            this.isRun = run;
        }
 
       
        //@Override 
        @Override
        public void run() {
            // TODO Auto-generated method stub
        	Logger.v("mine", "canvas thread run");
        //	Logger.v("mine", "isRun ")
        
        
/*            Looper.prepare();

	          mHandler = new Handler() {
	              public void handleMessage(Message msg) {
	            	//  game.loadGame(1);
	                  // process incoming messages here
	            	  Logger.v("mine", "yo");
	              }
	          };

	          Looper.loop();*/
        	//init();
        	if (!isRun)
        	{
        		Logger.v("mine","not running?");
        		isRun = true;
        	}
        		
    		try {
    			synchronized(mLoadedLock)
                {
        		
        			while(!mLoaded)
        			{
        				Logger.v("mine", "not finsihed loading waiting");
        				mLoadedLock.wait();
        				Logger.v("mine", "load unlock");
        			}
                }
    		} catch (Exception ex)
    		{
    			
    		}
                      //  this.wait();
                     //   Logger.v("mine", "done wait");
                    
    			
        	
    		while (isRun) {
    			
    			
    			/*synchronized (mPauseLock) {
    			    while (mPaused) {
    			        try {
    			        	  Logger.v("mine", "paused waiting");
    			            mPauseLock.wait();
    			            
    			            
    			        } catch (InterruptedException e) {
    			        }
    			    }*/
    			//}
    			    
    			/*synchronized(mPauseLock)
        		{
        			try {
        				Logger.v("mine", "waiting");
        				mPauseLock.wait();
        			}catch (InterruptedException e) {
        	        }
        			
        		}*/
    			
    			long now = System.nanoTime();
    			unprocessed += (now - lastTime) / nsPerTick;
    			lastTime = now;
    			boolean shouldRender = true;
    			while (unprocessed >= 1) {
    				ticks++;
    				tick();
    				unprocessed -= 1;
    				shouldRender = true;
    			}

    			/*try {
    				Thread.sleep(2);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}*/

    			if (shouldRender) {
    				frames++;
    				render();
    			}

    			if (System.currentTimeMillis() - lastTimer1 > 1000) {
    				lastTimer1 += 1000;
    				Logger.v("mine", ticks + " ticks, " + frames + " fps");
    				frames = 0;
    				ticks = 0;
    			}
    			synchronized (mPauseLock) {
    			    while(mPaused) {
    			    	try {
    			    		Logger.v("mine", "thread paused");
    			    		//mPauseLock.wait(100000);
    			    		mPauseLock.wait();
    			    		Logger.v("mine", "done waiting");
    			    		lastTime = System.nanoTime();
    			    		ticks =0;
    			    		frames=0;
    			    		Logger.v("mine", "pause unlock");
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    			    }
    			}
        	
        }
    		Logger.v("mine", "thread finishing");

    }
    
    }
    
    
	private static final long serialVersionUID = 1L;
	private Random random = new Random();
	public static final String NAME = "Minicraft";
	public static final int HEIGHT = 120;
	public static final int WIDTH = 160;
	private static final int SCALE = 3;
/*	private static final int SHOW_BUTTONS = 0;
	private static final int HIDE_BUTTONS = 0;
	private static final int SHOW_DPAD = 0;
	private static final int HIDE_DPAD = 0;
*/
	public static GameActivity mContext =null;
	//private Bitmap image= new Bitmap();
	
	private Bitmap image = null; //new .getBitmap();
	private int[] pixels =null;
	//image.getPixels();
//	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
//	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	private boolean running = false;
	private Screen screen;
	private Screen lightScreen;
	private InputHandler input =null;
	private int[] colors = new int[256];
	private int tickCount = 0;
	public int gameTime = 0;

	private Level level;
	private Level[] levels = new Level[5];
	private int currentLevel = 3;
	public Player player;

	public static Menu menu;
	private int playerDeadTime;
	private int pendingLevelChange;
	private int wonTimer = 0;
	private int screenWidth =0;
	private int screenHeight=0;
	public boolean hasWon = false;
	public boolean loadedFromSave= false;
	//private Object surfaceHolder;
	public boolean mCheated = false;

	public void setMenu(Menu menu) {
		this.menu = menu;
		if (menu != null) menu.init(this, input);
	}

	/*public void start() {
		running = true;
		new Thread(this).start();
	}*/

	
	public void stop() {
		Logger.v("mine", "stop");
		running = false;
		canvasThread.setRunning(false);
		synchronized(mPauseLock)
		{
			mPaused = false;
			mPauseLock.notify();
		}
		
		while(true)
		{
		  try {
				canvasThread.join(); 
				break;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	//	canvasThread.stop();
		//this.canvasThread.setRunning(false);
//		canvasThread.stop();
	}

	public void saveGame(int slot)
	{
		if (savetask != null)
		{
			savetask.cancel(true);
		}
		savetask = new saveTask(slot);
		//savetask.setSlot(slot);
		savetask.execute();
		
	}
	public void saveGameTask(int slot) {
		//public void onSaveInstanceState(Bundle savedInstanceState) {
			  // Save UI state changes to the savedInstanceState.
			  // This bundle will be passed to onCreate if the process is
			  // killed and restarted.
		//prefs.putBoolean("playerDeadTime", true);
	//if ()
		SharedPreferences prefs = mContext.getPreferences(4);
		SharedPreferences.Editor editor = prefs.edit();
		
		String prefix = String.format("game12-%d", slot);
		Logger.v("mine", "save game");
		//prefs.
		editor.putInt(prefix+ "wontimer", wonTimer);
		editor.putInt(prefix+"playerDeadTime", playerDeadTime);
		editor.putInt(prefix+"gametime", gameTime);
		editor.putBoolean(prefix+"haswon", hasWon);
		editor.putBoolean(prefix+"save", true);
		editor.putInt(prefix+"currentlevel",currentLevel);
		editor.putBoolean(prefix+"cheated", mCheated);
		
		//player.remove();
		//level.remove(player);
		
		for (int c=0; c<5; c++)
		{
			
		
		//for (Level l : levels)
		//{
			
			// Bla bla = new Bla(); 
			   
			  // assuming that both those serialize and deserialize methods are under the SerializerClass 
			  //byte[] blaBytes = SerializerClass.serializeObject(bla); 
			   
			  //Bla deserializedBla = (Bla) SerializerClass.deserializeObject(blaBytes);
			   // Create a path where we will place our private file on external
		    // storage.
			
			Logger.v("mine", String.format("saving level12-%d", c));
		    File file = new File(mContext.getCacheDir(), String.format("%d-level12-%d", slot,c));
		    
			byte[] level = Level.serializeObject(levels[c]);
			Logger.v("mine", String.format("level saved %d", level.length));
		    OutputStream os;
			try {
				os = new FileOutputStream(file);
			    os.write(level);
		        os.close();
			 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        //byte[] data = new byte[i s.available()];
		       // is.read(data);
		    
		
			
			//levels[c].saveLevel(editor);
		}
		
		editor.commit();
	}
	
	
	
	public class saveTask extends AsyncTask<Object, Object, Object>
	{
		int slot;
		//ProgressDialog p;
		private ProgressDialog Dialog;
		public saveTask(int slot) {
			this.slot = slot;
			// TODO Auto-generated constructor stub
		}


		@Override
		protected void onPreExecute() {
				//TODO Auto-generated method stub
				super.onPreExecute();
				 //p = ProgressDiaLogger.show(GameCanvas.this, "Saving...", "Please wait...");
			
				pause();
		}
		
		@Override protected  void onProgressUpdate(Object[] values) {
			
			Dialog =  new ProgressDialog(GameCanvas.mContext);
			Dialog.setMessage("Saving..");
			//DiaLogger.setIcon(R.drawable.ic_launcher);
			Dialog.setIcon(R.drawable.ic_menu_save);
			Dialog.show();
		};

		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			publishProgress("show");
			saveGameTask(slot);
			return null;
		}
		
		
		@Override protected void onCancelled() {
			if (Dialog!=null)
				Dialog.cancel();
		}
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			if (Dialog!=null)
				Dialog.cancel();
			
			setMenu(null);
			resumegame();
			
			super.onPostExecute(result);
			
		}
		
		
	}
	
	
	public class loadTask extends AsyncTask<Object, Object, Object> {

		int slot =-1;
		private ProgressDialog Dialog;
		boolean loadingsave = false;
		public loadTask(int slot) {
			this.slot = slot;
		
			           // mProgressDialog = ProgressDiaLogger.show(YouTube.this.getApplicationContext(), "", YouTube.this.getString(R.string.loading), true);
			        	
			            // start time consuming background process here
			
			 
			// TODO Auto-generated constructor stub
		}
		
	  @Override 
	  protected  void onProgressUpdate(Object[] values) {
		  Dialog = new ProgressDialog(GameCanvas.mContext);
		  Dialog.setMessage("Loading..");
			//DiaLogger.setIcon(R.drawable.ic_launcher);
		  Dialog.setIcon(R.drawable.ic_menu_save);
		  Dialog.show();
		  
	  }
	    

		@Override
		protected Object doInBackground(Object... arg0) {
			// TODO Auto-generated method stub
			
			publishProgress("show");
			//init();
			lastTime = System.nanoTime();
    		unprocessed = 0;
    		nsPerTick = 1000000000.0 / 60;
    		frames = 0;
    		ticks = 0;
    		lastTimer1 = System.currentTimeMillis();
    		
			boolean loaded = false;
			
			if (slot == -2 )
			{
				Logger.v("mine", "reset game and show");
				//init();
				mCheated=false;
				resetGame();
				setMenu(null);
				mStarted=true;
			}else
			if (slot ==-1)  // menu
			{
				Logger.v("mine", "load new game");
				// new game
				//init();
				resetGame();
				mCheated=false;
				setMenu(new TitleMenu(mContext));
				mStarted = false;
			}
			else
			{
				loadingsave = true;
				Logger.v("mine", "loading save");
				//load levels
				for (int c=0; c< 5; c++)
				{
					Logger.v("mine", String.format("loading level12-%d", c));
				    File file = new File(mContext.getCacheDir(), String.format("%d-level12-%d", slot,c));
				    
				//	byte[] level = Level.serializeObject(levels[c]);
				//	Logger.v("mine", String.format("level saved %d", level.length));
				   // OutputStream os;
				    
				
				    
					try {
					    InputStream is = new FileInputStream(file);
					    
					    long longlength = file.length();
			            int length = (int) longlength;
			           // if (length != longlength) throw new IOException("File size >= 2 GB");

			            // Read file and return data
			            byte[] data = new byte[length];
			            
			            is.read(data);
			            //is.readFully(data);
			            levels[c] = Level.deserializeObject(data,input, GameCanvas.this );
			            
			            
			            Logger.v("mine", "loaded");
			            loaded = true;
						//byte[] level;
						//is.read(level);
					 //   os.write(level);
				      //  os.close();
					 
					} catch (Exception e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						resetGame();
						mCheated=false;
						setMenu(new TitleMenu(mContext));
						mStarted = false;
						
					}
				}
				//();
				String prefix = String.format("game12-%d",slot);
				SharedPreferences prefs = mContext.getPreferences(4);
				wonTimer = prefs.getInt(prefix+ "wontimer", 0);
				playerDeadTime = prefs.getInt(prefix+"playerDeadTime", 0);
				gameTime = prefs.getInt(prefix+"gametime", 0);
				hasWon = prefs.getBoolean(prefix+"haswon", false);
				//prefs.getBoolean(prefix+"save", true);
				currentLevel = prefs.getInt(prefix+"currentlevel",3);
				
				mCheated = prefs.getBoolean(prefix+"cheated", false);
				//playerDeadTime = 0;
				//wonTimer = 0;
				//gameTime = 0;
				//hasWon = false;

			//	levels = new Level[5];
				//currentLevel = 3;

			//	levels[4] = new Level(128, 128, 1, null);
			//	levels[3] = new Level(128, 128, 0, levels[4]);
			//	levels[2] = new Level(128, 128, -1, levels[3]);
			//	levels[1] = new Level(128, 128, -2, levels[2]);
			//	levels[0] = new Level(128, 128, -3, levels[1]);

				level = levels[currentLevel];
				
				player = levels[currentLevel].findPlayer();
				if (player == null)
				{
					//player = new Player(this, input);
					//level = levels[currentLevel];
					player = new Player(GameCanvas.this, input);
					player.findStartPos(level);

					level.add(player);

					for (int i = 0; i < 5; i++) {
						levels[i].trySpawn(5000);
					}
				
					
				}else {
					
				player.input = input;
				
				}
				//load.. init has already been called.
				
				
				
				
			}
	
			
			return null;
		}
		@Override protected void onPreExecute() {
			pause();
		}; 
		
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			Logger.v("mine", "load finished");
			super.onPostExecute(result);
			 //synchronized(monitor)
             //{
    			  Logger.v("mine", "notify start");
                 //canvasThread.notify();
    			  	synchronized (mLoadedLock)
    		    	{
    			  		mLoaded = true;
    			  	
    		    	}
    			  	if (loadingsave)
    			  	{
    			  		
    			  		setMenu(null);
    			  		
    			  		mStarted = true;
    			  	}
    			    resumegame();
    			    if (Dialog!=null)
    			    	Dialog.dismiss();
    			    
    			    //if (slot ==-1)
    			    	
			
		}
		
		 @Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
			if (Dialog != null)
				Dialog.dismiss();
		}
		
		
		
	}
	
	synchronized public void loadGame(int slot) {
		if (loadtask != null)
		{
			Logger.v("mine", "already loaded load task");
		//	return;
			
			loadtask.cancel(true);
			loadtask=null;
		}
		//int slot =-1;
		
		
		loadtask= new loadTask(slot);
		loadtask.execute();
		/*SharedPreferences prefs = mContext.getPreferences(4);
		//playerDeadTime = perf.getInt("playerdeadtime", 0);
		String prefix = "game";
		
		wonTimer = prefs.getInt(prefix +"wontimer", 0);
		playerDeadTime = prefs.getInt(prefix +"playerDeadTime",0);
		gameTime = prefs.getInt(prefix +"gametime", 0);
		hasWon = prefs.getBoolean(prefix +"haswon", false);
		currentLevel = prefs.getInt(prefix +"currentlevel",0);
		loadedFromSave= true;
		//levels = new Level[5];
		
		//currentLevel = 3;*
		//resetGame();
		
		//wonTimer = 0;
		//gameTime = 0;
		//hasWon = false;

		levels = new Level[5];
		currentLevel = 3;

		levels[4] = new Level(128, 128, 1, null);
		levels[3] = new Level(128, 128, 0, levels[4]);
		levels[2] = new Level(128, 128, -1, levels[3]);
		levels[1] = new Level(128, 128, -2, levels[2]);
		levels[0] = new Level(128, 128, -3, levels[1]);

		level = levels[currentLevel];
		player = new Player(this, input);
		player.findStartPos(level);

		level.add(player);

		for (int i = 0; i < 5; i++) {
			levels[i].trySpawn(5000);
		}*/
	
	}
	
	public void resetGame() {
		playerDeadTime = 0;
		wonTimer = 0;
		gameTime = 0;
		hasWon = false;

		levels = new Level[5];
		currentLevel = 3;
		//currentLevel=0;

		levels[4] = new Level(128, 128, 1, null);
		levels[3] = new Level(128, 128, 0, levels[4]);
		levels[2] = new Level(128, 128, -1, levels[3]);
		levels[1] = new Level(128, 128, -2, levels[2]);
		levels[0] = new Level(128, 128, -3, levels[1]);

		level = levels[currentLevel];
		player = new Player(this, input);
		player.findStartPos(level);

		//levels[0].add(player);
		level.add(player);

		for (int i = 0; i < 5; i++) {
			levels[i].trySpawn(5000);
		}
	
	}

	private void init() {
		int pp = 0;
		for (int r = 0; r < 6; r++) {
			for (int g = 0; g < 6; g++) {
				for (int b = 0; b < 6; b++) {
					int rr = (r * 255 / 5);
					int gg = (g * 255 / 5);
					int bb = (b * 255 / 5);
					int mid = (rr * 30 + gg * 59 + bb * 11) / 100;

					int r1 = ((rr + mid * 1) / 2) * 230 / 255 + 10;
					int g1 = ((gg + mid * 1) / 2) * 230 / 255 + 10;
					int b1 = ((bb + mid * 1) / 2) * 230 / 255 + 10;
					colors[pp++] = r1 << 16 | g1 << 8 | b1;

				}
			}
		}
		try {

			if (input==null)
			{
				input =  new InputHandler(this, mContext);
				
				
				
			}

			
			setOnTouchListener (input);
			 /* mContext.findViewById(R.id.x).setOnTouchListener( input);
			  mContext.findViewById(R.id.c).setOnTouchListener( input);
			  mContext.findViewById(R.id.left).setOnTouchListener( input);
			  mContext.findViewById(R.id.right).setOnTouchListener( input);
			  mContext.findViewById(R.id.up).setOnTouchListener( input);
			  mContext.findViewById(R.id.down).setOnTouchListener( input);
			  mContext.findViewById(R.id.x).setOnTouchListener( input);
		       */
		       mContext.findViewById(R.id.SurfaceView01).setOnTouchListener(input);
		       
			Display display = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
			screenWidth = display.getWidth();
			screenHeight = display.getHeight();
					
			//screenWidth = 
			 pixels = new int[HEIGHT * WIDTH];
			image = //BitmapFactory.decodeResource(getResources(), R.drawable.icons);
					Bitmap.createBitmap(WIDTH, HEIGHT, Config.RGB_565 );
			image.getPixels(pixels, 0, WIDTH, 0, 0, WIDTH, HEIGHT);
			
			/*this.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					Logger.v("mine", "onKey");
					// TODO Auto-generated method stub
					return input.onKey(v, keyCode, event);
					//onreturn false;
				}
			});*/
			
		/*	for(int c=0; c< pixels.length; c++)
			{
				pixels[c] = Color.get(random.nextInt(255),random.nextInt(255),random.nextInt(255),random.nextInt(255));
				//Logger.v("mine",String.format("pixel %d color: %d", c, pixels[c]));
			}*/
			Resources res = mContext.getResources();
			Bitmap simage = BitmapFactory.decodeResource(getResources(), R.drawable.icons);
			
			//  GameCanvas.this (BitmapDrawable)res.getDrawable(R.drawable.icons).;
					/*(R.drawable.icons)
							ImageIO.read(Game.class.getResourceAsStream("/icons.png"))));*/
			screen = new Screen(WIDTH, HEIGHT, new SpriteSheet(simage));
			lightScreen = new Screen(WIDTH, HEIGHT, new SpriteSheet(simage));
		} catch (Exception e) {
			//Logger.v("mine", e.getMessage());
			
			e.printStackTrace();
		}

		//resetGame();
		//setMenu(new TitleMenu());
	}

	/*public void runCraft() {
		if (!gamestarted)
		{
		
		
		long lastTime = System.nanoTime();
		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / 60;
		int frames = 0;
		int ticks = 0;
		long lastTimer1 = System.currentTimeMillis();
		Logger.v("mine", "init");
		init();
		Logger.v("mine", "done init");

		while (running) {
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			while (unprocessed >= 1) {
				ticks++;
				tick();
				unprocessed -= 1;
				shouldRender = true;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer1 > 1000) {
				lastTimer1 += 1000;
				Logger.v("mine", ticks + " ticks, " + frames + " fps");
				frames = 0;
				ticks = 0;
			}
		}
		}
		else
		{
			Logger.v("mine", "second time in runcraft");
		}
	}*/

	public void tick() {
		//tickCount++;
		if (!hasFocus2()) {
			input.releaseAll();
		} else {
			
			if (player!=null)
			{
				if (!player.removed && !hasWon) gameTime++;
			}
			
			if (input !=null)
			{
				input.tick();
			}
			
			
			if (menu != null) {
				menu.tick();
			} else {
				if (input.context.clicked)
				{
					Logger.v("mine", "show menu");
				//	showSaveMenu();
					
					//showContextMenu();
				}
				if (player!=null)
				{
					if (player.removed) {
						playerDeadTime++;
						if (playerDeadTime > 60) {
							setMenu(new DeadMenu(mContext));
							
						}
					} else {
						if (pendingLevelChange != 0) {
							setMenu(new LevelTransitionMenu(pendingLevelChange));
							pendingLevelChange = 0;
						}
					}
					if (wonTimer > 0) {
						if (--wonTimer == 0) {
							setMenu(new WonMenu(mContext));
						}
					}
					
					level.tick();
					Tile.tickCount++;
				}
			}
		}
	}

	private void showMenu() {
		//mContext.onCreateContextMenu(menu, v, menuInfo)
		
		// TODO Auto-generated method stub
		//showContextMenu();
		pause();
		setMenu(new SettingsMenu(null,mContext));
	}
	private boolean hasFocus2() {
		// TODO Auto-generated method stub
		return true;
	}

	
	public void changeLevel(int dir) {
		level.remove(player);
		currentLevel += dir;
		level = levels[currentLevel];
		player.x = (player.x >> 4) * 16 + 8;
		player.y = (player.y >> 4) * 16 + 8;
		level.add(player);

	}

	public void render() {
		/*BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			requestFocus();
			return;
		}*/
		

		int xScroll = player.x - screen.w / 2;
		int yScroll = player.y - (screen.h - 8) / 2;
		if (xScroll < 16) xScroll = 16;
		if (yScroll < 16) yScroll = 16;
		if (xScroll > level.w * 16 - screen.w - 16) xScroll = level.w * 16 - screen.w - 16;
		if (yScroll > level.h * 16 - screen.h - 16) yScroll = level.h * 16 - screen.h - 16;
		if (currentLevel > 3) {
			int col = Color.get(20, 20, 121, 121);
			for (int y = 0; y < 14; y++)
				for (int x = 0; x < 24; x++) {
					screen.render(x * 8 - ((xScroll / 4) & 7), y * 8 - ((yScroll / 4) & 7), 0, col, 0);
				}
		}

		level.renderBackground(screen, xScroll, yScroll);
		level.renderSprites(screen, xScroll, yScroll);

		if (currentLevel < 3) {
			lightScreen.clear(0);
		
			level.renderLight(lightScreen, xScroll, yScroll);
			screen.overlay(lightScreen, xScroll, yScroll);
		}

		renderGui();

		//if (!hasFocus2()) renderFocusNagger();
		
		//if (mPaused)
		//{
			//renderPause();
		//}
		
	//	renderButtons();

		for (int y = 0; y < screen.h; y++) {
			for (int x = 0; x < screen.w; x++) {
				int cc = screen.pixels[x + y * screen.w];
				if (cc < 255) pixels[x + y * WIDTH] = colors[cc];
			}
		}
		
		if (this.surfaceHolder.getSurface().isValid())
		{
			Canvas c=null;
		    try {
		    	//if (!surfaceHolder.isCreatin)
		    		
		    	/*while (!surfaceHolder.isCreating())
		    	{
		    		Logger.v("mine", "is creating");
		    	}*/
		    	//Logger.v("mine", "lock");
	            c = this.surfaceHolder.lockCanvas();
	            synchronized(this.surfaceHolder) {
	            	
	            	//Logger.v("TAG", "render");
	                GameCanvas.this.onDraw(c);
	            }
	        } finally {
	        
	        		surfaceHolder.unlockCanvasAndPost(c);
	        }
		}
		
			
		  // GameCanvas.this.onDraw();
	}

		 private void renderButtons() {
			 Font.draw("C", screen, WIDTH - 10, HEIGHT/2 +2, Color.get(5, 555, 555, 555));
			 Font.draw("X", screen, WIDTH - 10, HEIGHT/2 -10, Color.get(5, 555, 555, 555));
				// TO
			 // TODO Auto-generated method stub
		
	}
		private void renderPause() {
				String msg = "PAUSED";
				int xx = (WIDTH - msg.length() * 8) / 2;
				int yy = (HEIGHT - 8) / 2;
				int w = msg.length();
				int h = 1;

				screen.render(xx - 8, yy - 8, 0 + 13 * 32, Color.get(-1, 1, 5, 445), 0);
				screen.render(xx + w * 8, yy - 8, 0 + 13 * 32, Color.get(-1, 1, 5, 445), 1);
				screen.render(xx - 8, yy + 8, 0 + 13 * 32, Color.get(-1, 1, 5, 445), 2);
				screen.render(xx + w * 8, yy + 8, 0 + 13 * 32, Color.get(-1, 1, 5, 445), 3);
				for (int x = 0; x < w; x++) {
					screen.render(xx + x * 8, yy - 8, 1 + 13 * 32, Color.get(-1, 1, 5, 445), 0);
					screen.render(xx + x * 8, yy + 8, 1 + 13 * 32, Color.get(-1, 1, 5, 445), 2);
				}
				for (int y = 0; y < h; y++) {
					screen.render(xx - 8, yy + y * 8, 2 + 13 * 32, Color.get(-1, 1, 5, 445), 0);
					screen.render(xx + w * 8, yy + y * 8, 2 + 13 * 32, Color.get(-1, 1, 5, 445), 1);
				}

				if ((tickCount / 20) % 2 == 0) {
					Font.draw(msg, screen, xx, yy, Color.get(5, 333, 333, 333));
				} else {
					Font.draw(msg, screen, xx, yy, Color.get(5, 555, 555, 555));
				}
	}
		// Bitmap sweet = BitmapFactory.decodeResource(getResources(), R.drawable.icons);
		//  GameCanvas.this.onDraw(c);
		  //while(isRun) {
           //   c = null;
		/*  Canvas c = null;
              try {
                  c = this.surfaceHolder.lockCanvas(null);
                  synchronized(this.surfaceHolder) {
                  	
                  	Logger.v("TAG", "hi");
                      Game.this.onDraw(c);
                  }
              } finally {
                  surfaceHolder.unlockCanvasAndPost(c);
              }
          }*/
		  


		
		//c.draw
	//	Graphics g = bs.getDrawGraphics();
	//	image.
	//	g.fillRect(0, 0, getWidth(), getHeight());

	//	int ww = WIDTH * 3;
	//	int hh = HEIGHT * 3;
//		int xo = (getWidth() - ww) / 2;
	//	int yo = (getHeight() - hh) / 2;
	//	g.drawImage(image, xo, yo, ww, hh, null);
	//	g.dispose();
	//	bs.show();
//	}

	private void renderGui() {
		for (int y = 0; y < 2; y++) {
			for (int x = 0; x < 20; x++) {
				screen.render(x * 8, screen.h - 16 + y * 8, 0 + 12 * 32, Color.get(000, 000, 000, 000), 0);
			}
		}

		for (int i = 0; i < 10; i++) {
			if (i < player.health)
				screen.render(i * 8, screen.h - 16, 0 + 12 * 32, Color.get(000, 200, 500, 533), 0);
			else
				screen.render(i * 8, screen.h - 16, 0 + 12 * 32, Color.get(000, 100, 000, 000), 0);

			if (player.staminaRechargeDelay > 0) {
				if (player.staminaRechargeDelay / 4 % 2 == 0)
					screen.render(i * 8, screen.h - 8, 1 + 12 * 32, Color.get(000, 555, 000, 000), 0);
				else
					screen.render(i * 8, screen.h - 8, 1 + 12 * 32, Color.get(000, 110, 000, 000), 0);
			} else {
				if (i < player.stamina)
					screen.render(i * 8, screen.h - 8, 1 + 12 * 32, Color.get(000, 220, 550, 553), 0);
				else
					screen.render(i * 8, screen.h - 8, 1 + 12 * 32, Color.get(000, 110, 000, 000), 0);
			}
		}
		if (player.activeItem != null) {
			player.activeItem.renderInventory(screen, 10 * 8, screen.h - 16);
		}

		if (menu != null) {
			menu.render(screen);
		}
	}

	private void renderFocusNagger() {
		String msg = "Click to focus!";
		int xx = (WIDTH - msg.length() * 8) / 2;
		int yy = (HEIGHT - 8) / 2;
		int w = msg.length();
		int h = 1;

		screen.render(xx - 8, yy - 8, 0 + 13 * 32, Color.get(-1, 1, 5, 445), 0);
		screen.render(xx + w * 8, yy - 8, 0 + 13 * 32, Color.get(-1, 1, 5, 445), 1);
		screen.render(xx - 8, yy + 8, 0 + 13 * 32, Color.get(-1, 1, 5, 445), 2);
		screen.render(xx + w * 8, yy + 8, 0 + 13 * 32, Color.get(-1, 1, 5, 445), 3);
		for (int x = 0; x < w; x++) {
			screen.render(xx + x * 8, yy - 8, 1 + 13 * 32, Color.get(-1, 1, 5, 445), 0);
			screen.render(xx + x * 8, yy + 8, 1 + 13 * 32, Color.get(-1, 1, 5, 445), 2);
		}
		for (int y = 0; y < h; y++) {
			screen.render(xx - 8, yy + y * 8, 2 + 13 * 32, Color.get(-1, 1, 5, 445), 0);
			screen.render(xx + w * 8, yy + y * 8, 2 + 13 * 32, Color.get(-1, 1, 5, 445), 1);
		}

		if ((tickCount / 20) % 2 == 0) {
			Font.draw(msg, screen, xx, yy, Color.get(5, 333, 333, 333));
		} else {
			Font.draw(msg, screen, xx, yy, Color.get(5, 555, 555, 555));
		}
	}

	public void scheduleLevelChange(int dir) {
		pendingLevelChange = dir;
	}
/*
	public static void main(String[] args) {
		Game game = new Game();
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		JFrame frame = new JFrame(Game.NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(game, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		game.start();
	}*/

	public void won() {
		wonTimer = 60 * 3;
		hasWon = true;
	}

	/*@Override
	public void run () {*/
	//	Logger.v("mine", "GameCanvas run");
	/*	Logger.v("mine", String.format("tickcount:%d", tickCount));
		tickCount++;
		Canvas c = null;
		 try {
	            c = this.surfaceHolder.lockCanvas(null);
	            synchronized(this.surfaceHolder) {
	            	
	            	Logger.v("TAG", "render");
	                GameCanvas.this.onDraw(c);
	            }
	        } finally {
	            surfaceHolder.unlockCanvasAndPost(c);
	        }
		//runCraft();
		// TODO Auto-generated method stub
		*/
	//}

	public void setContext(GameActivity gameActivity) {
		// TODO Auto-generated method stub
		mContext  = gameActivity;
	}
	public void pause() {
	
		//canvasThread.setRunning(false);
		//paused = true;
		///while (true)
	//	{
			Logger.v("mine", "new pause");
			synchronized (mPauseLock) {
		        mPaused = true;
		    }
			
		  //try {
		//	canvasThread.join(); 
	//		break;
	//	} catch (InterruptedException e) {
			// TODO Auto-generated catch block
	//		e.printStackTrace();
	//	}
		 
		//}
	/*	if (canvasThread.isAlive())
		{
			Logger.v("mini", "pause killing game");
			canvasThread.setRunning(false);
			
			try {
				monitor.notify();
			}catch (Exception ex)
			{
			
			}
		}*/
		//canvasThread.stop();
		
		// TODO Auto-generated method stub
		
	}
	public void loadSaveGame(int selected) {
		// TODO Auto-generated method stub
	//	loadGame(selected);
		Logger.v("mine", String.format("loading game %d", selected));
		loadGame(selected);
		
	}
	public void cheat() {
		mCheated = true;
		player.inventory.add(new ToolItem(ToolType.sword,4));
		player.inventory.add(new ToolItem(ToolType.axe,4));
		player.inventory.add(new ToolItem(ToolType.shovel,4));
		player.inventory.add(new ToolItem(ToolType.pickaxe,4));
		player.inventory.add(new ToolItem(ToolType.hoe, 4));
		player.inventory.add(new ToolItem(ToolType.staff,4));
		
		player.inventory.add(new FurnitureItem(new Torch()));
		player.inventory.add(new FurnitureItem(new Lantern()));
		player.inventory.add(new FurnitureItem(new Workbench()));
		// TODO Auto-generated method stub
		
	}
	
	public void showbuttons()
	{
		mHandler.sendEmptyMessage(GameActivity.SHOW_BUTTONS);
	}
	public void hidebuttons()
	{
		mHandler.sendEmptyMessage(GameActivity.HIDE_BUTTONS);
	}
	
	
	public void showdpad()
	{
		mHandler.sendEmptyMessage(GameActivity.SHOW_DPAD);
	}
	
	public void hidedpad()
	{
		mHandler.sendEmptyMessage(GameActivity.HIDE_DPAD);
	}
	
	public void setHandler(Handler handler) {
		mHandler= handler;
		// TODO Auto-generated method stub
		
	}
	public void showSaveMenu() {
		
		//pause();
		
		//showAd();
		setMenu(new ContextMenu(null,mContext));
		//setMenu(new SaveMenu(null,mContext));
		
		//Handler h = new Handler();
		//mContext.handler.removeCallbacks(removeAd);
		//mContext.handler.postDelayed(removeAd, 30000);
		// TODO Auto-generated method stub
		
	}
	
/*	public void showAd()
	{
		

		 
		 if (!showingAd)
		 {
			 showingAd = true;
	   int layoutid=909;
      AdWhirlManager.setConfigExpireTimeout(1000 * 60 * 5);
      
	  // AdWhirlTargeting.setAge(23);
      //AdWhirlTargeting.setGender(AdWhirlTargeting.Gender.MALE);
     // AdWhirlTargeting.setKeywords("online phonebook contact manager contacts");
     // AdWhirlTargeting.setPostalCode("94123");
      
      TextView t = new TextView(mContext);
      
   //   t.setText("happy happy happy");
      
    //  Toast.makeText(mContext, "bah", Toast.LENGTH_SHORT);
     
      
     AdWhirlTargeting.setTestMode(false);
     adWhirlLayout  = (AdWhirlLayout)mContext.findViewById(909);
      RelativeLayout layout = (RelativeLayout)mContext.findViewById(R.id.main);
      if (layout ==null)
      {
         return;
      }
      if(adWhirlLayout == null)
      {
          adWhirlLayout = new AdWhirlLayout(mContext,
                                                      "22ccade4eaa54beeae3f677951ad3ad7");
          adWhirlLayout.setAdWhirlInterface(mContext);
          adWhirlLayout.setId(layoutid);
        //  adWhirlLayout.setBackgroundColor(android.graphics.Color.RED);

          RelativeLayout.LayoutParams layoutParams = new
          RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,
                                          LayoutParams.WRAP_CONTENT);
          layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
          layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
          
          //layoutParams.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.SurfaceView01);
         adWhirlLayout.setLayoutParams(layoutParams);
         layout.addView(adWhirlLayout);
         
      }
  
      layout.addView(t);
      
		 }
     /* AdWhirlAdapter.setGoogleAdSenseCompanyName("Folstad Consulting Inc");
      AdWhirlAdapter.setGoogleAdSenseAppName("Komodo Contact Manager");
      AdWhirlAdapter.setGoogleAdSenseExpandDirection("TOP");
      AdWhirlAdapter.setGoogleAdSenseChannel("5790217756");
      //layout.invalidate();
  

	}*/
	
/*	public Runnable removeAd = new Runnable() {
		
		public void run() {
			showingAd = false;
			mContext.handler.removeCallbacks(removeAd);
			Logger.v("mine", "removing ad");
			// LinearLayout layout = (LinearLayout)findViewById(909);
		      if (adWhirlLayout ==null)
		      {
		         return;
		      }
		      else
		      {
		    	  RelativeLayout layout = (RelativeLayout)mContext.findViewById(R.id.main);
		    	  layout.removeView(adWhirlLayout);
		    	  adWhirlLayout.setAdWhirlInterface(null);
		    	  adWhirlLayout = null;
		      }
		      
		}
	};*/
	//{
	public void respawn() {
		player = new Player(this, input);
		//level.remove(player);
		//changeLevel(3);
		currentLevel = 3;
		level = levels[currentLevel];
		//player.x = (player.x >> 4) * 16 + 8;
		//player.y = (player.y >> 4) * 16 + 8;
		
		Bed b = level.findBed();
		
		if (b!=null)
		{
			player.x = b.x;
			player.y = b.y;
		}else
		{
			player.findStartPos(level);
		}
		
		
		level.add(player);
		//		//levels[0].add(player);
		//level.add(player);
		

		
		//for (int i = 0; i < 5; i++) {
		///	levels[i].trySpawn(5000);
		//}
		setMenu(null);
		//resetGame();
		
		// TODO Auto-generated method stub
		
	}
	public void sleep() {
		//TODO interstitial
		player.health = 10;
		// TODO Auto-generated method stub
		
	}
		  
//	}


}