package com.lols.diaf;

//import android.view.*;


//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import com.lols.diaf.gfx.Color;
import com.lols.diaf.gfx.Font;
import com.lols.diaf.R;

import android.app.Activity;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.*;
import android.widget.Button;
import android.widget.ImageView;


public class InputHandler implements OnKeyListener,  OnTouchListener {
	
	public class mButton {
		float x;
		float y;
		float w;
		float h;
		int id;
		//boolean init = false;
		Key k;
		
		boolean pressed;
		public mButton(int id, Key k) {
			
			this.id = id;
			this.k = k;
			buttons.add(this);
		}
		
		public void toggle(boolean b) {
			pressed = b;
			k.toggle(b);
		}
		public void init()
		{
			//if (!init)
			//{
				
				ImageView t = (ImageView)mContext.findViewById(id);
				if (t!=null)
				{
					int[] loc = new int[2];		
					t.getLocationInWindow(loc);
					y = loc[1]-5;
					x = loc[0]-5;
					w = t.getWidth()+5;
					h = t.getHeight()+5;
					//init = true;
				}
			//}
		}
		public boolean in(float _x, float _y)
		{
			boolean ret= false;
			if ((_x  <= x+w ) && (_x >= x)  &&
				(_y <= y+h) && (_y >= y))
			{
				ret = true;
			}
			return ret;
			
		}
		
		//public touch (MouseEvent)
		
	}
	public class Key {
		public int presses, absorbs;
		public boolean down, clicked;

		public Key() {
			keys.add(this);
		}

		public void toggle(boolean pressed) {
			if (pressed != down) {
				down = pressed;
			}
			if (pressed) {
				presses++;
			}
		}

		public void tick() {
			if (absorbs < presses) {
				absorbs++;
				clicked = true;
			} else {
				clicked = false;
			}
		}
	}

	public List<Key> keys = new ArrayList<Key>();
	
	public List<mButton> buttons = new ArrayList<mButton>();

	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	public Key attack = new Key();
	public Key menu = new Key();

	public Key context = new Key();
	
	public mButton bUP = new mButton(R.id.up, up);
	public mButton bLEFT = new mButton(R.id.left, left);
	public mButton bRIGHT = new mButton(R.id.right, right);
	public mButton bDOWN = new mButton(R.id.down, down);
	public mButton bX = new mButton(R.id.x, menu);
	public mButton bC = new mButton(R.id.c, attack);
	
	
	float canvaswidth = 0;
	private GameCanvas game;
	
	
	public ImageView x;
	public ImageView c;
	public void releaseAll() {
		for (int i = 0; i < keys.size(); i++) {
			keys.get(i).down = false;
		}
	}

	public void tick() {
		for (int i = 0; i < keys.size(); i++) {
			keys.get(i).tick();
		}
	}



