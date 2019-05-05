package net.soggymustache.butterflies.util;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.soggymustache.butterflies.client.render.RenderButterfly;
import net.soggymustache.butterflies.entity.EntityButterfly;

public class ButterflyInfo {

	public final String NAME;
	public float[] colors;
	public final int rarity = -1;
	public final float BRARITY;
	
	public ButterflyInfo(String name, float bRarity) {
		this.NAME = name;
		this.BRARITY = bRarity;
	}
	
	public ButterflyInfo(String name, float bRarity, float... colors) {
		this(name, bRarity);
		this.colors = colors;
	}
	
	/**
	 * Change how the butterfly renders, call super if it should just be regular with the colors
	 */
	@SideOnly(Side.CLIENT)
	public void render(ModelBase model, IButterflyRenderer render, EntityButterfly e, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {		
		GlStateManager.pushMatrix();
		GlStateManager.color(e.getRed(), e.getGreen(), e.getBlue());
    	render.addTexture(RenderButterfly.WINGS);
    	model.setModelAttributes(RenderButterfly.BUTTERFLY);
    	model.render(e, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        GlStateManager.popMatrix();
	}
	
}
