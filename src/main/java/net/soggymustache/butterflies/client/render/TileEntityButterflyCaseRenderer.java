package net.soggymustache.butterflies.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.soggymustache.butterflies.ButterfliesReference;
import net.soggymustache.butterflies.client.model.ModelButterfly;
import net.soggymustache.butterflies.client.model.ModelButterflyCase;
import net.soggymustache.butterflies.init.block.BlockButterflyCase;
import net.soggymustache.butterflies.init.block.te.TileEntityButterflyCase;
import net.soggymustache.butterflies.util.ButterflyType;
import net.soggymustache.butterflies.util.IButterflyRenderer;
import net.soggymustache.butterflies.util.NameUtilities;

public class TileEntityButterflyCaseRenderer extends TileEntitySpecialRenderer<TileEntityButterflyCase> implements IButterflyRenderer{

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
			GL11.glTranslatef((float) x + 0.5F, (float) y + 1.2F, (float) z + 0.5F);
			GL11.glScalef(0.35F, 0.35F, 0.35F);
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
			GL11.glTranslatef(0.0F, 0.0F, 0.33F);
			GL11.glRotatef(70, 1, 0, 0);
			GL11.glRotatef(180, 0, 1, 0);
			GL11.glTranslatef(0, -0.05F, 1.6F);
	    	BUTTERFLY.render((Entity) null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
	    	GlStateManager.color(1, 1, 1, 1);
	    	
	    	int extra = te.getTileData().getInteger("Extra");
	    	
			if(extra > ButterflyType.values().length) {
				GlStateManager.pushMatrix();
				NameUtilities.getInfo().get(extra).renderCase(BUTTERFLY, this, te.getTileData(), 0, 0, 0, 0, 0, 0.0625F);
				GlStateManager.popMatrix();
			}
			else {
				ResourceLocation color = null, wings = null;
				
				if(extra == ButterflyType.ADMIRAL.id) {
					wings = RenderButterfly.ADMIRAL_WINGS;
					color = RenderButterfly.ADMIRAL_COLOR;
				}
				else if(extra == ButterflyType.MONARCH.id) {
					wings = RenderButterfly.MONARCH_WINGS;
					color = RenderButterfly.MONARCH_COLOR;
				}
				else if(extra == ButterflyType.CLOAK.id) {
					wings = RenderButterfly.CLOAK_WINGS;
					color = RenderButterfly.CLOAK_COLOR;
				}
				else {
					color = RenderButterfly.WINGS;
				}
				
				GlStateManager.pushMatrix();
				if(wings != null) {
	            	this.bindTexture(wings);
	            	BUTTERFLY.render((Entity) null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				}
				GlStateManager.color(te.getTileData().getFloat("Red"), te.getTileData().getFloat("Green"), te.getTileData().getFloat("Blue"));
	        	this.bindTexture(color);
	            GlStateManager.popMatrix();
			}
			BUTTERFLY.render((Entity) null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

	      	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.popMatrix();
		}
	}

	@Override
	public void addTexture(ResourceLocation resource) {
		this.bindTexture(resource);
	}
}
