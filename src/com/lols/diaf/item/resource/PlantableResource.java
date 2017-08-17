package com.lols.diaf.item.resource;

import java.util.Arrays;
import java.util.List;

import com.lols.diaf.Logger;
import com.lols.diaf.entity.Player;
import com.lols.diaf.level.Level;
import com.lols.diaf.level.tile.Tile;

public class PlantableResource extends Resource {
	private List<Tile> sourceTiles;
	private Tile targetTile;

	public PlantableResource(String name, int sprite, int color, Tile targetTile, Tile... sourceTiles1) {
		this(name, sprite, color, targetTile, Arrays.asList(sourceTiles1));
	}

	public PlantableResource(String name, int sprite, int color, Tile targetTile, List<Tile> sourceTiles) {
		super(name, sprite, color);
		this.sourceTiles = sourceTiles;
		this.targetTile = targetTile;
	}

	public boolean interactOn(Tile tile, Level level, int xt, int yt, Player player, int attackDir) {
		Logger.v("mine", "interactOn");
		
		for (Tile t : sourceTiles)
		{
			Logger.v("mine", t.getClass().getName());
			Logger.v("mine", tile.getClass().getName());
			if(t.getClass().getName().equalsIgnoreCase(tile.getClass().getName()))
			//if (t.getClass().getName().equals(tile))
			{
				level.setTile(xt, yt, targetTile, 0);
				Logger.v("mine", "equals");
				return true;
			}
		}
		if (sourceTiles.contains(tile)) {
			Logger.v("mine", "setTile");
			level.setTile(xt, yt, targetTile, 0);
			return true;
		}
		return false;
	}
}
