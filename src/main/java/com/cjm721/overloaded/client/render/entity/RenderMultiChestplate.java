package com.cjm721.overloaded.client.render.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;
import static com.cjm721.overloaded.Overloaded.cachedConfig;

/**
 * Created by CJ on 5/25/2017.
 */
public class RenderMultiChestplate extends AbstractRenderMultiArmor {

    public RenderMultiChestplate() {
        this.bipedBody.cubeList.clear();
        this.bipedRightArm.cubeList.clear();
        this.bipedLeftArm.cubeList.clear();

        ModelRenderOBJ body = new ModelRenderOBJ(this, new ResourceLocation(MODID, "item/armor/multi_body.obj"));
        ModelRenderOBJ rightArm = new ModelRenderOBJ(this, new ResourceLocation(MODID, "item/armor/multi_right_arm.obj"));
        ModelRenderOBJ leftArm = new ModelRenderOBJ(this, new ResourceLocation(MODID, "item/armor/multi_left_arm.obj"));

        body.offsetY = 0.755F;
        rightArm.offsetY = 0.755F;
        leftArm.offsetY = 0.755F;
        body.offsetY = 0.755F;
        body.offsetZ = -0.03F;
        rightArm.offsetY = 0.72F;
        rightArm.offsetX = -0.205F; //rightArm.offsetX = -0.18F;
        rightArm.offsetZ = -0.05F;
        leftArm.offsetY = 0.72F;
        leftArm.offsetX = 0.21F; //leftArm.offsetX = 0.18F;
        leftArm.offsetZ = -0.06F;

        leftArm.scale = 1F / 13.9F;
        rightArm.scale = 1F / 13.9F;

        this.bipedBody.addChild(body);
        this.bipedRightArm.addChild(rightArm);
        this.bipedLeftArm.addChild(leftArm);

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

        this.bipedBody.render(f5);
        this.bipedRightArm.render(f5);
        this.bipedLeftArm.render(f5);

        GlStateManager.popMatrix();
    }
}