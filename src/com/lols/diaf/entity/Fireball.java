package com.lols.diaf.entity;

import java.util.List;

import com.lols.diaf.Logger;
import  com.lols.diaf.gfx.Color;
import com.lols.diaf.gfx.Screen;
import  com.lols.diaf.level.Level;
import  com.lols.diaf.level.tile.Tile;
import  com.lols.diaf.level.tile.TreeTile;

public class Fireball extends Entity {
	private int lifeTime;
	public double xa, ya;
	public double xx, yy;
	private int time;
	private Player owner;

	public Fireball(Player owner, double xa, double ya) {
		this.owner = owner;
		xx = this.x = owner.x;
		yy = this.y = owner.y;
		xr = 0;
		yr = 0;

		this.xa = xa;
		this.ya = ya;

		lifeTime = 60 * 10 + random.nextInt(30);
	}

	public void tick() {
		time++;
		if (time >= lifeTime) {
			remove();
			return;
		}
		xx += xa;
		yy += ya;
		x = (int) xx;
		y = (int) yy;
		
		Boolean remove = false;
		int xt = x >> 4;
		int yt = (y + -2) >> 4;
		
		Tile t = level.getTile(xt,yt);
		//Tile t = level.getTile(x,y);
		
	//	level.getTile(`, lifeTime)
		//t.hurt(owner.level,xt,yt, owner, 100, 1);
		if (t == Tile.tree)
		{
			//System.out.println("tree %d, %d");
			//t.burn();
			level.setTile(xt, yt, Tile.burningTree, 50);
			remove = true;
				
		}
		//System.out.println(String.format("%s %d %d", t.getClass().toString(), x ,y));
		
		List<Entity> toHit = level.getEntities(x, y, x+5, y+5);
		for (int i = 0; i < toHit.size(); i++) {
			
			Entity e = toHit.get(i);
		
			if (!(e instanceof Player))
			{
	
				/*if (e.canburn)
				{
						//e.hurt(owner, 1, ((Mob) e).dir ^ 1);
					e.burn(1);
					remove = true;
					remove();
				
				}*/
			}
			//System.out.println(e.getClass().toString());
			if (e instanceof Mob && !(e instanceof Player)) {
			//	System.out.println("hit");
				
				e.burn(1);
				remove = true;
				
			}
		//	Tile f = level.getTile(x, y);
			
			
			//if (e == ItemEntity.tr)
		//	{
			//	System.out.println("rock");
			//}
			//if (f == Tile.tree)
		//	{
			//	System.out.println("tree");
				
			//	f.burn();
		//		remove();
		//	}
			//if (e instanceof TreeTile)
		//	{
			//	e.burn();
				
			//}
		//	List<ItemEntity> toHitItem = level.get
		}
		
		if (remove )
		{
			remove();
		}
		
	}

	public boolean isBlockableBy(Mob mob) {
		return false;
	}

	public void render(Screen screen) {
		if (time >= lifeTime - 6 * 20) {
			if (time / 6 % 2 == 0) return;
		}

		int xt = 8;
		int yt = 13;

		
		 int firecol = Color.get(-1,555,500,490);
		
		screen.render(x - 4, y - 4 - 2, xt + yt * 32, firecol, random.nextInt(4));
		screen.render(x - 4, y - 4 + 2, xt + yt * 32, Color.get(-1, 000, 000, 000), random.nextInt(4));
	}
	
	
	public int getLightRadius() {
		return 2;
	}
}
