package net.soggymustache.butterflies.init.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.soggymustache.butterflies.ButterfliesReference;

@Mod.EventBusSubscriber(modid = ButterfliesReference.MOD_ID)
public class ButterflyItems {
	
	public static final List<Item> ITEMS = new ArrayList<Item>();

	public static final Item LEPIDOPTEROLOGY_BOOK = new ItemB("lepidopterology_book") {
		public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
			super.addInformation(stack, worldIn, tooltip, flagIn);
			tooltip.add("Right click a case or butterfly to use");
		}
	}.setCreativeTab(CreativeTabs.MISC);
	public static final Item BUTTERFLY_NET = new ItemNet("butterfly_net").setCreativeTab(CreativeTabs.MISC);
	public static final Item BUTTERFLY = new ItemButterfly("butterfly").setCreativeTab(CreativeTabs.MISC);
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> e) {
		e.getRegistry().registerAll(ITEMS.toArray(new Item[ITEMS.size()]));
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerRenders(ModelRegistryEvent e) {
		for (Item itm : ITEMS) {
			ModelLoader.setCustomModelResourceLocation(itm, 0, new ModelResourceLocation(itm.getRegistryName(), "inventory"));
		}
	}
}
