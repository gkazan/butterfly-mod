package net.soggymustache.butterflies.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderHelp {
	
	public static void renderModelOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, ModelBase model,
		ResourceLocation texture) {
		GlStateManager.enableColorMaterial();
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) posX, (float) posY, 350.0F);
		GlStateManager.scale((float) (-scale), (float) scale, (float) scale);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.glLightModel(2899, RenderHelper.setColorBuffer(0.9F, 0.9F, 0.9F, 1.0F));
		double d0 = (double) (mouseY);
		if (d0 < -180.0D) {
			mouseY += 360.0F;
		}
		if (d0 >= 180.0D) {
			mouseY -= 360.0F;
		}
		GlStateManager.rotate((float) d0 * 0.5F, 1.0F, 0.0F, 0.0F);
		double d1 = (double) (mouseX);
		if (d1 < -180.0D) {
			mouseX += 360.0F;
		}
		if (d1 >= 180.0D) {
			mouseX -= 360.0F;
		}
		GlStateManager.rotate((float) d1 * 0.5F, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(0.0F, -1.0F, 0.0F);
		RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
		rendermanager.setPlayerViewY(180.0F);
		rendermanager.setRenderShadow(false);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		model.isChild = false;
		try {
			model.render((Entity) null, 0, 0, 0, 0, 0, 0.0625F);
		} catch (NullPointerException e) {
		}
		rendermanager.setRenderShadow(true);
		GlStateManager.translate(0.0F, 0.0F, 0.0F);
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}
}
