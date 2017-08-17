package com.lols.diaf.level.tile;

import com.lols.diaf.entity.AirWizard;
import com.lols.diaf.entity.Entity;
import com.lols.diaf.gfx.Screen;
import com.lols.diaf.level.Level;

public class InfiniteFallTile extends Tile {
	public InfiniteFallTile(int id) {
		super(id);
	}

	public void render(Screen screen, Level level, int x, int y) {
	}

	public void tick(Level level, int xt, int yt) {
	}

	public boolean mayPass(Level level, int x, int y, Entity e) {
		if (e instanceof AirWizard) return true;
		return false;
	}
}
