package net.soggymustache.butterflies.config;

import java.util.stream.Stream;

import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.soggymustache.butterflies.ButterfliesMain;
import net.soggymustache.butterflies.ButterfliesReference;

@Config(modid = ButterfliesReference.MOD_ID, name = "Butterflies" + ButterfliesReference.VERSION)
@Config.LangKey("config.butterfly.title")
public class ButterflyConfig{

	public static final Options serverOptions = new Options();
	public static final ClientOptions clientOptions = new ClientOptions();

	public static class ClientOptions{
		
	}

	public static class Options {
		@Config.Name("Butterfly Spawn Weight")
		public int weight = 60;

		@Config.Name("Butterfly Spawn Min Group")
		public int min = 10;
		
		@Config.Name("Butterfly Spawn Max Group")
		public int max = 20;
		
		@Config.Name("Butterfly Spawn Biomes")
		public String[] biomes = ButterfliesMain.mergeArrays(BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST), BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS));
		
	}

	@Mod.EventBusSubscriber(modid = ButterfliesReference.MOD_ID)
	private static class EventHandler {
		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(ButterfliesReference.MOD_ID)) {
				ConfigManager.sync(ButterfliesReference.MOD_ID, Config.Type.INSTANCE);
			}
		}
	}
}
