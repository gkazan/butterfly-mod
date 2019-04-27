package net.soggymustache.butterflies.init.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.soggymustache.butterflies.ButterfliesReference;

@Mod.EventBusSubscriber(modid = ButterfliesReference.MOD_ID)
public class ButterflyBlocks {

	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block CASE = new BlockButterflyCase("butterfly_case", Material.WOOD);
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Block> e) {
		e.getRegistry().registerAll(BLOCKS.toArray(new Block[BLOCKS.size()]));
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerRenders(ModelRegistryEvent e) {
		for (Block itm : BLOCKS) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(itm), 0, new ModelResourceLocation(itm.getRegistryName(), "inventory"));
		}
	}

}