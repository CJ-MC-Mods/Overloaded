package com.cjm721.overloaded.client.render.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;

public class RenderMultiBoots extends ModelBiped {

    public RenderMultiBoots() {
        super(0, 0, 1024, 1024);

        this.bipedRightLeg.cubeList.clear();
        this.bipedLeftLeg.cubeList.clear();

        ModelRenderOBJ rightBoot = new ModelRenderOBJ(this, new ResourceLocation(MODID, "item/armor/multi_right_boot.obj"));
        ModelRenderOBJ leftBoot = new ModelRenderOBJ(this, new ResourceLocation(MODID, "item/armor/multi_left_boot.obj"));

        rightBoot.offsetY = 0.76F;
        rightBoot.offsetX = -0.03F;
        leftBoot.offsetY = 0.76F;
        leftBoot.offsetX = 0.03F;

        leftBoot.scale = 1F / 14F;
        rightBoot.scale = 1F / 14F;

        this.bipedRightLeg.addChild(rightBoot);
        this.bipedLeftLeg.addChild(leftBoot);
    }

    @Override
    public void render(@Nullable Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entity != null && entity instanceof EntityPlayer) {
            super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
        }

        GlStateManager.pushMatrix();
        if (entity != null && entity.isSneaking()) {
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
        }

        this.bipedRightLeg.render(scale);
        this.bipedLeftLeg.render(scale);

        GlStateManager.popMatrix();
    }
}
