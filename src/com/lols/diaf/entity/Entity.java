package com.lols.diaf.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Random;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.lols.diaf.Logger;
import com.lols.diaf.gfx.Color;
import com.lols.diaf.gfx.Screen;
import com.lols.diaf.item.Item;
import com.lols.diaf.level.Level;
import com.lols.diaf.level.tile.Tile;

public class Entity implements Serializable   {
	/**
	 * 
	 */
	//private static final long serialVersionUID = 5791492904375424151L;
	public transient  Random random = new Random();
	public int x, y;
	public int xr = 6;
	public int yr = 6;
	public boolean removed;
	transient public Level level;
	public boolean burning = false;
	public boolean canburn = true;
	private int time=0;
	public static int id;
	
	

	
//	@Override
	 public static Object deserializeObject(byte[] b) { 
		    try { 
		      ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(b)); 
		      Entity object =(Entity) in.readObject(); 
		      in.close(); 
		 
		      return object; 
		    } catch(ClassNotFoundException cnfe) { 
		      //Logger.e("deserializeObject", "class not found error", cnfe); 
		 
		      return null; 
		    } catch(IOException ioe) { 
		      ///Logger.e("deserializeObject", "io error", ioe); 
		 
		      return null; 
		    } 
		  } 

	 
	  

	  public static byte[] serializeObject(Entity o) { 
	    ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
	 
	    try { 
	      ObjectOutput out = new ObjectOutputStream(bos); 
	      out.writeObject(o); 
	      out.close(); 
	 
	      // Get the bytes of the serialized object 
	      byte[] buf = bos.toByteArray(); 
	 
	      return buf; 
	    } catch(IOException ioe) { 
	     // Logger.e("serializeObject", "error", ioe); 
	 
	      return null; 
	    } 
	  } 
	  
	public void loadEntity(Bundle prefs, int i, int c2) {
		// TODO Auto-generated method stub
		String prefix = String.format("entity-%d-%d", i, c2);
		
		x = prefs.getInt(prefix + "x", x);
		y = prefs.getInt(prefix+"y", y);
		xr = prefs.getInt(prefix+"xr", xr);
		yr = prefs.getInt(prefix+ "yr", yr);
		removed = prefs.getBoolean(prefix + "removed", removed);
		//level = 
		
	}
	
	/*private List<Entity> loadentities(Bundle prefs, int c) {
		// TODO Auto-generated method stub
		int c2 = 0;
		String prefix = String.format("entity-%d-%d", c, c2);
		while(prefs.getInt(prefix+"x", -1)!= -1)
		{
			c2++;
			Entity e = new Entity();
			e.loadEntity(prefs,  c, c2);
			
			
		}
	
		return null;
	}*/
	public void saveEntity(SharedPreferences.Editor prefs, int i, int i2) {
		//public void onSaveInstanceState(Bundle savedInstanceState) {
			  // Save UI state changes to the savedInstanceState.
			  // This bundle will be passed to onCreate if the process is
			  // killed and restarted.
		//prefs.putBoolean("playerDeadTime", true);
		
		String prefix = String.format("entity-%d-%d", i, i2);
		//prefs.putInt(prefix + "level", );
		prefs.putInt(prefix + "x", x);
		prefs.putInt(prefix + "y", y);
		prefs.putInt(prefix + "xr", xr);
		prefs.putInt(prefix + "yr", yr);
		prefs.putBoolean(prefix + "removed", removed);
		Logger.v("mine", String.format("entity %s",this.toString()));
		byte [] object = Entity.serializeObject(this);
		Logger.v("mine", String.format("entity size: %d %s", object.length, this.toString()));
		//prefs.putByteArray("level", tiles);
		//prefs.putByteArray("data", data);
		
		
	}
	
