package net.soggymustache.butterflies;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.soggymustache.butterflies.client.gui.GuiHandler;
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
		List<Biome> biomes = new ArrayList<>();
		
		for(Biome b : BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST)) {
			biomes.add(b);
		}
		
		for(Biome b : BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS)) {
			biomes.add(b);
		}
		
		for (Biome b : biomes) {
			Biome.SpawnListEntry e = new Biome.SpawnListEntry(EntityButterfly.class, 60, 10, 20);
			if (!b.getSpawnableList(EnumCreatureType.CREATURE).contains(e))
				b.getSpawnableList(EnumCreatureType.CREATURE).add(e);
		}
		
	}
}
