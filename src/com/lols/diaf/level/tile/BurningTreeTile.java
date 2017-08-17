package com.lols.diaf.level.tile;


//import com.lols.diaf.entity.Entity;
import com.lols.diaf.entity.Entity;
import com.lols.diaf.entity.ItemEntity;
import com.lols.diaf.entity.Mob;
import com.lols.diaf.entity.Player;
import com.lols.diaf.entity.particle.SmashParticle;
import com.lols.diaf.entity.particle.TextParticle;
import com.lols.diaf.gfx.Color;
import com.lols.diaf.gfx.Screen;
import com.lols.diaf.item.Item;
import com.lols.diaf.item.ResourceItem;
import com.lols.diaf.item.ToolItem;
import com.lols.diaf.item.ToolType;
import com.lols.diaf.item.resource.Resource;
import com.lols.diaf.level.Level;

public class BurningTreeTile extends Tile {
	private int time =0;
	
	public BurningTreeTile(int id) {
		super(id);
		connectsToGrass = true;
		
	}

	public void render(Screen screen, Level level, int x, int y) {
		int col = Color.get(10, 30, 151, level.grassColor);
		int barkCol1 = Color.get(10, 30, 430, level.grassColor);
		int barkCol2 = Color.get(10, 30, 320, level.grassColor);

		boolean u = level.getTile(x, y - 1) == this;
		boolean l = level.getTile(x - 1, y) == this;
		boolean r = level.getTile(x + 1, y) == this;
		boolean d = level.getTile(x, y + 1) == this;
		boolean ul = level.getTile(x - 1, y - 1) == this;
		boolean ur = level.getTile(x + 1, y - 1) == this;
		boolean dl = level.getTile(x - 1, y + 1) == this;
		boolean dr = level.getTile(x + 1, y + 1) == this;

		if (u && ul && l) {
			screen.render(x * 16 + 0, y * 16 + 0, 10 + 1 * 32, col, 0);
		} else {
			screen.render(x * 16 + 0, y * 16 + 0, 9 + 0 * 32, col, 0);
		}
		if (u && ur && r) {
			screen.render(x * 16 + 8, y * 16 + 0, 10 + 2 * 32, barkCol2, 0);
		} else {
			screen.render(x * 16 + 8, y * 16 + 0, 10 + 0 * 32, col, 0);
		}
		if (d && dl && l) {
			screen.render(x * 16 + 0, y * 16 + 8, 10 + 2 * 32, barkCol2, 0);
		} else {
			screen.render(x * 16 + 0, y * 16 + 8, 9 + 1 * 32, barkCol1, 0);
		}
		if (d && dr && r) {
			screen.render(x * 16 + 8, y * 16 + 8, 10 + 1 * 32, col, 0);
		} else {
			screen.render(x * 16 + 8, y * 16 + 8, 10 + 3 * 32, barkCol2, 0);
		}
	
				
				//System.out.println(String.format("time: %d x: %d y: %d", time, x, y));
				//if (time / 6 % 10 != 0) {
					int firecol = Color.get(-1,555,500,490);
					int bxt = 0;
					
					if (time > 30)
					{
						//bxt += random.nextInt(2);
						//time = 5;
						bxt=1;
						time=0;
						//doHurt(1,1);
					}
					

					int xo = x;
					int yo = y;
					//System.out.println(String.format("burning %d %d", xo, yo));
					time++;
					int byt = 11 ;
					screen.render(xo*16+1, yo*26, bxt+byt*32, firecol, 1);
					screen.render(xo*16+6, yo*16, bxt+byt*32, firecol, 1);
					screen.render(xo*16+5, yo*16+5, bxt+byt*32, firecol, 1);
					screen.render(xo*16+2, yo*16+9, bxt+byt*32, firecol, 1);

	}

	public void tick(Level level, int xt, int yt) {
		//int damage = level.getData(xt, yt);
		//if (damage > 0) level.setData(xt, yt, damage - 1);
		
		int xn = xt;
		int yn = yt;

		if (random.nextBoolean())
			xn += random.nextInt(2) * 2 - 1;
		else
			yn += random.nextInt(2) * 2 - 1;

		if (level.getTile(xn, yn) == Tile.tree) {
			level.setTile(xn, yn, this, 0);
		}
		
		
		int age = level.getData(xt, yt) + 1;
		//System.out.println(String.format("age: %d ",age));
		if (age > 60) {
			level.setTile(xt, yt, Tile.dirt, 0);
			
		} else {
			level.setData(xt, yt, age);
		}
	}

	public boolean mayPass(Level level, int x, int y, Entity e) {
		return false;
	}

	public void hurt(Level level, int x, int y, Mob source, int dmg, int attackDir) {
		hurt(level, x, y, dmg);
	}

	public boolean interact(Level level, int xt, int yt, Player player, Item item, int attackDir) {
		if (item instanceof ToolItem) {
			ToolItem tool = (ToolItem) item;
			if (tool.type == ToolType.axe) {
				if (player.payStamina(4 - tool.level)) {
					
					hurt(level, xt, yt, random.nextInt(10) + (tool.level) * 5 + 10);
					return true;
				}
			}
		}
		return false;
	}
	public void bumpedInto(Level level, int x, int y, Entity entity) {
		//entity.hurt(this, x, y, 3);
		entity.burn(1);
	}
	private void hurt(Level level, int x, int y, int dmg) {
	/*	{
			int count = random.nextInt(10) == 0 ? 1 : 0;
			for (int i = 0; i < count; i++) {
				level.add(new ItemEntity(new ResourceItem(Resource.apple), x * 16 + random.nextInt(10) + 3, y * 16 + random.nextInt(10) + 3));
			}
		}
		int damage = level.getData(x, y) + dmg;
		level.add(new SmashParticle(x * 16 + 8, y * 16 + 8));
		level.add(new TextParticle("" + dmg, x * 16 + 8, y * 16 + 8, Color.get(-1, 500, 500, 500)));
		if (damage >= 20) {
			int count = random.nextInt(2) + 1;
			for (int i = 0; i < count; i++) {
				level.add(new ItemEntity(new ResourceItem(Resource.wood), x * 16 + random.nextInt(10) + 3, y * 16 + random.nextInt(10) + 3));
			}
			count = random.nextInt(random.nextInt(4) + 1);
			for (int i = 0; i < count; i++) {
				level.add(new ItemEntity(new ResourceItem(Resource.acorn), x * 16 + random.nextInt(10) + 3, y * 16 + random.nextInt(10) + 3));
			}
			level.setTile(x, y, Tile.grass, 0);
		} else {
			level.setData(x, y, damage);
		}*/
	}
	
	public int getLightRadius(Level level, int x, int y) {
		return 4;
	}
}
