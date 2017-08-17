package com.lols.diaf.entity;
import com.lols.diaf.gfx.Color;

public class Torch extends Furniture {
	public Torch() {
		super("Torch");
		col = Color.get(-1, 000, 111, 555);
		sprite = 7;
		xr = 3;
		yr = 2;
		canburn =false;
	}

	public int getLightRadius() {
		return 8;
	}
}