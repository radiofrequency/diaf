package com.lols.diaf.screen;

import android.app.Activity;
import android.util.Log;

import com.lols.diaf.gfx.Color;
import com.lols.diaf.gfx.Font;
import com.lols.diaf.gfx.Screen;
import com.lols.diaf.sound.Sound;

public class TitleMenu extends Menu {
	private int selected = 0;
	private Activity mContext;

	private static final String[] options = { "Start game", "Load game", "Settings" };

	//public TitleMenu() {
	//}

	public TitleMenu(Activity mContext) {
		this.mContext = mContext;
		// TODO Auto-generated constructor stub
	}

	public void tick() {
		//Logger.v("mine", "TitleMenu tick");
		if (input!=null)
		{
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
		}
		int len = options.length;
		if (selected < 0) selected += len;
		if (selected >= len) selected -= len;

		if (input.attack.clicked || input.menu.clicked) {
			if (selected == 0) {
				Sound.test.play();
				//game.resetGame();
				game.loadGame(-2);
				//game.resetGame();
				//game.setMenu(null);
			}
			if (selected == 1) game.setMenu(new LoadMenu(this, mContext));
			//if (selected == 3) game.setMenu(new InstructionsMenu(this));
			if (selected == 2) game.setMenu(new SettingsMenu(this, mContext));
		//	if (selected == 4) game.setMenu(new AboutMenu(this));
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
		for (int i = 0; i < 3; i++) {
			String msg = options[i];
			int col = Color.get(0, 222, 222, 222);
			if (i == selected) {
				msg = "> " + msg + " <";
				col = Color.get(0, 555, 555, 555);
			}
			Font.draw(msg, screen, (screen.w - msg.length() * 8) / 2, (8 + i) * 8, col);
		}

		//info t bottom
		Font.draw("(Arrow keys,X and C)", screen, 0, screen.h - 8, Color.get(0, 111, 111, 111));
	}
}