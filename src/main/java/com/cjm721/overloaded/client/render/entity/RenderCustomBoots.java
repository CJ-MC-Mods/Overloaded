package com.cjm721.overloaded.client.render.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;

public class RenderCustomBoots  extends ModelBiped {

    public RenderCustomBoots() {
        super(0, 0, 1024, 1024);

        this.bipedRightLeg.cubeList.clear();
        this.bipedLeftLeg.cubeList.clear();

        ModelRenderOBJ rightBoot = new ModelRenderOBJ(this, new ResourceLocation(MODID, "models/item/armor/custom_right_boot.obj"));
        ModelRenderOBJ leftBoot = new ModelRenderOBJ(this, new ResourceLocation(MODID, "models/item/armor/custom_left_boot.obj"));

        this.bipedRightLeg.addChild(rightBoot);
        this.bipedLeftLeg.addChild(leftBoot);

//        rightBoot.offsetY = 0.76F;
//        rightBoot.offsetX = -0.03F;
//        leftBoot.offsetY = 0.76F;
//        leftBoot.offsetX = 0.03F;
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
