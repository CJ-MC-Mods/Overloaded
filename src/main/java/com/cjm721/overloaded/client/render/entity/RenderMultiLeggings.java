package com.cjm721.overloaded.client.render.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;

public class RenderMultiLeggings extends ModelBiped {

    public RenderMultiLeggings() {
        super(0, 0, 1024, 1024);

        this.bipedRightLeg.cubeList.clear();
        this.bipedLeftLeg.cubeList.clear();


        ModelRenderOBJ rightLeg = new ModelRenderOBJ(this, new ResourceLocation(MODID, "models/item/armor/multi_right_leg.obj"));
        ModelRenderOBJ leftLeg = new ModelRenderOBJ(this, new ResourceLocation(MODID, "models/item/armor/multi_left_leg.obj"));

        this.bipedRightLeg.addChild(rightLeg);
        this.bipedLeftLeg.addChild(leftLeg);
    }

    @Override
    public void render(@Nullable Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {

        if (entity != null && entity instanceof EntityPlayer) {
            super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        }

        GlStateManager.pushMatrix();
        if (entity != null && entity.isSneaking()) {
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
        }

        this.bipedRightLeg.render(1);
        this.bipedLeftLeg.render(1);

        GlStateManager.popMatrix();

    }
}