	public InputHandler(GameCanvas game, Activity mContext) {
		this.game =  game;
				game.setOnKeyListener(this);
		
		this.mContext = mContext;
		
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).init();
		}

		canvaswidth = game.getWidth();
		
		Logger.v("mine", String.format("canvas width: %f", canvaswidth));
		
	}

	public Activity mContext;
	public void keyPressed(KeyEvent ke) {
		toggle(ke, true);
	}

	public void keyReleased(KeyEvent ke) {
		toggle(ke, false);
	}

	private void toggle(KeyEvent ke, boolean _pressed) {
		
		Logger.v("mine", String.format("Keycode: %d pressed: %s", ke.getKeyCode(), new Boolean(_pressed).toString()));
		
		
		
		
		boolean pressed = !_pressed;
		
		
		
		if (ke.getKeyCode() == KeyEvent.ACTION_UP) 
		{
			up.toggle(pressed);
			
			Logger.v("mine", String.format("up: %d", ke.getKeyCode()));
			return;
		}
		if (ke.getKeyCode() == KeyEvent.ACTION_DOWN) 
		{
			down.toggle(pressed);
			Logger.v("mine", String.format("down: %d", ke.getKeyCode()));
			return;
		}
		
		
		if (ke.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
			left.toggle(pressed);
			
			Logger.v("mine", String.format("left: %d", ke.getKeyCode()));
			return;
		}
		if (ke.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
			Logger.v("mine", String.format("right: %d", ke.getKeyCode()));
			right.toggle(pressed);
			return;
		}
		if (ke.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) 
		{
			up.toggle(pressed);
			Logger.v("mine", String.format("up: %d", ke.getKeyCode()));
			return;
		}
		if (ke.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) 
		{
			down.toggle(pressed);
			Logger.v("mine", String.format("down: %d", ke.getKeyCode()));
			return;
		}
		
		if (ke.getKeyCode() == 23) 
			 {
			menu.toggle(pressed);
			Logger.v("mine", String.format("menu: %d", ke.getKeyCode()));
			return;
			 }
		if (ke.getKeyCode() == 4) {
			Logger.v("mine", String.format("attack: %d", ke.getKeyCode()));
			attack.toggle(pressed);
			return;
		}
		
		if (ke.getKeyCode() == 8) {
			 menu.toggle(pressed);
			Logger.v("mine", String.format("menu: %d", ke.getKeyCode()));
			return;
		}
		if (ke.getKeyCode() == 9) 
		{
			attack.toggle(pressed);
			Logger.v("mine", String.format("attack: %d", ke.getKeyCode()));
			return;
		}
	
		
		if (ke.getKeyCode() == 82)  {
			context.toggle(pressed);
			Logger.v("mine", String.format("down: %d", ke.getKeyCode()));
			return;
		}
		if (ke.getKeyCode() == 100) 
		{
			context.toggle(pressed);
			Logger.v("mine", String.format("down: %d", ke.getKeyCode()));
			return;
		}
	
		if (ke.getKeyCode() == KeyEvent.KEYCODE_MENU)  {
			Logger.v("mine", String.format("context: %d", ke.getKeyCode()));
			context.toggle(pressed);
			return;
		}
		
		
		if (ke.getKeyCode()  == KeyEvent.KEYCODE_I) 
			{
				Logger.v("mine", String.format("up: %d", ke.getKeyCode()));
				up.toggle(pressed);
				return;
			}
		
		if (ke.getKeyCode()  == KeyEvent.KEYCODE_K) 
			{
				Logger.v("mine", "down pressed");
				down.toggle(pressed);
				return;
			}
		
		if (ke.getKeyCode()  == KeyEvent.KEYCODE_J)  {
				left.toggle(pressed);
				return;
		}
		
		if (ke.getKeyCode()  == KeyEvent.KEYCODE_L) {
			right.toggle(pressed);
			return;
		}
		
		if (ke.getKeyCode()  == KeyEvent.KEYCODE_X) {
			menu.toggle(pressed);
			return;
		}
		if (ke.getKeyCode()  == KeyEvent.KEYCODE_C) {
			attack.toggle(pressed);
			return;
		}
		
		

	}

	public void keyTyped(KeyEvent ke) {
	}

	
	
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
	
		Logger.v("mine","onkey");
		
		
		if (event.getKeyCode() ==KeyEvent.KEYCODE_MENU )
		{
			//context.toggle(true);
		}
		
		
		if (event.getAction()  == KeyEvent.ACTION_UP)
		{
			Logger.v("mine", String.format("up %d", keyCode));
			toggle(event, true);
		}
		
		if (event.getAction()  == KeyEvent.ACTION_DOWN)
		{
			Logger.v("mine", String.format("down %d", keyCode));
			toggle(event,false);
		}
		
		
		
			
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
	String TAG="mine";



