package com.cjm721.overloaded.client.render.entity;

import com.cjm721.overloaded.Overloaded;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;

public class RenderMultiHelmet extends AbstractRenderMultiArmor {

  public static RenderMultiHelmet INSTANCE;

  public RenderMultiHelmet(BipedModel baseModel) {
    super(baseModel);

    IBakedModel helmet = Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(Overloaded.MODID, "item/armor/multi_helmet"));

    this.bipedHead.addChild(new ModelRenderOBJ(this, helmet));
  }
}
