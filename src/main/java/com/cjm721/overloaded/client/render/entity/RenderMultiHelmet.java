package com.cjm721.overloaded.client.render.entity;

import com.cjm721.overloaded.Overloaded;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.util.ResourceLocation;

public class RenderMultiHelmet extends AbstractRenderMultiArmor {

  public static RenderMultiHelmet INSTANCE;

  public RenderMultiHelmet(BipedModel baseModel) {
    super(baseModel);

    IBakedModel helmet = Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(Overloaded.MODID, "item/armor/multi_helmet"));

    this.head.addChild(new ModelRenderOBJ(this, helmet));
  }
}
