package com.lols.diaf.entity;

import com.lols.diaf.gfx.Color;
import com.lols.diaf.gfx.Screen;
import com.lols.diaf.item.ResourceItem;
import com.lols.diaf.item.resource.Resource;
import com.lols.diaf.sound.Sound;

public class Pig extends Mob {
	private int xa, ya;
	private int randomWalkTime = 0;
	private int attackDelay = 0;
	private int attackTime = 0;
	private int attackType = 0;

	public Pig(int i) {
		x = random.nextInt(64 * 16);
		y = random.nextInt(64 * 16);
		health = maxHealth = 10;
	}

	public void tick() {
		super.tick();


		if (level.player != null && randomWalkTime == 0) {
			int xd = level.player.x + x;
			int yd = level.player.y + y;
			if (xd * xd + yd * yd < 50 * 50) {
				xa = 0;
				ya = 0;
				if (xd < 0) xa = -1;
				if (xd > 0) xa = +1;
				if (yd < 0) ya = -1;
				if (yd > 0) ya = +1;
			}
		}
          
		int speed = tickTime & 1;
		if (!move(xa * speed, ya * speed) || random.nextInt(200) == 0) {
			randomWalkTime = 60;
			xa = (random.nextInt(3) - 1) * random.nextInt(2);
			ya = (random.nextInt(3) - 1) * random.nextInt(2);
		}
		if (randomWalkTime > 0) randomWalkTime--;
	}

	protected void doHurt(int damage, int attackDir) {
		super.doHurt(damage, attackDir);
		if (attackDelay == 0 && attackTime == 0) {
			attackDelay = 60 * 2;
		}
	}

	public void render(Screen screen) {
		int b = 8;
		int xt = 8;
		int yt = 16;

		int flip1 = (walkDist >> 3) & 1;
		int flip2 = (walkDist >> 3) & 1;
		//int flip2 = 1;
		if (dir == 1) {
			xt += 2;
		}
		if (dir > 1) {
			flip1 = 0;
			flip2 = 0;//((walkDist >> 4) & 1);
			if (dir == 2) {
				flip1 = 1;
				flip2 = 1;
			}
			xt += 4 + ((walkDist >> 3) & 1) * 2;
		}
		int xo = x - 8;
		int yo = y - 11;

		int col1 = Color.get(-1, 523,520, 555);
		
		//int col2 = Color.get(-1, 300, 300, 300);
		/*if (health < 200) {
			if (tickTime / 3 % 2 == 0) {
				col1 = Color.get(-1, 500, 100, 555);
				col2 = Color.get(-1, 500, 100, 532);
			}
		} else if (health < 1000) {
			if (tickTime / 5 % 4 == 0) {
				col1 = Color.get(-1, 500, 100, 555);
				col2 = Color.get(-1, 500, 100, 532);
			}
		}
		if (hurtTime > 0) {
			col1 = Color.get(-1, 555, 555, 555);
			col2 = Color.get(-1, 555, 555, 555);
		}*/
		if (hurtTime > 0) {
			col1 = Color.get(-1, 555, 555, 555);
		//	col2 = Color.get(-1, 555, 555, 555);
		}
		screen.render(xo + b * flip1, yo + 0, xt + yt * 32, col1, flip1);
		screen.render(xo + b - b * flip1, yo + 0, xt + 1 + yt * 32, col1, flip1);
		screen.render(xo + b * flip2, yo + b, xt + (yt + 1) * 32, col1, flip2);
		screen.render(xo + b - b * flip2, yo + b, xt + 1 + (yt + 1) * 32, col1, flip2);
		super.render(screen);
	}

	protected void touchedBy(Entity entity) {
		if (entity instanceof Player) {
			//entity.hurt(this, 3, dir);
		}
		super.touchedBy(entity);
		
	}

	protected void die() {
		super.die();
		int count = random.nextInt(2) + 1;
		for (int i = 0; i < count; i++) {
			level.add(new ItemEntity(new ResourceItem(Resource.pork), x + random.nextInt(11) - 5, y + random.nextInt(11) - 5));
		}
	}

}