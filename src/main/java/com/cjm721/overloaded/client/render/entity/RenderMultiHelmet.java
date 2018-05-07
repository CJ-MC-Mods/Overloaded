package com.cjm721.overloaded.client.render.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;
import static com.cjm721.overloaded.config.OverloadedConfig.textureResolutions;

public class RenderMultiHelmet extends ModelBiped {

    public RenderMultiHelmet() {
        super(0, 0, textureResolutions.multiArmorResolution, textureResolutions.multiArmorResolution);

        this.bipedHead.cubeList.clear();
        //this.bipedHeadwear.cubeList.clear();

        ModelRenderOBJ head = new ModelRenderOBJ(this, new ResourceLocation(MODID, "item/armor/multi_helmet.obj"));
        head.offsetY = -0.1F;
        head.offsetX = -0.033F;
        head.offsetZ = 0.1F;
        head.scale = 1F / 13F;

        this.bipedHead.addChild(head);


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

        this.bipedHead.render(f5);

        GlStateManager.popMatrix();

    }
}
