package net.soggymustache.butterflies.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.soggymustache.butterflies.ButterfliesReference;
import net.soggymustache.butterflies.client.model.ModelButterfly;
import net.soggymustache.butterflies.client.render.RenderButterfly;
import net.soggymustache.butterflies.client.render.TileEntityButterflyCaseRenderer;
import net.soggymustache.butterflies.entity.EntityButterfly;
import net.soggymustache.butterflies.util.ButterflyType;
import net.soggymustache.butterflies.util.IButterflyRenderer;
import net.soggymustache.butterflies.util.NameUtilities;

public class GuiLepidopterologyManual extends GuiScreen implements IButterflyRenderer {
	
	private static final Minecraft mc = Minecraft.getMinecraft();
	private final int ImageHeight = 201, ImageWidth = 318;
	private static final ResourceLocation GUITextures = new ResourceLocation(ButterfliesReference.MOD_ID + ":textures/gui/data.png");

    private float mousePosx;
    private float mousePosY;
    
	private int page = 0;
	private int totalPages = 1;
	private EntityButterfly butterfly;
	private NBTTagCompound compound;
	private static final ModelButterfly bModel = new ModelButterfly();
	
	public GuiLepidopterologyManual(EntityButterfly butterfly){
		this.butterfly = butterfly;
	}
	
	public GuiLepidopterologyManual(NBTTagCompound compound){
		this.compound = compound;
	}


	@Override
	public void initGui() {

	}
	
	@Override
	public void updateScreen() {

	}

