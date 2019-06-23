package com.cjm721.overloaded.client.render.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;

import javax.annotation.Nullable;

public class RenderMultiHelmet extends AbstractRenderMultiArmor {

  public RenderMultiHelmet() {
    this.field_78116_c.cubeList.clear();
    this.bipedHeadwear.cubeList.clear();

    //        ModelRenderOBJ head = new ModelRenderOBJ(this, new ResourceLocation(MODID,
    // "item/armor/multi_helmet.obj"));
    //        head.offsetY = -0.1F;
    //        head.offsetX = -0.033F;
    //        head.offsetZ = 0.1F;
    //        head.scale = 1F / 13F;
    //
    //        this.bipedHead.addChild(head);
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
    if (entity instanceof ArmorStandEntity) {
      netHeadYaw = ((ArmorStandEntity) entity).rotationYawHead;
    }
    super.setRotationAngles(
        entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

    GlStateManager.pushMatrix();
    if (this.isSneak) {
      GlStateManager.translatef(0.0F, 0.2F, 0.0F);
    }

    this.field_78116_c.render(scale);

    GlStateManager.popMatrix();
  }
}
