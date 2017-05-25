package com.cjm721.overloaded.client.render.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;

/**
 * Created by CJ on 5/25/2017.
 */
public class RenderCustomChestplate extends ModelBiped {

    public RenderCustomChestplate() {
        super(0, 0, 1024, 1024);
        this.bipedBody.cubeList.clear();
        this.bipedRightArm.cubeList.clear();
        this.bipedLeftArm.cubeList.clear();

        ModelRenderOBJ body = new ModelRenderOBJ(this, new ResourceLocation(MODID, "models/item/armor/custom_helmet.obj"));
        ModelRenderOBJ rightArm = new ModelRenderOBJ(this, new ResourceLocation(MODID, "models/item/armor/custom_helmet.obj"));
        ModelRenderOBJ leftArm = new ModelRenderOBJ(this, new ResourceLocation(MODID, "models/item/armor/custom_helmet.obj"));

        this.bipedBody.addChild(body);
        this.bipedRightArm.addChild(rightArm);
        this.bipedLeftArm.addChild(leftArm);

        body.offsetY = 0.755F;
        body.offsetZ = -0.03F;
        rightArm.offsetY = 0.72F;
        rightArm.offsetX = -0.205F; //rightArm.offsetX = -0.18F;
        rightArm.offsetZ = -0.05F;
        leftArm.offsetY = 0.72F;
        leftArm.offsetX = 0.21F; //leftArm.offsetX = 0.18F;
        leftArm.offsetZ = -0.06F;
    }

    @Override
    public void render(@Nullable Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {

        if (entity != null && entity instanceof EntityPlayer) {
            super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        }

        GlStateManager.pushMatrix();
        if (entity != null && entity.isSneaking()) {
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
        }

        this.bipedBody.render(1);
        this.bipedRightArm.render(1);
        this.bipedLeftArm.render(1);

        GlStateManager.popMatrix();

    }
}