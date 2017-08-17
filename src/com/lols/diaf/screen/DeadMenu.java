package com.lols.diaf.screen;

import android.app.Activity;

import com.lols.diaf.gfx.Color;
import com.lols.diaf.gfx.Font;
import com.lols.diaf.gfx.Screen;

public class DeadMenu extends Menu {
	private int inputDelay = 60;
	Activity mContext;

	public DeadMenu(Activity mContext) {
		this.mContext = mContext;
	}

	public void tick() {
		if (inputDelay > 0)
			inputDelay--;
		else if (input.attack.clicked ) {
			game.setMenu(new TitleMenu(mContext));
		}
		else if(input.menu.clicked )
		{
			game.respawn();
		}
		
	}

	public void render(Screen screen) {
		String t= "You died! Aww!";
		Font.renderFrame(screen, "", 1, 3, 18, 10);
		Font.draw(t, screen, 2 * 8, 4 * 8, Color.get(-1, 555, 555, 555));

		int seconds = game.gameTime / 60;
		int minutes = seconds / 60;
		int hours = minutes / 60;
		minutes %= 60;
		seconds %= 60;

		StringBuilder sb = new StringBuilder();
		
		//String timeString = "";
		if (hours > 0) {
			sb.append(hours).append("h").append((minutes < 10 ? "0" : "")).append(minutes).append("m");
			//timeString = hours + "h" + (minutes < 10 ? "0" : "") + minutes + "m";
		} else {
			sb.append(minutes).append("m ").append(seconds < 10 ? "0" : "" ).append(seconds).append("s");
			//timeString = minutes + "m " + (seconds < 10 ? "0" : "") + seconds + "s";
		}
		Font.draw("Time:", screen, 2 * 8, 5 * 8, Color.get(-1, 555, 555, 555));
		Font.draw(sb.toString(), screen, (2 + 5) * 8, 5 * 8, Color.get(-1, 550, 550, 550));
		Font.draw("Score:", screen, 2 * 8, 6 * 8, Color.get(-1, 555, 555, 555));
		Font.draw(String.format("%d", game.player.score), screen, (2 + 6) * 8, 6 * 8, Color.get(-1, 550, 550, 550));
		Font.draw("Press C to lose", screen, 2 * 8, 8 * 8, Color.get(-1, 333, 333, 333));
		Font.draw("X to respawn", screen, 2 * 8, 9 * 8, Color.get(-1, 333, 333, 333));
		
	}
}
