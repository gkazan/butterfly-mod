package net.soggymustache.butterflies.proxy;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.soggymustache.butterflies.ButterfliesMain;
import net.soggymustache.butterflies.ButterfliesReference;
import net.soggymustache.butterflies.client.gui.GuiHandler;
import net.soggymustache.butterflies.entity.ButterfliesEntities;
import net.soggymustache.butterflies.init.block.te.TileEntityButterflyCase;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event){	
		GameRegistry.registerTileEntity(TileEntityButterflyCase.class, new ResourceLocation(ButterfliesReference.MOD_ID + ":te_butterfly_case"));
	}
	
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(ButterfliesMain.instance, new GuiHandler());
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
	
	public void registerRenders() {
	}
	
}