	@Override
	public void drawScreen(int parWidth, int parHeight, float particle) {
		if(butterfly != null) {
			GlStateManager.pushMatrix();
	        this.mousePosx = (float)parWidth;
	        this.mousePosY = (float)parHeight;
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableColorMaterial(); 
			
			int offLeft = (int) ((width - ImageWidth) / 2.0F);
			int offTop = 30;
			
			this.mc.getTextureManager().bindTexture(GUITextures);
			this.drawModalRectWithCustomSizedTexture(offLeft - 10, offTop, 0, 0, ImageWidth + 20,ImageHeight,ImageWidth + 20,ImageHeight);
			
	        int ie = (this.width - this.ImageWidth) / 2;
	        int je = (this.height - this.ImageHeight) / 2 + 30;
	        //(float)(ie + 51) - this.mousePosx, (float)(je + 75 - 10) - this.mousePosY
	
	        offTop += 20;
	        this.drawEntityOnScreen(offLeft + 240, offTop + 100, 195, 500, 10, butterfly);
	
	        fontRenderer.drawString("Name: " + butterfly.getBName(), offLeft + 15, offTop + 00, 0X000000); 
	        fontRenderer.drawString("Rarity: " + butterfly.getRarity(), offLeft + 15, offTop + 10, 0X000000); 
	        
			GlStateManager.popMatrix();
		}
		if(compound != null) {
			GlStateManager.pushMatrix();
	        this.mousePosx = (float)parWidth;
	        this.mousePosY = (float)parHeight;
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableColorMaterial(); 
			
			int offLeft = (int) ((width - ImageWidth) / 2.0F);
			int offTop = 30;
			
			this.mc.getTextureManager().bindTexture(GUITextures);
			this.drawModalRectWithCustomSizedTexture(offLeft - 10, offTop, 0, 0, ImageWidth + 20,ImageHeight,ImageWidth + 20,ImageHeight);
			
	        int ie = (this.width - this.ImageWidth) / 2;
	        int je = (this.height - this.ImageHeight) / 2 + 30;
	
	        offTop += 20;
//	        this.drawEntityOnScreen(offLeft + 240, offTop + 100, 195, 500, 10, butterfly);
			int posX = 240 + offLeft;
			int posY = 60 + offTop;
			int scale = 100;

			for(int pp = 0; pp < 2; pp++) {
				if(pp == 1) {
					posX = 110 + offLeft;
					posY = -30 + offTop;
				}
				GlStateManager.enableColorMaterial();
				GlStateManager.pushMatrix();
				GlStateManager.translate((float) posX, (float) posY, 350.0F);
				GlStateManager.scale((float) (-scale), (float) scale, (float) scale);
				RenderHelper.enableStandardItemLighting();
				GlStateManager.glLightModel(2899, RenderHelper.setColorBuffer(0.9F, 0.9F, 0.9F, 1.0F));
				GlStateManager.rotate(-100, 1, 0, 0);
				GlStateManager.translate(0.0F, -1.0F, 0.0F);
				RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
				rendermanager.setPlayerViewY(180.0F);
				rendermanager.setRenderShadow(false);
				
				if(pp == 1) {
					GlStateManager.rotate(90, 0, 0, 1);
					GlStateManager.rotate(100, 1, 0, 0);
					GlStateManager.rotate(180, 0, 1, 0);
				}
				
				TileEntityButterflyCaseRenderer.BUTTERFLY.isChild = false;
				GlStateManager.color(1, 1, 1, 1);
				Minecraft.getMinecraft().getTextureManager().bindTexture(RenderButterfly.TEXTURE);
				TileEntityButterflyCaseRenderer.BUTTERFLY.render((Entity) null, 0, 0, 0, 0, 0, 0.0625F);


				int extra = compound.getInteger("Extra");
		    	
				if(extra > ButterflyType.values().length) {
					NameUtilities.getInfo().get(extra).renderCase(TileEntityButterflyCaseRenderer.BUTTERFLY, this, compound, 0, 0, 0, 0, 0, 0.0625F);
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
		            	this.addTexture(wings);
		            	TileEntityButterflyCaseRenderer.BUTTERFLY.render((Entity) null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
					}
					GlStateManager.color(compound.getFloat("Red"), compound.getFloat("Green"), compound.getFloat("Blue"));
		        	this.addTexture(color);
		            GlStateManager.popMatrix();
				}
				
				TileEntityButterflyCaseRenderer.BUTTERFLY.render((Entity) null, 0, 0, 0, 0, 0, 0.0625F);
				rendermanager.setRenderShadow(true);
				GlStateManager.translate(0.0F, 0.0F, 0.0F);
				GlStateManager.popMatrix();
				RenderHelper.disableStandardItemLighting();
				GlStateManager.disableRescaleNormal();
				GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
				GlStateManager.disableTexture2D();
				GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
			}
		    
	        fontRenderer.drawString("Name: " + compound.getString("Name"), offLeft + 15, offTop + 00, 0X000000); 
	        fontRenderer.drawString("Rarity: " + compound.getFloat("Rarity"), offLeft + 15, offTop + 10, 0X000000); 
	        
			GlStateManager.popMatrix();
		}
		super.drawScreen(parWidth, parHeight, particle);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode){
		if (keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode())
			Minecraft.getMinecraft().player.closeScreen();
	}
	@Override
	protected void mouseClickMove(int parMouseX, int parMouseY, int parLastButtonClicked, long parTimeSinceMouseClick) { }

	@Override
	protected void actionPerformed(GuiButton button) {

	}

	@Override
	public void onGuiClosed() {
		butterfly = null;
		compound = null;
	}

    public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent)
    {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)posX, (float)posY, 50.0F);
        GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        //GlStateManager.rotate(ent.world.getWorldTime() * 0.9F, 0, 1, 0);
        ent.renderYawOffset = (float)Math.atan((double)(mouseX / 40.0F)) * 20.0F;
        ent.rotationYaw = (float)Math.atan((double)(mouseX / 40.0F)) * 40.0F;
        ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@SideOnly(Side.CLIENT)
   	static class GenericButton extends GuiButton{
		public GenericButton(int x, int y, int width, int height, String text) {
			super(1, x, y, width, height, text);
		}
	}

	@Override
	public void addTexture(ResourceLocation resource) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
	}
}
