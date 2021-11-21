package com.cjm721.overloaded.client.render.entity;

import com.cjm721.overloaded.Overloaded;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.util.ResourceLocation;

public class RenderMultiBoots extends AbstractRenderMultiArmor {

    public static RenderMultiBoots INSTANCE;

    public RenderMultiBoots(BipedModel baseModel) {
        super(baseModel,-10);
        IBakedModel leftLeg = Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(Overloaded.MODID, "item/armor/multi_left_boot"));
        IBakedModel rightLeg = Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(Overloaded.MODID, "item/armor/multi_right_boot"));

        this.leftLeg.addChild(new ModelRenderOBJ(this, leftLeg));
        this.rightLeg.addChild(new ModelRenderOBJ(this, rightLeg));
    }
}
