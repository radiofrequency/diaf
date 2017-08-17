package com.lols.diaf.screen;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Message;

import com.lols.diaf.gfx.Color;
import com.lols.diaf.gfx.Font;
import com.lols.diaf.gfx.Screen;
import com.lols.diaf.sound.Sound;

public class ContextMenu extends Menu {
	private int selected = 0;
	Menu parent;
	Activity mContext;
	private String[] options = {  "New","Save", "Load", "Cheat", "Settings","Exit" };



	public ContextMenu(Menu parent, Activity mContext) {
		this.parent = parent;
		this.mContext = mContext;
		//game.showAd();
			
		/*SharedPreferences prefs = mContext.getPreferences(4);
		//SharedPreferences.Editor editor = prefs.edit();
	
		for (int c=0;c<4; c++)
		{
			String prefix = String.format("game12-%d", c);
			if (prefs.getBoolean(String.format(prefix+"save",c),false) == true)
			{
				options[c] = String.format("Save %d %s",c +1,"SAVED");
			}
			else
			{
				options[c] = String.format("Save %d %s",c+1,"EMPTY");
			}
		}*/
		
		// TODO Auto-generated constructor stub
	}

	public void tick() {
		
		if ( input.menu.clicked) {
			game.setMenu(parent);
		}
		
		//Logger.v("mine", "TitleMenu tick");
		if (input.up.clicked) 
			{
			//Logger.v("mine", "upclicked");
			selected--;
			}
		if (input.down.clicked)
			{
		//	Logger.v("mine", "downclicked");
			selected++;
			}

		int len = options.length;
		if (selected < 0) selected += len;
		if (selected >= len) selected -= len;

		if (input.attack.clicked) {
			
			if (selected ==0)
			{
				game.loadGame(-2);
			}
			if (selected ==1)
			{
				game.setMenu(new SaveMenu(this,mContext));
			}
			if (selected ==2)
			{
				game.setMenu(new LoadMenu(this,mContext));
			}
			
		
			if (selected ==3 )
			{
				game.cheat();
				game.setMenu(null);
				//cheat
			}
			
			if (selected ==4)
			{
				game.setMenu(new SettingsMenu(this, mContext));
			}
			
			if (selected ==5)
			{
				System.exit(1);
			}
				
				//game.setMenu(null);
		//	}
				
			//if (selected == 0) {
			//	Sound.test.play();
			//	game.resetGame();
			//	game.setMenu(null);
			//}
			//if (selected == 1) game.setMenu(new LoadMenu(this));
			//if (selected == 2) game.setMenu(new InstructionsMenu(this));
			//if (selected == 3) game.setMenu(new AboutMenu(this));
		}
		
		if (input.menu.clicked)
		{
			game.setMenu(null);
		}
	}

	public void render(Screen screen) {
		screen.clear(0);

		//int h = 2;
		int h = 2;
		int w = 13;
		//int w = 23;
		int titleColor = Color.get(0, 010, 131, 551);
		int xo = (screen.w - w * 8) / 2;
		int yo = 18;
		//int yo = 0;
		
		//background
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
			//int x=0;	
			//Logger.v("mine", String.format("title %d %d %d",xo + x * 8,yo + y * 8,x + (y + 6) * 32 ));
				screen.render(xo + x * 8, yo + y * 8, x + (y + 6) * 32, titleColor, 0);
		//	screen.render(0,32,224, titleColor, 0);
			}
		}

		
		int yy= 20;
		//menu
		for (int i = 0; i < 6; i++) {
			String msg = options[i];
			int col = Color.get(0, 222, 222, 222);
			if (i == selected) {
				msg = "> " + msg + " <";
				col = Color.get(0, 555, 555, 555);
			}
			Font.draw(msg, screen, (screen.w - msg.length() * 8) / 2, (5 + i) * 8, col);
		}

		//info t bottom
		//Font.draw("(Arrow keys,X and C)", screen, 0, screen.h - 8, Color.get(0, 111, 111, 111));
	}
}
