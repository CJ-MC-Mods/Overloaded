package com.cjm721.overloaded.client.render.entity;

import com.cjm721.overloaded.config.OverloadedConfig;
import net.minecraft.client.renderer.entity.model.BipedModel;

public abstract class AbstractRenderMultiArmor extends BipedModel {

  final BipedModel baseModel;

  public AbstractRenderMultiArmor(BipedModel baseModel) {
    super(
        0,
        0,
        OverloadedConfig.INSTANCE.textureResolutions.multiArmorResolution,
        OverloadedConfig.INSTANCE.textureResolutions.multiArmorResolution);
    this.baseModel = baseModel;
  }

  public AbstractRenderMultiArmor(BipedModel baseModel, float yOffSet) {
    super(
        0,
        yOffSet,
        OverloadedConfig.INSTANCE.textureResolutions.multiArmorResolution,
        OverloadedConfig.INSTANCE.textureResolutions.multiArmorResolution);
    this.baseModel = baseModel;
  }
}
