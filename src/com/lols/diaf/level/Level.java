package com.lols.diaf.level;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.lols.diaf.GameCanvas;
import com.lols.diaf.InputHandler;
import com.lols.diaf.Logger;
import com.lols.diaf.entity.AirWizard;
import com.lols.diaf.entity.Bed;
import com.lols.diaf.entity.Cow;
import com.lols.diaf.entity.Creeper;
import com.lols.diaf.entity.Entity;
import com.lols.diaf.entity.Goose;
import com.lols.diaf.entity.Mob;
import com.lols.diaf.entity.Pig;
import com.lols.diaf.entity.Player;
import com.lols.diaf.entity.Slime;
import com.lols.diaf.entity.Zombie;
import com.lols.diaf.gfx.Screen;
import com.lols.diaf.level.levelgen.LevelGen;
import com.lols.diaf.level.tile.Tile;

public class Level implements Serializable {
	/**
	 * 
	 */
//	private static final long serialVersionUID = -7923681260583094102L;

	transient private Random random = new Random();

	public int w, h;

	public byte[] tiles;
	public byte[] data;
	public List<Entity>[] entitiesInTiles;

	public int grassColor = 141;
	public int dirtColor = 322;
	public int sandColor = 550;
	private int depth;
	public int monsterDensity = 8;

	public List<Entity> entities = new ArrayList<Entity>();
	transient private Comparator<Entity> spriteSorter = new Comparator<Entity>() {
		public int compare(Entity e0, Entity e1) {
			if (e1.y < e0.y) return +1;
			if (e1.y > e0.y) return -1;
			return 0;
		}

	};

	
	public Bed findBed() 
	{
		for (Entity e : entities)
		{
			if (e instanceof Bed)
				return (Bed)e;
		}
		return null;
	}
	public Player findPlayer()
	{
	    for(Entity e : entities)
	      {
	    	  //level  = object;
	    	  //e.random = new Random();
	    	  
	    	  if (e instanceof Player)
	    	  {
	    		//  Logger.v("mine", "found player, removing");
	    		  //object.entities.remove(e);
	    		  //((Player)e).input = input;
	    		  //((Player)e).game = game;
	    		  //((Player)e).input = 
	    		  return (Player)e;
	    	  }
	      }
	    return null;
	}
	
	//private void saveLevel()
	public Level loadLevel(SharedPreferences prefs) {
		
		String prefix = String.format("level-%d", depth);
		
		Logger.v("mine", "load level");
		depth = prefs.getInt(prefix + "depth", 0);
		w = prefs.getInt(prefix + "w", 0);
		h = prefs.getInt(prefix + "h", 0);
		
		dirtColor = prefs.getInt(prefix + "dirtcolor", 0);
		monsterDensity = prefs.getInt(prefix + "monsterDensity", 0);
		
		
	//	tiles = prefs.getByteArray(prefix + "tiles");
	//	data = prefs.getByteArray(prefix + "data");
		//for (int i = 0; i < w * h; i++) {
		//	((Entity)entitiesInTiles[i]).saveEntity(prefs);
	//	}
		//entitiesInTiles[i] = new ArrayList<Entity>();
		
		//for (int i = 0; i < w * h; i++) {
			//entityInTiles[i]
		//	entitiesInTiles[i] = new ArrayList<Entity>();
			
			
			
			//int ec = 0;
			
			//entitiesInTiles[i] = loadentities();
			
			//int c2 = 0;
			//String prefix2 = String.format("entity-%d-%d", i, c2);
			//while(prefs.getInt(prefix2+"x", -1)!= -1)
			//{
				
				//Entity e = new Entity();
				//e.loadEntity(prefs,  i, c2);
				
				//c2++;
				//entitiesInTiles[i].add(e);
				
			//}
			
			//while(Entity e = Entity.loadEntities(i))
			//{
			//	entitiesInTiles[i].add(e);
			//			//Entity t = new Entity();
			
			//while (t.loadEntity, i))
			//if (t.loadEntities(prefs, i, ec))
			//	entitiesInTiles[i].add(t);
			
			//ec++;
			//entitiesInTitle[i].add( Entity.loadEntity(prefs));
			
			//for (Entity x : entitiesInTiles[i])
			//{
				//Logger.v("mine", "save entity");
				//x.saveEntity(prefs);
			//}
				
			
		//}
	
		
		return null;
	}
	
	
	private List<Entity> loadentities() {
		// TODO Auto-generated method stub
		
		return null;
	}

