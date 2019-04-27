package net.soggymustache.butterflies.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.soggymustache.butterflies.ButterfliesReference;
import net.soggymustache.butterflies.client.model.ModelButterfly;
import net.soggymustache.butterflies.client.model.ModelButterflyCase;
import net.soggymustache.butterflies.init.block.BlockButterflyCase;
import net.soggymustache.butterflies.init.block.te.TileEntityButterflyCase;
import net.soggymustache.butterflies.util.NameUtilities;

public class TileEntityButterflyCaseRenderer extends TileEntitySpecialRenderer<TileEntityButterflyCase> {

	private static final ModelButterflyCase CASE = new ModelButterflyCase();
	public static final ModelButterfly BUTTERFLY = new ModelButterfly();
	private static final ResourceLocation CASE_TEXTURE = new ResourceLocation(ButterfliesReference.MOD_ID + ":textures/tile/case.png");
	
	@Override
    public void render(TileEntityButterflyCase te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		IBlockState ButterflyCase = te.getWorld().getBlockState(te.getPos());
		if(ButterflyCase == null)
			return;

		GL11.glPushMatrix();
		this.bindTexture(CASE_TEXTURE);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		
		switch(ButterflyCase.getValue(BlockButterflyCase.FACING)) {
			case SOUTH:
			default:
				GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
				break;
			case WEST:
				GL11.glRotatef(-180F, 1.0F, 0.0F, 1.0F);
				break;
			case EAST:
				GL11.glRotatef(180F, 1.0F, 0.0F, -1.0F);
				break;
			case NORTH:
				GL11.glRotatef(-180F, -0.0F, -0.0F, -1.0F);
				break;
		}

    	CASE.render((Entity) null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		GlStateManager.popMatrix();
		
		if(te.getTileData().hasKey("Name")) {
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			
			this.bindTexture(RenderButterfly.TEXTURE);
			GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			switch(ButterflyCase.getValue(BlockButterflyCase.FACING)) {
				case SOUTH:
				default:
					GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
					break;
				case WEST:
					GL11.glRotatef(-180F, 1.0F, 0.0F, 1.0F);
					break;
				case EAST:
					GL11.glRotatef(180F, 1.0F, 0.0F, -1.0F);
					break;
				case NORTH:
					GL11.glRotatef(-180F, -0.0F, -0.0F, -1.0F);
					break;
			}
			GL11.glRotatef(70, 1, 0, 0);
			GL11.glRotatef(180, 0, 1, 0);
			GL11.glTranslatef(0, -0.05F, 1.6F);
	    	BUTTERFLY.render((Entity) null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
	    	GlStateManager.color(1, 1, 1, 1);
/*	    	if(te.getTileData().getFloat("Extra") == NameUtilities.GLASSWING) {
	            GlStateManager.enableAlpha();
	            GlStateManager.enableBlend();
	            GlStateManager.color(te.getTileData().getFloat("Red"), te.getTileData().getFloat("Green"), te.getTileData().getFloat("Blue"), 0.5F);
	        	this.bindTexture(RenderButterfly.WINGS);
            }
            else if(te.getTileData().getFloat("Extra") == NameUtilities.ADMIRAL) {
            	this.bindTexture(RenderButterfly.ADMIRAL_WINGS);
    			BUTTERFLY.render((Entity) null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				GlStateManager.color(te.getTileData().getFloat("Red"), te.getTileData().getFloat("Green"), te.getTileData().getFloat("Blue"));
	        	this.bindTexture(RenderButterfly.ADMIRAL_COLOR);
            }
            else if(te.getTileData().getFloat("Extra") == NameUtilities.MONARCH) {
            	this.bindTexture(RenderButterfly.MONARCH_WINGS);
    			BUTTERFLY.render((Entity) null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				GlStateManager.color(te.getTileData().getFloat("Red"), te.getTileData().getFloat("Green"), te.getTileData().getFloat("Blue"));
	        	this.bindTexture(RenderButterfly.MONARCH_COLOR);
            }
            else if(te.getTileData().getFloat("Extra") == NameUtilities.CLOAK) {
            	this.bindTexture(RenderButterfly.CLOAK_WINGS);
    			BUTTERFLY.render((Entity) null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				GlStateManager.color(te.getTileData().getFloat("Red"), te.getTileData().getFloat("Green"), te.getTileData().getFloat("Blue"));
	        	this.bindTexture(RenderButterfly.CLOAK_COLOR);
            }
            else if(te.getTileData().getFloat("Extra") == NameUtilities.RAINBOW) {
    	        int i1 = 25;
    	        int i = te.ticks / 25 + 324;
    	        int j = EnumDyeColor.values().length;
    	        int k = i % j;
    	        int l = (i + 1) % j;
    	        float f = ((float)(te.ticks % 25) + 2.0F) / 25.0F;
    	        float[] afloat1 = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(k));
    	        float[] afloat2 = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(l));
    	        GlStateManager.color(afloat1[0] * (1.0F - f) + afloat2[0] * f, afloat1[1] * (1.0F - f) + afloat2[1] * f, afloat1[2] * (1.0F - f) + afloat2[2] * f);
	        	this.bindTexture(RenderButterfly.WINGS);
            }
            else {
				GlStateManager.color(te.getTileData().getFloat("Red"), te.getTileData().getFloat("Green"), te.getTileData().getFloat("Blue"));
	        	this.bindTexture(RenderButterfly.WINGS);
            }*/

			BUTTERFLY.render((Entity) null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

	      	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.popMatrix();
			

		}
	}
}
