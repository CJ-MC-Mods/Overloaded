package com.cjm721.overloaded.client.render.entity;

import com.cjm721.overloaded.Overloaded;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.util.ResourceLocation;

public class RenderMultiChestplate extends AbstractRenderMultiArmor {

  public static RenderMultiChestplate INSTANCE;

  public RenderMultiChestplate(BipedModel baseModel) {
    super(baseModel);

    IBakedModel body = Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(Overloaded.MODID, "item/armor/multi_chestplate_body"));
    IBakedModel leftArm = Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(Overloaded.MODID, "item/armor/multi_chestplate_leftarm"));
    IBakedModel rightArm = Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(Overloaded.MODID, "item/armor/multi_chestplate_rightarm"));

    this.bipedBody.addChild(new ModelRenderOBJ(this, body));
    this.bipedLeftArm.addChild(new ModelRenderOBJ(this, leftArm));
    this.bipedRightArm.addChild(new ModelRenderOBJ(this, rightArm));
  }

  @Override
  public void render(MatrixStack stack, IVertexBuilder buffer, int light, int overlay, float r, float g, float b, float a) {
    super.render(stack, buffer, light, overlay, r, g, b, a);
    this.bipedBody.render(stack, buffer, light, overlay, r, g, b, a);
    this.bipedLeftArm.render(stack, buffer, light, overlay, r, g, b, a);
    this.bipedRightArm.render(stack, buffer, light, overlay, r, g, b, a);
  }
}