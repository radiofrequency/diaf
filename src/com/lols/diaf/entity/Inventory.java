package com.lols.diaf.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.lols.diaf.item.Item;
import com.lols.diaf.item.ResourceItem;
import com.lols.diaf.item.resource.Resource;

public class Inventory implements Serializable {
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	public List<Item> items = new ArrayList<Item>();

	public void add(Item item) {
		add(items.size(), item);
	}

	public void add(int slot, Item item) {
		
	//	if (item.getName().equals(object))
		if (item instanceof ResourceItem) {
			ResourceItem toTake = (ResourceItem) item;
			ResourceItem has = findResource(toTake.resource);
			if (has == null) {
				items.add(slot, toTake);
			} else {
				has.count += toTake.count;
			}
		} else {
			items.add(slot, item);
		}
	}

	private ResourceItem findResource(Resource resource) {
		for (int i = 0; i < items.size(); i++) {
			{
				//Logger.v("mine", String.format("items: %d %d", i, items.size()));
				if (items.get(i) instanceof ResourceItem) {
					//Logger.v("mine", String.format("is resource item items: %d %d", i, items.size()));
					ResourceItem has = (ResourceItem) items.get(i);
					if (has.resource.name.equals(resource.name))
							{
	//						Logger.v("mine", String.format("has: %d %d %s has %s", i, items.size(), resource.name, has.getName()));
						
						return has;
					
							}
					if (has.resource == resource) {
		//				Logger.v("mine", String.format("has: %d %d", i, items.size()));
						
						return has;
					}
				}
			}
		}
		return null;
	}

	public boolean hasResources(Resource r, int count) {
		ResourceItem ri = findResource(r);
		if (ri == null) return false;
		return ri.count >= count;
	}

	public boolean removeResource(Resource r, int count) {
		ResourceItem ri = findResource(r);
		if (ri == null) return false;
		if (ri.count < count) return false;
		ri.count -= count;
		if (ri.count <= 0) items.remove(ri);
		return true;
	}

	public int count(Item item) {
		//if (item.getClass().getName().equals(ResourceItem))
		if (item instanceof ResourceItem) {
			ResourceItem ri = findResource(((ResourceItem)item).resource);
			if (ri!=null) return ri.count;
		} else {
			int count = 0;
			for (int i=0; i<items.size(); i++) {
//				if (items.get(i).getClass().getName().equals(item.getClass().getName())) count++;
				//
				if (items.get(i).matches(item)) count++;
			}
			return count;
		}
		return 0;
	}
}
