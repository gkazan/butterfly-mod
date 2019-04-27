package net.soggymustache.butterflies.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.soggymustache.butterflies.entity.EntityButterfly;

public class ModelButterfly extends ModelBase {
    public ModelRenderer body;
    public ModelRenderer wingRight1;
    public ModelRenderer wingLeft1;
    public ModelRenderer head;
    public ModelRenderer wingRight2;
    public ModelRenderer wingLeft2;
    public ModelRenderer antannaeLeft;
    public ModelRenderer antannaeRight;

    public ModelButterfly() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.wingRight2 = new ModelRenderer(this, 0, 18);
        this.wingRight2.mirror = true;
        this.wingRight2.setRotationPoint(-1.0F, -1.0F, 0.0F);
        this.wingRight2.addBox(-9.0F, 0.0F, 0.0F, 9, 0, 7, 0.0F);
        this.wingRight1 = new ModelRenderer(this, 0, 9);
        this.wingRight1.mirror = true;
        this.wingRight1.setRotationPoint(-1.0F, -1.0F, 0.0F);
        this.wingRight1.addBox(-10.0F, 0.0F, -8.0F, 10, 0, 8, 0.0F);
        this.antannaeLeft = new ModelRenderer(this, 0, 0);
        this.antannaeLeft.setRotationPoint(0.6F, -0.6F, -1.0F);
        this.antannaeLeft.addBox(0.0F, -3.0F, -0.5F, 0, 3, 1, 0.0F);
        this.setRotateAngle(antannaeLeft, 0.3490658503988659F, 0.0F, 0.3490658503988659F);
        this.head = new ModelRenderer(this, 11, 0);
        this.head.setRotationPoint(-0.02F, -0.1F, -3.5F);
        this.head.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 2, 0.0F);
        this.setRotateAngle(head, 0.3490658503988659F, 0.0F, 0.0F);
        this.antannaeRight = new ModelRenderer(this, 0, 0);
        this.antannaeRight.mirror = true;
        this.antannaeRight.setRotationPoint(-0.6F, -0.6F, -1.0F);
        this.antannaeRight.addBox(0.0F, -3.0F, -0.5F, 0, 3, 1, 0.0F);
        this.setRotateAngle(antannaeRight, 0.3490658503988659F, 0.0F, -0.3490658503988659F);
        this.wingLeft1 = new ModelRenderer(this, 0, 9);
        this.wingLeft1.setRotationPoint(1.0F, -1.0F, 0.0F);
        this.wingLeft1.addBox(0.0F, 0.0F, -8.0F, 10, 0, 8, 0.0F);
        this.wingLeft2 = new ModelRenderer(this, 0, 18);
        this.wingLeft2.setRotationPoint(1.0F, -1.0F, 0.0F);
        this.wingLeft2.addBox(0.0F, 0.0F, 0.0F, 9, 0, 7, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body.addBox(-1.0F, -1.0F, -3.5F, 2, 2, 7, 0.0F);
        this.setRotateAngle(body, -0.3490658503988659F, 0.0F, 0.0F);
        this.body.addChild(this.wingRight2);
        this.body.addChild(this.wingRight1);
        this.head.addChild(this.antannaeLeft);
        this.body.addChild(this.head);
        this.head.addChild(this.antannaeRight);
        this.body.addChild(this.wingLeft1);
        this.body.addChild(this.wingLeft2);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.body.render(f5);
    }
    
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    	super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    	if(entityIn instanceof EntityButterfly) {
    		EntityButterfly butter = (EntityButterfly)entityIn;
    		
        	if(!butter.getIsButterflyLanded()) {
        		this.head.rotateAngleX = (float) Math.toRadians(20);
    	    	this.body.rotateAngleX = (float) Math.toRadians(-20);
    	    	this.wingLeft1.rotateAngleZ = MathHelper.cos(ageInTicks * 0.7F) * (float)Math.PI * 0.25F;
    	    	this.wingLeft2.rotateAngleZ = MathHelper.cos(ageInTicks * 0.7F) * (float)Math.PI * 0.25F;
    	    	
    	     	this.wingRight1.rotateAngleZ = -MathHelper.cos(ageInTicks * 0.7F) * (float)Math.PI * 0.25F;
    	    	this.wingRight2.rotateAngleZ = -MathHelper.cos(ageInTicks * 0.7F) * (float)Math.PI * 0.25F;
        	}
        	else {
        		this.head.rotateAngleX = 0;
    	    	this.body.rotateAngleX = 0;
    	    	
    	    	this.wingLeft1.rotateAngleZ = (float) Math.toRadians(-80);
    	    	this.wingRight1.rotateAngleZ = (float) Math.toRadians(80);
    	    	this.wingRight2.rotateAngleZ = (float) Math.toRadians(80);
    	    	this.wingLeft2.rotateAngleZ = (float) Math.toRadians(-80);
    	    	
    	    	this.wingLeft1.rotateAngleZ = MathHelper.cos(ageInTicks * 0.1F) * (float)Math.PI * 0.05F - 1.2F;
    	    	this.wingLeft2.rotateAngleZ = MathHelper.cos(ageInTicks * 0.1F) * (float)Math.PI * 0.05F - 1.2F;
    	    	
    	     	this.wingRight1.rotateAngleZ = -MathHelper.cos(ageInTicks * 0.1F) * (float)Math.PI * 0.05F + 1.2F;
    	    	this.wingRight2.rotateAngleZ = -MathHelper.cos(ageInTicks * 0.1F) * (float)Math.PI * 0.05F + 1.2F;
        	}
    	}
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
