package net.soggymustache.butterflies;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.soggymustache.butterflies.proxy.CommonProxy;

@Mod(modid = ButterfliesReference.MOD_ID, name = ButterfliesReference.NAME, version = ButterfliesReference.VERSION)
public class ButterfliesMain {

	public static final Logger logger = LogManager.getLogger(ButterfliesReference.MOD_ID);
	
	@SidedProxy(serverSide = ButterfliesReference.SERVER_PROXY_CLASS, clientSide = ButterfliesReference.CLIENT_PROXY_CLASS)
	public static CommonProxy proxy;

	@Mod.Instance(ButterfliesReference.MOD_ID)
	public static ButterfliesMain instance;

	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
		proxy.registerRenders();
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
}
