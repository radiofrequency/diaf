package com.lols.diaf.item;

import java.io.Serializable;

import android.util.Log;

import com.lols.diaf.Logger;
import com.lols.diaf.entity.Entity;
import com.lols.diaf.entity.ItemEntity;
import com.lols.diaf.entity.Player;
import com.lols.diaf.gfx.Screen;
import com.lols.diaf.level.Level;
import com.lols.diaf.level.tile.Tile;
import com.lols.diaf.screen.ListItem;

public class Item implements ListItem, Serializable {
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;

	public int getColor() {
		return 0;
	}

	public int getSprite() {
		return 0;
	}

	public void onTake(ItemEntity itemEntity) {
	}

	public void renderInventory(Screen screen, int x, int y) {
	}

	public boolean interact(Player player, Entity entity, int attackDir) {
		return false;
	}

	public void renderIcon(Screen screen, int x, int y) {
	}

	public boolean interactOn(Tile tile, Level level, int xt, int yt, Player player, int attackDir) {
		return false;
	}
	
	public boolean isDepleted() {
		return false;
	}

	public boolean canAttack() {
		return false;
	}

	public int getAttackDamageBonus(Entity e) {
		Logger.v("mine", "item attack bonus");
		return 0;
	}

	public String getName() {
		return "";
	}

	public boolean matches(Item item) {
		return item.getClass() == getClass();
	}
}