public void render(Screen screen) {
		
		if (burning)
		{
			
			//System.out.println(String.format("time: %d", time));
			//if (time / 6 % 10 != 0) {
				int firecol = Color.get(-1,555,500,490);
				int bxt = 0;
				
				if (time > 30)
				{
					//bxt += random.nextInt(2);
					//time = 5;
					bxt=1;
					time=0;
					//hurt(this,1,1);
					//doHurt(1,1);
				}
				

				int xo = x - 8;
				int yo = y - 11;
				//System.out.println(String.format("burning %d %d", xo, yo));
				time++;
				int byt = 11 ;
				screen.render(xo+1, yo, bxt+byt*32, firecol, 1);
				screen.render(xo+6, yo, bxt+byt*32, firecol, 1);
				screen.render(xo+5, yo+5, bxt+byt*32, firecol, 1);
				screen.render(xo+2, yo+9, bxt+byt*32, firecol, 1);
				
				
				
		//	}
			
		//	x = 1
			//screen.render()
			
		}
	}
	public void tick() {
	}
	
	public void burn( int dmg)
	{
		if (canburn)
			burning = true;
	}

	public void remove() {
		removed = true;
	}

	public final void init(Level level) {
		this.level = level;
	}

	public boolean intersects(int x0, int y0, int x1, int y1) {
		return !(x + xr < x0 || y + yr < y0 || x - xr > x1 || y - yr > y1);
	}

	public boolean blocks(Entity e) {
		return false;
	}

	public void hurt(Mob mob, int dmg, int attackDir) {
	}

	public void hurt(Tile tile, int x, int y, int dmg) {
	}

	public boolean move(int xa, int ya) {
		if (xa != 0 || ya != 0) {
			boolean stopped = true;
			if (xa != 0 && move2(xa, 0)) stopped = false;
			if (ya != 0 && move2(0, ya)) stopped = false;
			if (!stopped) {
				int xt = x >> 4;
				int yt = y >> 4;
				level.getTile(xt, yt).steppedOn(level, xt, yt, this);
			}
			return !stopped;
		}
		return true;
	}

	protected boolean move2(int xa, int ya) {
		if (xa != 0 && ya != 0) throw new IllegalArgumentException("Move2 can only move along one axis at a time!");

		int xto0 = ((x) - xr) >> 4;
		int yto0 = ((y) - yr) >> 4;
		int xto1 = ((x) + xr) >> 4;
		int yto1 = ((y) + yr) >> 4;

		int xt0 = ((x + xa) - xr) >> 4;
		int yt0 = ((y + ya) - yr) >> 4;
		int xt1 = ((x + xa) + xr) >> 4;
		int yt1 = ((y + ya) + yr) >> 4;
		boolean blocked = false;
		for (int yt = yt0; yt <= yt1; yt++)
			for (int xt = xt0; xt <= xt1; xt++) {
				if (xt >= xto0 && xt <= xto1 && yt >= yto0 && yt <= yto1) continue;
				level.getTile(xt, yt).bumpedInto(level, xt, yt, this);
				if (!level.getTile(xt, yt).mayPass(level, xt, yt, this)) {
					blocked = true;
					return false;
				}
			}
		if (blocked) return false;

		List<Entity> wasInside = level.getEntities(x - xr, y - yr, x + xr, y + yr);
		List<Entity> isInside = level.getEntities(x + xa - xr, y + ya - yr, x + xa + xr, y + ya + yr);
		for (int i = 0; i < isInside.size(); i++) {
			Entity e = isInside.get(i);
			if (e == this) continue;

			e.touchedBy(this);
		}
		isInside.removeAll(wasInside);
		for (int i = 0; i < isInside.size(); i++) {
			Entity e = isInside.get(i);
			if (e == this) continue;

			if (e.blocks(this)) {
				return false;
			}
		}

		x += xa;
		y += ya;
		return true;
	}

	protected void touchedBy(Entity entity) {
		if (burning)
		{
			entity.burning = true;
		}
	}

	public boolean isBlockableBy(Mob mob) {
		return true;
	}

	public void touchItem(ItemEntity itemEntity) {
	}

	public boolean canSwim() {
		return false;
	}

	public boolean interact(Player player, Item item, int attackDir) {
		return item.interact(player, this, attackDir);
	}

	public boolean use(Player player, int attackDir) {
		return false;
	}

	public int getLightRadius() {
		if (burning)
		{
			return 6;
		}
		return 0;
	}

	
}