/*
	@Override
	public void onClick(View v) {
		
		if (v.getId() == R.id.c)
		{
				attack.toggle(true);
			//attack.toggle(false);
		}
		else
		{
			attack.toggle(false);
		}
		if (v.getId() == R.id.x)
		{
		
			menu.toggle(true);
			//menu.toggle(false);
		}
		else
		{
			menu.toggle(false);
		}
		if (v.getId() == R.id.left)
		{
			left.toggle(true);
			//left.toggle(false);
		}
		else
		{
			left.toggle(false);
		}
		
		if (v.getId() == R.id.right)
		{
			right.toggle(true);
			//right.toggle(false);
		}		
		else
		{
			right.toggle(false);
		}
		if (v.getId() == R.id.up)
		{
			up.toggle(true);
			//up.toggle(false);
		}
		else
			
			
		{
			up.toggle(false);
		}
		if (v.getId() == R.id.down)
		{
			down.toggle(true);
			//down.toggle(false);
		}
		else
		{
			down.toggle(false);
		}
		

		
		// TODO Auto-generated method stub
		
	}*/

	public boolean moving;
	
	float lastx;
	float lasty;
	
	float downx;
	float downy;
	
	float pdownx;
	float pdowny;
	
	float plastx;
	float plasty;
	
	int direction;  //0 up 1 right 2 down 3 left

	
	long lastchange=System.currentTimeMillis();
	
	

	/** Show an event in the LogCat view, for debugging */
	private void dumpEvent(MotionEvent event) {
		float curx =   event.getX();
		float cury =   event.getY();
		
	   String names[] = { "DOWN" , "UP" , "MOVE" , "CANCEL" , "OUTSIDE" ,
	      "POINTER_DOWN" , "POINTER_UP" , "7?" , "8?" , "9?" };
	   StringBuilder sb = new StringBuilder();
	   
	   int action = event.getAction();
	   int actionCode = action & MotionEvent.ACTION_MASK;
	   
	   sb.append("event ACTION_" ).append(names[actionCode]);
	   
	   if (actionCode == MotionEvent.ACTION_POINTER_DOWN
	         || actionCode == MotionEvent.ACTION_POINTER_UP) {
	      sb.append("(pid " ).append(
	      action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
	      sb.append(")" );
	   }
	   sb.append("[" );
	   for (int i = 0; i < event.getPointerCount(); i++) {
	      sb.append("#" ).append(i);
	      sb.append("(pid " ).append(event.getPointerId(i));
	      sb.append(")=" ).append((int) event.getX(i));
	      sb.append("," ).append((int) event.getY(i));
	      if (i + 1 < event.getPointerCount())
	         sb.append(";" );
	  	
	   
	      
	      
	   }
	   sb.append("]" );
	   Logger.v("event", sb.toString());
	}
	mButton cButton = null;
	mButton cButton2 = null;
	
	
	mButton leftsidebutton = null;
	mButton rightsidebutton = null;
	
	int mActivePointerId=0;
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
	//	dumpEvent(event);
		

	
		
		
		if (v.getId() == R.id.SurfaceView01)
		{
			
			
			//float curx =   event.getX();
			//float cury = event.getY();
			int action = event.getAction();
			
			switch (action & MotionEvent.ACTION_MASK)
			{
				case MotionEvent.ACTION_DOWN:
				{
					mActivePointerId = event.getPointerId(0);
					downx = event.getX();
					downy = event.getY();
					
					
					//if (game.mTouchDpad)
				//	{
						for (int i = 0; i < buttons.size(); i++) {
						//Logger.v(TAG,"testbutton");
						//	List b = buttons.get(i).in(downx,downy);
							
							if (buttons.get(i).in(downx,downy))
							{
							//Logger.
								
								
								
								buttons.get(i).toggle(true);
								Logger.v("button", String.format("downx:  %f", downx));
								
								if (downx < canvaswidth /2)
								{
									Logger.v("button", "down left");
									leftsidebutton = buttons.get(i);
									//left side
								}
								else
								{		Logger.v("button", "down right");
									//right side
									rightsidebutton = buttons.get(i);
								}
							
								
								
							}
						}
				//	}
				//	
					
					
				}
				break;
				
				case MotionEvent.ACTION_UP: {
				       //mActivePointerId = -1;
					
					
					
					   float curx = event.getX();
					   
						Logger.v("button", String.format("upcurx:  %f canvas: %f", curx,  canvaswidth/2));
						
						
					   if (curx <( canvaswidth/2))
						{
							if(leftsidebutton !=null)
							{
								Logger.v("button", "up left");
								leftsidebutton.toggle(false);
								leftsidebutton = null;
							}
						}
						else
						{
							if (rightsidebutton !=null){
								Logger.v("button", "up right");
								rightsidebutton.toggle(false);
								rightsidebutton = null;	
							}
						}
						
						
				  
				       /*float curx = event.getX();
						float cury = event.getY();
						for (int i = 0; i < buttons.size(); i++) {
							//Logger.v(TAG,"testbutton");
							if (buttons.get(i).in(downx,downy))
							{
								//Logger.
								buttons.get(i).toggle(false);
								//cButton2 = buttons.get(i);
							}
						}*/
				       
				//        break;
				}
				break;
				
				  case MotionEvent.ACTION_POINTER_UP: {
					  
					  final int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) 
				                >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
				      //  final int pointerId = event.getPointerId(pointerIndex);
				        //if (pointerId == mActivePointerId) {
				            // This was our active pointer going up. Choose a new
				            // active pointer and adjust accordingly.
				        //    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
				            
				            float curx = event.getX(pointerIndex);
				            float cury = event.getY(pointerIndex);
				            //mLastTouchX = ev.getX(newPointerIndex);
				            //mLastTouchY = ev.getY(newPointerIndex);
				            mActivePointerId = event.getPointerId(pointerIndex);
				           // Logger.v("mine", String.format("pointer up p: %d x:%f y:%f ", pointerIndex, curx,cury));
				        	Logger.v("button", "pointer up");
				        //    if(cButton !=null)
				          //  {
				           // 	cButton.toggle(false);
				           // }
				            

								if (curx < canvaswidth /2)
								{
									if(leftsidebutton !=null)
									{
										Logger.v("button", "pointer up left");
										leftsidebutton.toggle(false);
										leftsidebutton = null;
									}
								}
								else
								{
									if (rightsidebutton !=null){
										Logger.v("button", "pointer up right");
										rightsidebutton.toggle(false);
										rightsidebutton = null;	
									}
								}
								
				        	/*for (int i = 0; i < buttons.size(); i++) {
								//Logger.v(TAG,"testbutton");
								if (buttons.get(i).in(pdownx,pdowny))
								{
									//Logger.
									buttons.get(i).toggle(false);
									//cButton2 = buttons.get(i);
								}
							}*/
				        	
				            
				       // }
					/*  for (int i = 0; i < event.getPointerCount(); i++) {
						  
						  float curx = event.getX(i);
							float cury = event.getY(i);
							for (int j = 0; j < buttons.size(); i++) {
								//Logger.v(TAG,"testbutton");
								if (buttons.get(j).in(curx,cury))
								{
									//Logger.
									buttons.get(j).toggle(false);
									//cButton2 = buttons.get(i);
								}
							}
					      
					      
					   }*/
				    }
				  break;
				  case MotionEvent.ACTION_POINTER_DOWN: {
					  final int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) 
				                >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
				      //  final int pointerId = event.getPointerId(pointerIndex);
				        //if (pointerId == mActivePointerId) {
				            // This was our active pointer going up. Choose a new
				            // active pointer and adjust accordingly.
				        //    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
				            
				             pdownx = event.getX(pointerIndex);
				             pdowny = event.getY(pointerIndex);
				            //mLastTouchX = ev.getX(newPointerIndex);
				            //mLastTouchY = ev.getY(newPointerIndex);
				            mActivePointerId = event.getPointerId(pointerIndex);
				           Logger.v("button", String.format("pointer down p: %d x:%f y:%f ", pointerIndex, pdownx,pdowny));
				        	for (int i = 0; i < buttons.size(); i++) {
								//Logger.v(TAG,"testbutton");
								if (buttons.get(i).in(pdownx,pdowny))
								{
									//Logger.
									buttons.get(i).toggle(true);
									//cButton = buttons.get(i);
									
									if (pdownx <canvaswidth /2)
									{
										Logger.v("button", "pointer down left");
										leftsidebutton = buttons.get(i);
										//left side
									}
									else
									{
										Logger.v("button", "pointer down right");
										//right side
										rightsidebutton = buttons.get(i);
									}
								
								}
							}
					  	/*for (int i = 0; i < event.getPointerCount(); i++) {
						  
						  float curx = event.getX(i);
							float cury = event.getY(i);
							for (int j = 0; j < buttons.size(); i++) {
								//Logger.v(TAG,"testbutton");
								if (buttons.get(j).in(curx,cury))
								{
									//Logger.
									buttons.get(j).toggle(true);
									//cButton2 = buttons.get(i);
								}
							}
					      
					      
					   }*/
 
				    }
				  break;

				  
			}
			
			
			
		/*	
			int actionCode = action & MotionEvent.ACTION_MASK;
		*/
			int range = 60;
			long curchange =  System.currentTimeMillis();
			
		
			
	/*		if(actionCode == MotionEvent.ACTION_POINTER_UP || actionCode == MotionEvent.ACTION_POINTER_DOWN){
				int  pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK);
				
				
				float curx =   event.getX(pointerIndex);
				float cury = event.getY(pointerIndex);
				boolean t = false;
				if (actionCode ==MotionEvent.ACTION_POINTER_UP)
				{
					t =false;
					if (cButton!=null)
					{
						cButton.toggle(t);
					}
				}

				if (actionCode ==MotionEvent.ACTION_POINTER_DOWN)
				
				{
					//  pdownx = event.getX(1);
					//  pdowny = event.getY(1);
					t =true;
					for (int i = 0; i < buttons.size(); i++) {
						if (buttons.get(i).in(curx,cury))
						{
							buttons.get(i).toggle(t);
							cButton = buttons.get(i);
						}
					}
				}
				
				
				*/
				
			
			// }
			   
		
			 /* if(event.getAction() == MotionEvent.ACTION_DOWN  || event.getAction() == MotionEvent.ACTION_UP){
				//  moving = true;
					int  pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK); 
					float curx =   event.getX(pointerIndex);
					float cury = event.getY(pointerIndex);
					boolean t = false;
					if (actionCode ==MotionEvent.ACTION_UP)
					{
						t =false;
						if (cButton2!=null)
						{
							cButton2.toggle(t);
						}
					}

					if (actionCode ==MotionEvent.ACTION_DOWN)
					
					{
						t =true;
						  downx = event.getX();
						  downy = event.getY();
							for (int i = 0; i < buttons.size(); i++) {
								//Logger.v(TAG,"testbutton");
								if (buttons.get(i).in(curx,cury))
								{
									//Logger.
									buttons.get(i).toggle(t);
									cButton2 = buttons.get(i);
								}
							}
					
					}
					
				
					
			    }
			
			    }*/
		}
			  /*if(event.getAction() == MotionEvent.ACTION_MOVE){
			    	
			    	//Logger.v("mine", String.format("lastchange %d curchange %d diff %d", lastchange,curchange, curchange-lastchange));
			    if(GameCanvas.menu != null)
	    {
			    	if (curchange - lastchange < 200 )
			    		return true;
			    }
				  
				//  if (  curchange - lastchange > 100 && GameCanvas.menu != null)
			    //	{
			    		boolean bright = false;
			    		boolean bleft = false;
			    		boolean bup = false;
			    		boolean bdown = false;
			    		lastchange=System.currentTimeMillis();
			    		
			    		int dup = 0;
			    		int dright = 0;
			    		int ddown = 0;
			    		int dleft =0;
			    		
			    		//ddown = (int) ((int) cury-downy);
			    		
			    		ddown = (int) (cury - downy+range);
			    		dright = (int)(curx - downx+range);
			    		dleft = (int)(curx - downx+range);
			    		dup = (int)(cury -downy+range);
			    		
			    		Logger.v("mine", String.format("down: %d right: %d, left: %d, up: %d", ddown, dright, dleft, dup));
			    		//dright = (int) (curx - downx+range);
			    		//dleft = (int) (curx - downx-range);
			    		//d
			    			//First convert to cartesian coordinates
			    			//int cartX = (int)(curx / movementRadius * movementRange);
			    			//int cartY = (int)(cury / movementRadius * movementRange);
			    			
			    		//	radial = Math.sqrt((cartX*cartX) + (cartY*cartY));
			    		//	double angle = Math.atan2(curx, cury);
			    		//	Logger.v("mine", String.format("angle: %f", angle));
			    			//Invert Y axis if requested
			    		/*	if ( !yAxisInverted )
			    				cartY  *= -1;
			    			
			    			if ( userCoordinateSystem == COORDINATE_CARTESIAN ) {
			    				userX = cartX;
			    				userY = cartY;
			    			}
			    			else if ( userCoordinateSystem == COORDINATE_DIFFERENTIAL ) {
			    				userX = cartY + cartX / 4;
			    				userY = cartY - cartX / 4;
			    				
			    				if ( userX < -movementRange )
			    					userX = (int)-movementRange;
			    				if ( userX > movementRange )
			    					userX = (int)movementRange;

			    				if ( userY < -movementRange )
			    					userY = (int)-movementRange;
			    				if ( userY > movementRange )
			    					userY = (int)movementRange;
			    			}*/
			    		
			    	
			    /*	if (cury > downy + range)
			    	{
			    		bdown = true;
			    		Logger.v("mine", "move down");
			    		//if ()
			    		down.toggle(true);
			    		up.toggle(false);
			    		left.toggle(false);
			    		right.toggle(false);
			    	}
			    	
			    	
			    	
			    	if (curx > downx + range )
			    	{
			    		bright = true;
			    		
			    		Logger.v("mine", "move right");
			    		right.toggle(true);
			    		up.toggle(false);	
			    		down.toggle(false);
			    		left.toggle(false);
			    	
			    	}
			    	if(curx < downx -range)
			    	{
			    		bleft = true;
			    		Logger.v("mine", "move left");
			    		left.toggle(true);
			    		up.toggle(false);
			    		down.toggle(false);
			    		right.toggle(false);
			    	}
			    	
			    	if (cury < downy - range)
			    	{
			    		bup = true;
			    		Logger.v("mine", "move up");
			    		up.toggle(true);
			    		
			    		
			    		down.toggle(false);
			    		
			    		left.toggle(false);
			    		
			    		right.toggle(false);
			    	
			    	}
			    	
			    	if (bup && bright)
			    	{
			    		//moving right and up so figure which is larger 
			    	}
			    	
			    	if (bup && bleft)
			    	{
			    		//moving left and up so figure which is larger
			    	}
			    	
			    	
			    	if (curx < range && cury < range)
			    	{
			    		up.toggle(false);
			    		
			    		down.toggle(false);
			    		
			    		left.toggle(false);
			    		
			    		right.toggle(false);
			    		
			    		//up.toggle(false);
			    		
			    		
			    	}*/
			    //	}*/
			    	
			    	
		//	    }
		//}
			
	
		return true;
	}
	
}
