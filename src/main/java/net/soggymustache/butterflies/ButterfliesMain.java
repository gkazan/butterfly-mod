package net.soggymustache.butterflies;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.soggymustache.butterflies.client.gui.GuiHandler;
import net.soggymustache.butterflies.config.ButterflyConfig;
import net.soggymustache.butterflies.entity.EntityButterfly;

@Mod(modid = ButterfliesReference.MOD_ID, name = ButterfliesReference.NAME, version = ButterfliesReference.VERSION)
public class ButterfliesMain {

	public static final Logger logger = LogManager.getLogger(ButterfliesReference.MOD_ID);

	@Mod.Instance(ButterfliesReference.MOD_ID)
	public static ButterfliesMain instance;

	@EventHandler
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(ButterfliesMain.instance, new GuiHandler());
	}

	@EventHandler
	public static void serverStarting(FMLServerStartingEvent event) {
		for (String s : ButterflyConfig.serverOptions.biomes) {
			Biome b = ForgeRegistries.BIOMES.getValue(new ResourceLocation(s));
			Biome.SpawnListEntry e = new Biome.SpawnListEntry(EntityButterfly.class, ButterflyConfig.serverOptions.weight, ButterflyConfig.serverOptions.min, ButterflyConfig.serverOptions.max);
			b.getSpawnableList(EnumCreatureType.CREATURE).add(e);
		}
	}
	
	public static String[] mergeArrays(Set<Biome> set, Set<Biome> set2) {
		List<String> valid = new ArrayList<>();
		for(Biome e : set)
			valid.add(e.getRegistryName().toString());
		for(Biome e : set2)
			valid.add(e.getRegistryName().toString());
		return valid.toArray(new String[0]);
	}
}
