package net.soggymustache.butterflies.init.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemButterfly extends ItemB{

	public ItemButterfly(String unloc) {
		super(unloc);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return stack.getTagCompound() == null ? super.getItemStackDisplayName(stack) : stack.getTagCompound().getString("Name");
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(stack.getTagCompound() != null) {
			tooltip.add("Rarity: " + stack.getTagCompound().getFloat("Rarity"));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
}
