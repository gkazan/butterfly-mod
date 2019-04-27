package net.soggymustache.butterflies.init.items;

import net.minecraft.item.Item;

public class ItemB extends Item{
	
	public ItemB(String unloc) {
		this.setUnlocalizedName(unloc);
		this.setRegistryName(unloc);
		ButterflyItems.ITEMS.add(this);
	}
}
