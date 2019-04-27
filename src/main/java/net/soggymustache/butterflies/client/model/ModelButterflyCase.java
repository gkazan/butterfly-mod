package net.soggymustache.butterflies.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelButterflyCase extends ModelBase {
    public ModelRenderer base;
    public ModelRenderer top;
    public ModelRenderer right;
    public ModelRenderer left;
    public ModelRenderer bottom;
    public ModelRenderer door;

    public ModelButterflyCase() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.door = new ModelRenderer(this, 28, 16);
        this.door.setRotationPoint(-4.5F, 0.0F, -2.98F);
        this.door.addBox(0.0F, -6.5F, -1.0F, 9, 13, 1, 0.0F);
        this.bottom = new ModelRenderer(this, 28, 14);
        this.bottom.setRotationPoint(0.0F, 6.5F, -2.0F);
        this.bottom.addBox(-5.5F, 0.0F, -1.0F, 11, 1, 1, 0.0F);
        this.left = new ModelRenderer(this, 54, 0);
        this.left.setRotationPoint(4.5F, -1.0F, -2.0F);
        this.left.addBox(0.0F, -5.5F, -1.0F, 1, 13, 1, 0.0F);
        this.top = new ModelRenderer(this, 28, 14);
        this.top.setRotationPoint(0.0F, -6.5F, -2.0F);
        this.top.addBox(-5.5F, -1.0F, -1.0F, 11, 1, 1, 0.0F);
        this.right = new ModelRenderer(this, 54, 0);
        this.right.setRotationPoint(-4.5F, -1.0F, -2.0F);
        this.right.addBox(-1.0F, -5.5F, -1.0F, 1, 13, 1, 0.0F);
        this.base = new ModelRenderer(this, 0, 0);
        this.base.setRotationPoint(0.0F, 16.0F, 8.0F);
        this.base.addBox(-6.0F, -8.0F, -2.0F, 12, 16, 2, 0.0F);
        this.base.addChild(this.door);
        this.base.addChild(this.bottom);
        this.base.addChild(this.left);
        this.base.addChild(this.top);
        this.base.addChild(this.right);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.base.render(f5);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
