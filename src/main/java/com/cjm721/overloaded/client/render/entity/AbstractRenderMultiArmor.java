package com.cjm721.overloaded.client.render.entity;

import com.cjm721.overloaded.config.OverloadedConfig;
import net.minecraft.client.renderer.entity.model.BipedModel;

public abstract class AbstractRenderMultiArmor extends BipedModel {

  public AbstractRenderMultiArmor() {
    super(
        0,
        0,
        OverloadedConfig.INSTANCE.textureResolutions.multiArmorResolution,
        OverloadedConfig.INSTANCE.textureResolutions.multiArmorResolution);
  }
}
