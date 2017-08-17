package com.lols.diaf.crafting;

import com.lols.diaf.entity.Player;
import com.lols.diaf.gfx.Color;
import com.lols.diaf.gfx.Font;
import com.lols.diaf.gfx.Screen;
import com.lols.diaf.item.ResourceItem;
import com.lols.diaf.item.resource.Resource;

public class ResourceRecipe extends Recipe {
	private Resource resource;

	public ResourceRecipe(Resource resource) {
		super(new ResourceItem(resource, 1));
		this.resource = resource;
	}

	public void craft(Player player) {
		player.inventory.add(0, new ResourceItem(resource, 1));
	}
}
