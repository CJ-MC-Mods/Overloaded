package com.cjm721.overloaded.client.render.entity;

import com.cjm721.overloaded.Overloaded;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.util.ResourceLocation;

public class RenderMultiLeggings extends AbstractRenderMultiArmor {

  public static RenderMultiLeggings INSTANCE;

  public RenderMultiLeggings(BipedModel baseModel) {
    super(baseModel, 10f);
    IBakedModel leftLeg = Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(Overloaded.MODID, "item/armor/multi_left_leg"));
    IBakedModel rightLeg = Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(Overloaded.MODID, "item/armor/multi_right_leg"));

//    this.bipedLeftLeg.addChild(new ModelRenderOBJ(this, leftLeg));
    this.bipedRightLeg.addChild(new ModelRenderOBJ(this, rightLeg));
  }
}
