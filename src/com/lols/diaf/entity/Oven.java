package com.lols.diaf.entity;

import com.lols.diaf.crafting.Crafting;
import com.lols.diaf.gfx.Color;
import com.lols.diaf.screen.CraftingMenu;

public class Oven extends Furniture {
	public Oven() {
		super("Oven");
		col = Color.get(-1, 000, 332, 442);
		sprite = 2;
		xr = 3;
		yr = 2;
		canburn = true;
	}

	public boolean use(Player player, int attackDir) {
		player.game.setMenu(new CraftingMenu(Crafting.ovenRecipes, player));
		return true;
	}
}