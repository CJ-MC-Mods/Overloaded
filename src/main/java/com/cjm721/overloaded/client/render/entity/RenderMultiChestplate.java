package com.cjm721.overloaded.client.render.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;

/** Created by CJ on 5/25/2017. */
public class RenderMultiChestplate extends AbstractRenderMultiArmor {

  public RenderMultiChestplate(BipedModel baseModel) {
    super(baseModel);
//    this.bipedBody.cubeList.clear();
//    this.bipedRightArm.cubeList.clear();
//    this.bipedLeftArm.cubeList.clear();
//
//    ModelRenderOBJ body =
//        new ModelRenderOBJ(this, new ModelResourceLocation("overloaded:multi_body", "armor"));
//    ModelRenderOBJ rightArm =
//        new ModelRenderOBJ(this, new ModelResourceLocation("overloaded:multi_right_arm", "armor"));
//    ModelRenderOBJ leftArm =
//        new ModelRenderOBJ(this, new ModelResourceLocation("overloaded:multi_left_arm", "armor"));
//
//    body.offsetY = 0.755F;
//    rightArm.offsetY = 0.755F;
//    leftArm.offsetY = 0.755F;
//    body.offsetY = 0.755F;
//    body.offsetZ = -0.03F;
//    rightArm.offsetY = 0.72F;
//    rightArm.offsetX = -0.205F; // rightArm.offsetX = -0.18F;
//    rightArm.offsetZ = -0.05F;
//    leftArm.offsetY = 0.72F;
//    leftArm.offsetX = 0.21F; // leftArm.offsetX = 0.18F;
//    leftArm.offsetZ = -0.06F;
//
//    leftArm.scale = 1F / 13.9F;
//    rightArm.scale = 1F / 13.9F;
//
//    this.bipedBody.addChild(body);
//    this.bipedRightArm.addChild(rightArm);
//    this.bipedLeftArm.addChild(leftArm);
  }
//
//  @Override
//  public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
//    baseModel.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
//    super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
//    if (baseModel != null && !(entityIn instanceof PlayerEntity) && !entityIn.func_226296_dJ_()) {
//      this.bipedRightArm.rotateAngleX = baseModel.bipedRightArm.rotateAngleX;
//      this.bipedRightArm.rotationPointX = baseModel.bipedRightArm.rotationPointX;
//      this.bipedRightArm.rotateAngleY = baseModel.bipedRightArm.rotateAngleY;
//      this.bipedRightArm.rotationPointY= baseModel.bipedRightArm.rotationPointY;
//      this.bipedRightArm.rotateAngleZ = baseModel.bipedRightArm.rotateAngleZ;
//      this.bipedRightArm.rotationPointZ= baseModel.bipedRightArm.rotationPointZ;
//
//      this.bipedLeftArm.rotateAngleX = baseModel.bipedLeftArm.rotateAngleX;
//      this.bipedLeftArm.rotationPointX = baseModel.bipedLeftArm.rotationPointX;
//      this.bipedLeftArm.rotateAngleY = baseModel.bipedLeftArm.rotateAngleY;
//      this.bipedLeftArm.rotationPointY= baseModel.bipedLeftArm.rotationPointY;
//      this.bipedLeftArm.rotateAngleZ = baseModel.bipedLeftArm.rotateAngleZ;
//      this.bipedLeftArm.rotationPointZ= baseModel.bipedLeftArm.rotationPointZ;
//    }
//  }
//
//  @Override
//  public void render(
//      @Nullable LivingEntity entity,
//      float limbSwing,
//      float limbSwingAmount,
//      float ageInTicks,
//      float netHeadYaw,
//      float headPitch,
//      float scale) {
//    this.setRotationAngles(
//        entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
//
//    GlStateManager.pushMatrix();
//    if (this.isSneak) {
//      GlStateManager.translatef(0.0F, 0.2F, 0.0F);
//    }
//
//    this.bipedBody.render(scale);
//    this.bipedRightArm.render(scale);
//    this.bipedLeftArm.render(scale);
//
//    GlStateManager.popMatrix();
//  }
}
