package com.lols.diaf.level.tile;

import com.lols.diaf.entity.Entity;
import com.lols.diaf.gfx.Color;
import com.lols.diaf.gfx.Screen;
import com.lols.diaf.level.Level;

public class StoneTile extends Tile {
	public StoneTile(int id) {
		super(id);
	}

	public void render(Screen screen, Level level, int x, int y) {
		int rc1 = 111;
		int rc2 = 333;
		int rc3 = 555;
		screen.render(x * 16 + 0, y * 16 + 0, 32, Color.get(rc1, level.dirtColor, rc2, rc3), 0);
		screen.render(x * 16 + 8, y * 16 + 0, 32, Color.get(rc1, level.dirtColor, rc2, rc3), 0);
		screen.render(x * 16 + 0, y * 16 + 8, 32, Color.get(rc1, level.dirtColor, rc2, rc3), 0);
		screen.render(x * 16 + 8, y * 16 + 8, 32, Color.get(rc1, level.dirtColor, rc2, rc3), 0);
	}

	public boolean mayPass(Level level, int x, int y, Entity e) {
		return false;
	}

}
