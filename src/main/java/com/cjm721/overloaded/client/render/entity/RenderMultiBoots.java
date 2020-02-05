package com.cjm721.overloaded.client.render.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;

public class RenderMultiBoots extends AbstractRenderMultiArmor {

    public RenderMultiBoots(BipedModel baseModel) {
        super(baseModel);
//    this.bipedRightLeg.cubeList.clear();
//    this.bipedLeftLeg.cubeList.clear();
//
//    ModelRenderOBJ rightBoot =
//        new ModelRenderOBJ(this, new ModelResourceLocation(MODID + ":multi_right_boot", "armor"));
//    ModelRenderOBJ leftBoot =
//        new ModelRenderOBJ(this, new ModelResourceLocation(MODID + ":multi_left_boot", "armor"));
//
//    rightBoot.offsetY = 0.76F;
//    rightBoot.offsetX = -0.03F;
//    leftBoot.offsetY = 0.76F;
//    leftBoot.offsetX = 0.03F;

//    leftBoot.scale = 1F / 14F;
//    rightBoot.scale = 1F / 14F;
//
//    this.bipedRightLeg.addChild(rightBoot);
//    this.bipedLeftLeg.addChild(leftBoot);
    }

//    @Override
    public void render(
            @Nullable LivingEntity entity,
            float limbSwing,
            float limbSwingAmount,
            float ageInTicks,
            float netHeadYaw,
            float headPitch,
            float scale) {
//    super.setRotationAngles(
//        entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        GlStateManager.pushMatrix();
        if (this.isSneak) {
            GlStateManager.translatef(0.0F, 0.2F, 0.0F);
        }

//    this.bipedRightLeg.render(scale);
//    this.bipedLeftLeg.render(scale);

        GlStateManager.popMatrix();
    }
}
