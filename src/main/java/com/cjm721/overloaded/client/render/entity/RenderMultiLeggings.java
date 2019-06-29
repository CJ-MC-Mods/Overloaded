package com.cjm721.overloaded.client.render.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;

public class RenderMultiLeggings extends AbstractRenderMultiArmor {

  public RenderMultiLeggings() {
    this.bipedBody.cubeList.clear();
    this.bipedRightLeg.cubeList.clear();
    this.bipedLeftLeg.cubeList.clear();

    ModelRenderOBJ belt =
        new ModelRenderOBJ(this, new ResourceLocation(MODID, "item/armor/multi_belt.obj"));
    ModelRenderOBJ rightLeg =
        new ModelRenderOBJ(this, new ResourceLocation(MODID, "item/armor/multi_right_leg.obj"));
    ModelRenderOBJ leftLeg =
        new ModelRenderOBJ(this, new ResourceLocation(MODID, "item/armor/multi_left_leg.obj"));

    belt.offsetY = 0.756F;
    belt.offsetZ = -0.04F;
    rightLeg.offsetY = 0.6F;
    rightLeg.offsetX = -0.085F; // rightLeg.offsetX = -0.06F;
    leftLeg.offsetY = 0.6F;
    leftLeg.offsetX = 0.085F; // leftLeg.offsetX = 0.06F;

    leftLeg.scale = 1F / 14F;
    rightLeg.scale = 1F / 14F;

    this.bipedBody.addChild(belt);
    this.bipedRightLeg.addChild(rightLeg);
    this.bipedLeftLeg.addChild(leftLeg);
  }

  @Override
  public void render(
      @Nullable LivingEntity entity,
      float limbSwing,
      float limbSwingAmount,
      float ageInTicks,
      float netHeadYaw,
      float headPitch,
      float scale) {
    super.setRotationAngles(
        entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

    GlStateManager.pushMatrix();
    if (this.isSneak) {
      GlStateManager.translatef(0.0F, 0.2F, 0.0F);
    }

    this.bipedBody.render(scale);
    this.bipedRightLeg.render(scale);
    this.bipedLeftLeg.render(scale);

    GlStateManager.popMatrix();
  }
}
