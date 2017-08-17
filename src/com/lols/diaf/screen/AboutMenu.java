package com.lols.diaf.screen;

import com.lols.diaf.gfx.Color;
import com.lols.diaf.gfx.Font;
import com.lols.diaf.gfx.Screen;

public class AboutMenu extends Menu {
	private Menu parent;

	public AboutMenu(Menu parent) {
		this.parent = parent;
	}

	public void tick() {
		if (input.attack.clicked || input.menu.clicked) {
			game.setMenu(parent);
		}
	}

	public void render(Screen screen) {
		screen.clear(0);

		Font.draw("About DIAF", screen, 2 * 8 + 4, 1 * 8, Color.get(0, 555, 555, 555));
		
	}
}
