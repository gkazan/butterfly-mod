package net.soggymustache.butterflies.client;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.soggymustache.butterflies.ButterfliesReference;
import net.soggymustache.butterflies.client.render.RenderButterfly;
import net.soggymustache.butterflies.client.render.TileEntityButterflyCaseRenderer;
import net.soggymustache.butterflies.entity.EntityButterfly;
import net.soggymustache.butterflies.init.block.te.TileEntityButterflyCase;

@Mod.EventBusSubscriber(modid = ButterfliesReference.MOD_ID)
public class RenderEventHandler {

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void ModelRegistryEvent(ModelRegistryEvent e) {
		RenderingRegistry.registerEntityRenderingHandler(EntityButterfly.class, RenderButterfly::new);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityButterflyCase.class, new TileEntityButterflyCaseRenderer());
	}
	
}