	 public static Level deserializeObject(byte[] b, InputHandler input, GameCanvas game) { 
		    try { 
		      ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(b)); 
		      Level object =(Level) in.readObject();
		      object.random = new Random();
		      object.spriteSorter = new Comparator<Entity>() {
		  		public int compare(Entity e0, Entity e1) {
		  			if (e1.y < e0.y) return +1;
		  			if (e1.y > e0.y) return -1;
		  			return 0;
		  		}

		  	};
		      for(Entity e : object.entities)
		      {
		    	  e.level  = object;
		    	  e.random = new Random();
		    	  
		    	  if (e instanceof Player)
		    	  {
		    		  Logger.v("mine", "found player, removing");
		    		  //object.entities.remove(e);
		    		  ((Player)e).input = input;
		    		  ((Player)e).game = game;
		    		  //((Player)e).input = 
		    	  }
		      }
		  	for (int i = 0; i < object.w * object.h; i++) {
				//entitiesInTiles[i] = new ArrayList<Entity>();
			//	int ecount =0;	
				for (Entity x : object.entitiesInTiles[i])
				{
					x.level = object;
				
					//i
					//Logger.v("mine", "save entity in tile");
					//x.saveEntity(prefs, i, ecount);
					//ecount++;
				}
					
				
			}
		    return object; 
		    } catch(ClassNotFoundException cnfe) { 
		     // Logger.v("deserializeObject", "class not found error", cnfe); 
		 
		      return null; 
		    } catch(IOException ioe) { 
		      //Logger.v("deserializeObject", "io error", ioe); 
		 
		      return null; 
		    } 
		  } 


	  public static byte[] serializeObject(Level o) { 
	    ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
	 
	    try { 
	      ObjectOutput out = new ObjectOutputStream(bos); 
	      out.writeObject(o); 
	      out.close(); 
	 
	      
	      // Get the bytes of the serialized object 
	      byte[] buf = bos.toByteArray(); 
	 
	      return buf; 
	    } catch(IOException ioe) { 
	      //Logger.e("serializeObject", "error", ioe); 
	 
	      return null; 
	    } 
	  } 
	  
	public void saveLevel(SharedPreferences.Editor prefs) {
		//public void onSaveInstanceState(Bundle savedInstanceState) {
			  // Save UI state changes to the savedInstanceState.
			  // This bundle will be passed to onCreate if the process is
			  // killed and restarted.
		//prefs.putBoolean("playerDeadTime", true);
		String prefix = String.format("level-%d", depth);
		Logger.v("mine", "level");
		prefs.putInt(prefix + "depth", depth);
		prefs.putInt(prefix + "w", w);
		prefs.putInt(prefix + "h", h);
		prefs.putInt(prefix + "dirtcolor", dirtColor);
		prefs.putInt(prefix +"monsterDensity", monsterDensity);
		
		
		//prefs.putByteArray(prefix +"tiles", tiles);
	//	prefs.putByteArray(prefix + "data", data);
		//for (int i = 0; i < w * h; i++) {
		//	((Entity)entitiesInTiles[i]).saveEntity(prefs);
	//	}
			
		for (int i = 0; i < w * h; i++) {
			//entitiesInTiles[i] = new ArrayList<Entity>();
			int ecount =0;	
			for (Entity x : entitiesInTiles[i])
			{
				Logger.v("mine", "save entity in tile");
				x.saveEntity(prefs, i, ecount);
				ecount++;
			}
				
			
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	public Level(int w, int h, int level, Level parentLevel) {
		if (level < 0) {
			dirtColor = 222;
		}
		this.depth = level;
		this.w = w;
		this.h = h;
		byte[][] maps;

		if (level == 1) {
			dirtColor = 444;
		}
		if (level == 0)
			maps = LevelGen.createAndValidateTopMap(w, h);
		else if (level < 0) {
			maps = LevelGen.createAndValidateUndergroundMap(w, h, -level);
			monsterDensity = 4;
		} else {
			maps = LevelGen.createAndValidateSkyMap(w, h); // Sky level
			monsterDensity = 4;
		}

		tiles = maps[0];
		data = maps[1];

		if (parentLevel != null) {
			for (int y = 0; y < h; y++)
				for (int x = 0; x < w; x++) {
					if (parentLevel.getTile(x, y) == Tile.stairsDown) {

						setTile(x, y, Tile.stairsUp, 0);
						if (level == 0) {
							setTile(x - 1, y, Tile.hardRock, 0);
							setTile(x + 1, y, Tile.hardRock, 0);
							setTile(x, y - 1, Tile.hardRock, 0);
							setTile(x, y + 1, Tile.hardRock, 0);
							setTile(x - 1, y - 1, Tile.hardRock, 0);
							setTile(x - 1, y + 1, Tile.hardRock, 0);
							setTile(x + 1, y - 1, Tile.hardRock, 0);
							setTile(x + 1, y + 1, Tile.hardRock, 0);
						} else {
							setTile(x - 1, y, Tile.dirt, 0);
							setTile(x + 1, y, Tile.dirt, 0);
							setTile(x, y - 1, Tile.dirt, 0);
							setTile(x, y + 1, Tile.dirt, 0);
							setTile(x - 1, y - 1, Tile.dirt, 0);
							setTile(x - 1, y + 1, Tile.dirt, 0);
							setTile(x + 1, y - 1, Tile.dirt, 0);
							setTile(x + 1, y + 1, Tile.dirt, 0);
						}
					}

				}
		}

		entitiesInTiles = new ArrayList[w * h];
		for (int i = 0; i < w * h; i++) {
			entitiesInTiles[i] = new ArrayList<Entity>();
		}
		
		if (level==1) {
			AirWizard aw = new AirWizard();
			aw.x = w*8;
			aw.y = h*8;
			add(aw);
		}
	}

	public void renderBackground(Screen screen, int xScroll, int yScroll) {
		int xo = xScroll >> 4;
		int yo = yScroll >> 4;
		int w = (screen.w + 15) >> 4;
		int h = (screen.h + 15) >> 4;
		screen.setOffset(xScroll, yScroll);
		for (int y = yo; y <= h + yo; y++) {
			for (int x = xo; x <= w + xo; x++) {
				getTile(x, y).render(screen, this, x, y);
			}
		}
		screen.setOffset(0, 0);
	}

	private List<Entity> rowSprites = new ArrayList<Entity>();

	public Player player;

	public void renderSprites(Screen screen, int xScroll, int yScroll) {
		int xo = xScroll >> 4;
		int yo = yScroll >> 4;
		int w = (screen.w + 15) >> 4;
		int h = (screen.h + 15) >> 4;

		screen.setOffset(xScroll, yScroll);
		for (int y = yo; y <= h + yo; y++) {
			for (int x = xo; x <= w + xo; x++) {
				if (x < 0 || y < 0 || x >= this.w || y >= this.h) continue;
				rowSprites.addAll(entitiesInTiles[x + y * this.w]);
			}
			if (rowSprites.size() > 0) {
				sortAndRender(screen, rowSprites);
			}
			rowSprites.clear();
		}
		screen.setOffset(0, 0);
	}

	
	
	public void hurtRadius(int xHurt, int yHurt)
	{
		int hurtdistance =10;
		
		//List<Entity>entities = entitiesInTies
	}
	public void renderLight(Screen screen, int xScroll, int yScroll) {
		int xo = xScroll >> 4;
		int yo = yScroll >> 4;
		int w = (screen.w + 15) >> 4;
		int h = (screen.h + 15) >> 4;

		screen.setOffset(xScroll, yScroll);
		int r = 4;
		for (int y = yo - r; y <= h + yo + r; y++) {
			for (int x = xo - r; x <= w + xo + r; x++) {
				if (x < 0 || y < 0 || x >= this.w || y >= this.h) continue;
				List<Entity> entities = entitiesInTiles[x + y * this.w];
				for (int i = 0; i < entities.size(); i++) {
					Entity e = entities.get(i);
					// e.render(screen);
					int lr = e.getLightRadius();
					if (lr > 0) screen.renderLight(e.x - 1, e.y - 4, lr * 8);
				}
				int lr = getTile(x, y).getLightRadius(this, x, y);
				if (lr > 0) screen.renderLight(x * 16 + 8, y * 16 + 8, lr * 8);
			}
		}
		screen.setOffset(0, 0);
	}

	// private void renderLight(Screen screen, int x, int y, int r) {
	// screen.renderLight(x, y, r);
	// }

	private void sortAndRender(Screen screen, List<Entity> list) {
		Collections.sort(list, spriteSorter);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).render(screen);
		}
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= w || y >= h) return Tile.rock;
		return Tile.tiles[tiles[x + y * w]];
	}

	public void setTile(int x, int y, Tile t, int dataVal) {
		if (x < 0 || y < 0 || x >= w || y >= h) return;
		tiles[x + y * w] = t.id;
		data[x + y * w] = (byte) dataVal;
	}

	public int getData(int x, int y) {
		if (x < 0 || y < 0 || x >= w || y >= h) return 0;
		return data[x + y * w] & 0xff;
	}

	public void setData(int x, int y, int val) {
		if (x < 0 || y < 0 || x >= w || y >= h) return;
		data[x + y * w] = (byte) val;
	}

	public void add(Entity entity) {
		if (entity instanceof Player) {
			player = (Player) entity;
		}
		entity.removed = false;
		entities.add(entity);
		entity.init(this);

		insertEntity(entity.x >> 4, entity.y >> 4, entity);
	}

	public void remove(Entity e) {
		entities.remove(e);
		int xto = e.x >> 4;
		int yto = e.y >> 4;
		removeEntity(xto, yto, e);
	}

	private void insertEntity(int x, int y, Entity e) {
		if (x < 0 || y < 0 || x >= w || y >= h) return;
		entitiesInTiles[x + y * w].add(e);
	}

	private void removeEntity(int x, int y, Entity e) {
		if (x < 0 || y < 0 || x >= w || y >= h) return;
		entitiesInTiles[x + y * w].remove(e);
	}

	public void trySpawn(int count) {
		for (int i = 0; i < count; i++) {
			Mob mob = null;

			int minLevel = 1;
			int maxLevel = 1;
			if (depth < 0) {
				maxLevel = (-depth) + 1;
			}
			if (depth > 0) {
				minLevel = maxLevel = 4;
			}

			int lvl = random.nextInt(maxLevel - minLevel + 1) + minLevel;
/*			if (random.nextInt(2) == 0)
				mob = new Slime(lvl);
			else
				mob = new Zombie(lvl);

			if (mob.findStartPos(this)) {
				this.add(mob);
			}*/
			
			switch (random.nextInt(2))
			{
				case 0:
					mob = new Slime(lvl);
				break;
				case 1:
					mob = new Zombie(lvl);
				break;
				
				case 2:
					mob = new Creeper(lvl);
				break;
					

			}
			
			
			if (mob.findStartPos(this)) {
				this.add(mob);
			}
			
			if (this.depth ==0 )
			{
			switch (random.nextInt(3))
			{
			
				case 0:
					mob = new Pig(lvl);
					
				break;
					
				case 1:
					
					mob = new Cow(lvl);
					
				break;
				case 2:
					mob = new Goose(lvl);
				break;
				
			}
			if (mob.findStartPos(this)) {
				this.add(mob);
			}
			}
			
		}
	}

	public void tick() {
		trySpawn(1);

		for (int i = 0; i < w * h / 50; i++) {
			int xt = random.nextInt(w);
			int yt = random.nextInt(w);
			getTile(xt, yt).tick(this, xt, yt);
		}
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			int xto = e.x >> 4;
			int yto = e.y >> 4;

			e.tick();

			if (e.removed) {
				entities.remove(i--);
				removeEntity(xto, yto, e);
			} else {
				int xt = e.x >> 4;
				int yt = e.y >> 4;

				if (xto != xt || yto != yt) {
					removeEntity(xto, yto, e);
					insertEntity(xt, yt, e);
				}
			}
		}
	}

	public List<Entity> getEntities(int x0, int y0, int x1, int y1) {
		List<Entity> result = new ArrayList<Entity>();
		int xt0 = (x0 >> 4) - 1;
		int yt0 = (y0 >> 4) - 1;
		int xt1 = (x1 >> 4) + 1;
		int yt1 = (y1 >> 4) + 1;
		for (int y = yt0; y <= yt1; y++) {
			for (int x = xt0; x <= xt1; x++) {
				if (x < 0 || y < 0 || x >= w || y >= h) continue;
				List<Entity> entities = entitiesInTiles[x + y * this.w];
				for (int i = 0; i < entities.size(); i++) {
					Entity e = entities.get(i);
					if (e.intersects(x0, y0, x1, y1)) result.add(e);
				}
			}
		}
		return result;
	}
}