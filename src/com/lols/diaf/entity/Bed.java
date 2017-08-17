package com.lols.diaf.entity;

import com.lols.diaf.gfx.Color;
import com.lols.diaf.screen.ContainerMenu;

public class Bed extends Furniture {
	public Inventory inventory = new Inventory();

	public Bed() {
		super("Bed");
		col = Color.get(-1, 110, 000, 555);
		sprite = 6;
	}

	public boolean use(Player player, int attackDir) {
		//player.game.setMenu(new ContainerMenu(player, "Chest", inventory));
		
		player.game.sleep();
		return true;
	}
}