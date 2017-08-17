package com.lols.diaf.screen;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Message;

import com.lols.diaf.gfx.Color;
import com.lols.diaf.gfx.Font;
import com.lols.diaf.gfx.Screen;
import com.lols.diaf.sound.Sound;

public class SettingsMenu extends Menu {
	private int selected = 0;
	Menu parent;
	Activity mContext;
	private String[] options = { "DPAD", "BUTTONS" };
	private String[] options2 = { "DPAD", "BUTTONS" };

	//public SettingsMenu() {
//	}

	public SettingsMenu(Menu parent, Activity mContext) {
		this.parent = parent;
		this.mContext = mContext;
		
		SharedPreferences prefs = mContext.getPreferences(4);
		//SharedPreferences.Editor editor = prefs.edit();
	
		
		for (int c=0;c<2; c++)
		{
			String prefix = String.format("game12-%d", c);
		
			String setting = "YES";
			if (prefs.getBoolean(String.format(prefix+"setting-%d",c),true) == false)
				setting = "NO";
			
			//else
		//	{
			
			options[c] = String.format("%s %s",options[c],setting);
		//	}
/*			else
			{
				options[c] = String.format("Save %d %s",c+1,"EMPTY");
			}*/
		}
		
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

		if (input.attack.clicked ) {
			
			SharedPreferences prefs = mContext.getPreferences(4);
			SharedPreferences.Editor editor = prefs.edit();
			String prefix = String.format("game12-%d", selected);
			if (prefs.getBoolean(String.format(prefix+"setting-%d",selected),false) == true)
			{
				editor.putBoolean(String.format(prefix+"setting-%d", selected), false);
				options[selected] = String.format("%s %s",options2[selected], "NO");
				if (selected == 0)
				{
					game.hidedpad();
				}
				else
					game.hidebuttons();
				
				
			}
			else
			{
				editor.putBoolean(String.format(prefix+"setting-%d", selected), true);
				options[selected] = String.format("%s %s",options2[selected], "YES");
				
				if (selected == 0)
				{
					game.showdpad();
				}
				else
					game.showbuttons();

			}
			editor.commit();

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
		int yo = 24;
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

		
		//menu
		for (int i = 0; i < 2; i++) {
			String msg = options[i];
			int col = Color.get(0, 222, 222, 222);
			if (i == selected) {
				msg = "> " + msg + " <";
				col = Color.get(0, 555, 555, 555);
			}
			Font.draw(msg, screen, (screen.w - msg.length() * 8) / 2, (8 + i) * 8, col);
		}

		//info t bottom
		//Font.draw("(Arrow keys,X and C)", screen, 0, screen.h - 8, Color.get(0, 111, 111, 111));
	}
}
