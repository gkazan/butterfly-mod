package net.soggymustache.butterflies.proxy;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.soggymustache.butterflies.client.render.RenderButterfly;
import net.soggymustache.butterflies.client.render.TileEntityButterflyCaseRenderer;
import net.soggymustache.butterflies.entity.EntityButterfly;
import net.soggymustache.butterflies.init.block.te.TileEntityButterflyCase;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenders() {
		RenderingRegistry.registerEntityRenderingHandler(EntityButterfly.class, RenderButterfly::new);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityButterflyCase.class, new TileEntityButterflyCaseRenderer());
	}
}