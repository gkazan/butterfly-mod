package net.soggymustache.butterflies.client.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.soggymustache.butterflies.ButterfliesReference;
import net.soggymustache.butterflies.client.model.ModelButterfly;
import net.soggymustache.butterflies.entity.EntityButterfly;
import net.soggymustache.butterflies.util.ButterflyInfo;
import net.soggymustache.butterflies.util.ButterflyType;
import net.soggymustache.butterflies.util.NameUtilities;

@SideOnly(Side.CLIENT)
public class RenderButterfly extends RenderLiving<EntityButterfly>
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(ButterfliesReference.MOD_ID + ":textures/entity/butterfly/butterfly_base.png");
    public static final ResourceLocation WINGS = new ResourceLocation(ButterfliesReference.MOD_ID + ":textures/entity/butterfly/butterfly.png");
    public static final ResourceLocation ADMIRAL_WINGS = new ResourceLocation(ButterfliesReference.MOD_ID + ":textures/entity/butterfly/admiral_overlay.png");
    public static final ResourceLocation ADMIRAL_COLOR = new ResourceLocation(ButterfliesReference.MOD_ID + ":textures/entity/butterfly/admiral_color.png");
    public static final ResourceLocation MONARCH_WINGS = new ResourceLocation(ButterfliesReference.MOD_ID + ":textures/entity/butterfly/monarch_overlay.png");
    public static final ResourceLocation MONARCH_COLOR = new ResourceLocation(ButterfliesReference.MOD_ID + ":textures/entity/butterfly/monarch_color.png");
    public static final ResourceLocation CLOAK_WINGS = new ResourceLocation(ButterfliesReference.MOD_ID + ":textures/entity/butterfly/cloak_overlay.png");
    public static final ResourceLocation CLOAK_COLOR = new ResourceLocation(ButterfliesReference.MOD_ID + ":textures/entity/butterfly/cloak_color.png");
    
    
    public RenderButterfly(RenderManager rm)
    {
        super(rm, new ModelButterfly(), 0.2F);
        this.addLayer(new LayerButterfly(this));
    }
    
    @Override
    protected void preRenderCallback(EntityButterfly entitylivingbaseIn, float partialTickTime) {
    	super.preRenderCallback(entitylivingbaseIn, partialTickTime);
    	GlStateManager.scale(0.5F, 0.5F, 0.5F);
    	if(entitylivingbaseIn.getIsButterflyLanded()) {
    		GlStateManager.translate(0.0F, 1.6F, 0.0F);
    	}
    	else {
    		GlStateManager.translate(0.0F, 1.3F, 0.0F);
    	}
    }

    protected ResourceLocation getEntityTexture(EntityButterfly entity)
    {
        return TEXTURE;
    }
    
	@SideOnly(Side.CLIENT)
	public class LayerButterfly implements LayerRenderer<EntityButterfly> {
		private final RenderButterfly render;
		private final ModelButterfly butterfly = new ModelButterfly();
		
		public LayerButterfly(RenderButterfly re) {
			this.render = re;
		}
		
		@Override
		public void doRenderLayer(EntityButterfly e, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
			if (!e.isInvisible())
	        {
				if(e.getExtra() > ButterflyType.values().length) {
					NameUtilities.getInfo().get(e.getExtra()).render(this.butterfly, this.render, e, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
				}
				else {
					ResourceLocation color = null, wings = null;
					
					if(e.getExtra() == ButterflyType.ADMIRAL.id) {
						wings = render.ADMIRAL_WINGS;
						color = render.ADMIRAL_COLOR;
					}
					else if(e.getExtra() == ButterflyType.MONARCH.id) {
						wings = render.MONARCH_WINGS;
						color = render.MONARCH_COLOR;
					}
					else if(e.getExtra() == ButterflyType.CLOAK.id) {
						wings = render.CLOAK_WINGS;
						color = render.CLOAK_COLOR;
					}
					else {
						color = render.WINGS;
					}
					
					GlStateManager.pushMatrix();
					if(wings != null) {
		            	this.render.bindTexture(wings);
			        	this.butterfly.setModelAttributes(this.render.mainModel);
			            this.butterfly.render(e, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
					}
					GlStateManager.color(e.getRed(), e.getGreen(), e.getBlue());
		        	this.render.bindTexture(color);
		        	this.butterfly.setModelAttributes(this.render.mainModel);
		            this.butterfly.render(e, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		            GlStateManager.popMatrix();
				}
			}
		}

		@Override
		public boolean shouldCombineTextures() {
			return true;
		}
	}
}