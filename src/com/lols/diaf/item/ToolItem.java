package com.lols.diaf.item;

import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

import android.util.Log;

import com.lols.diaf.Logger;
import com.lols.diaf.entity.Entity;
import com.lols.diaf.entity.ItemEntity;
import com.lols.diaf.gfx.Color;
import com.lols.diaf.gfx.Font;
import com.lols.diaf.gfx.Screen;

public class ToolItem extends Item  implements Serializable{
	/**
	 * 
	 */
	//private static final long serialVersionUID = -2969068580655985653L;

	private transient Random random= null;// = new Random();

	public static final int MAX_LEVEL = 5;
	public static final String[] LEVEL_NAMES = { //
	"Wood", "Rock", "Iron", "Gold", "Gem"//
	};


	/*private void readObject(java.io.ObjectInputStream in)
		     throws IOException, ClassNotFoundException {
		//super.readObject();
		random = new Random();
	}*/
	
	public static final int[] LEVEL_COLORS = {//
	Color.get(-1, 100, 321, 431),//
			Color.get(-1, 100, 321, 111),//
			Color.get(-1, 100, 321, 555),//
			Color.get(-1, 100, 321, 550),//
			Color.get(-1, 100, 321, 055),//
	};

	public ToolType type;
	public int level;

	public ToolItem(ToolType type, int level) {
		this.type = type;
		this.level = level;
		//random = new Random();
	}

	public int getColor() {
		return LEVEL_COLORS[level];
	}

	public int getSprite() {
		return type.sprite + 5 * 32;
	}

	public void renderIcon(Screen screen, int x, int y) {
		screen.render(x, y, getSprite(), getColor(), 0);
	}

	public void renderInventory(Screen screen, int x, int y) {
		screen.render(x, y, getSprite(), getColor(), 0);
		Font.draw(getName(), screen, x + 8, y, Color.get(-1, 555, 555, 555));
	}

	public String getName() {
		return LEVEL_NAMES[level] + " " + type.name;
	}

	public void onTake(ItemEntity itemEntity) {
	}

	public boolean canAttack() {
		return true;
	}

	public int getAttackDamageBonus(Entity e) {
		Logger.v("mine", String.format("%s level %d" , type.name,level));
		if (random == null)
		{
			random = new Random();
		}
		
	
		if (type.name.equalsIgnoreCase("Axe")) {
			int randomdmg = random.nextInt(4);
			Logger.v("mine", String.format("axe %d %d" , randomdmg, (level + 1) * 2 + randomdmg) );
		
			return (level + 1) * 2 + randomdmg;
		}
		if (type.name.equalsIgnoreCase("Swrd")) {
			Logger.v("sword", String.format("sword" ));
			return (level + 1) * 3 + random.nextInt(2 + level * level * 2);
		}
		return 1;
	}

	public boolean matches(Item item) {
		if (item instanceof ToolItem) {
			ToolItem other = (ToolItem) item;
			if (other.type != type) return false;
			if (other.level != level) return false;
			return true;
		}
		return false;
	}
}