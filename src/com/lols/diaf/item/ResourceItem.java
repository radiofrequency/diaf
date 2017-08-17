package com.lols.diaf.item;

import com.lols.diaf.entity.ItemEntity;
import com.lols.diaf.entity.Player;
import com.lols.diaf.gfx.Color;
import com.lols.diaf.gfx.Font;
import com.lols.diaf.gfx.Screen;
import com.lols.diaf.item.resource.Resource;
import com.lols.diaf.level.Level;
import com.lols.diaf.level.tile.Tile;

public class ResourceItem extends Item {
	public Resource resource;
	public int count = 1;

	public ResourceItem(Resource resource) {
		this.resource = resource;
	}

	public ResourceItem(Resource resource, int count) {
		this.resource = resource;
		this.count = count;
	}

	public int getColor() {
		return resource.color;
	}

	public int getSprite() {
		return resource.sprite;
	}

	public void renderIcon(Screen screen, int x, int y) {
		screen.render(x, y, resource.sprite, resource.color, 0);
	}

	public void renderInventory(Screen screen, int x, int y) {
		screen.render(x, y, resource.sprite, resource.color, 0);
		Font.draw(resource.name, screen, x + 32, y, Color.get(-1, 555, 555, 555));
		int cc = count;
		if (cc > 999) cc = 999;
		Font.draw("" + cc, screen, x + 8, y, Color.get(-1, 444, 444, 444));
	}

	public String getName() {
		return resource.name;
	}

	public void onTake(ItemEntity itemEntity) {
	}

	public boolean interactOn(Tile tile, Level level, int xt, int yt, Player player, int attackDir) {
		if (resource.interactOn(tile, level, xt, yt, player, attackDir)) {
			count--;
			return true;
		}
		return false;
	}

	public boolean isDepleted() {
		return count <= 0;
	}

}