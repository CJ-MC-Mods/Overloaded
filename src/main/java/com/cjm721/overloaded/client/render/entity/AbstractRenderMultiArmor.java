package com.cjm721.overloaded.client.render.entity;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;

import static com.cjm721.overloaded.Overloaded.cachedConfig;

public abstract class AbstractRenderMultiArmor extends BipedModel {

  public AbstractRenderMultiArmor() {
    super(
        0,
        0,
        cachedConfig.textureResolutions.multiArmorResolution,
        cachedConfig.textureResolutions.multiArmorResolution);
  }
}
