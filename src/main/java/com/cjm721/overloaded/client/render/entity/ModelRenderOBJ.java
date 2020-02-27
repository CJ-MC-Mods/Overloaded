package com.cjm721.overloaded.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class ModelRenderOBJ extends ModelRenderer {

    private final IBakedModel objModel;

    public ModelRenderOBJ(Model baseModel, IBakedModel model) {
        super(baseModel);
        objModel = model;
    }

    @Override
    public void render(MatrixStack matrix, IVertexBuilder vertex, int light, int overlay, float r, float g, float b, float a) {
        if(this.showModel) {
            matrix.push();
            vertex = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource().getBuffer(RenderType.cutout());
            this.renderModel(objModel, ItemStack.EMPTY, light, overlay, matrix, vertex);
            matrix.pop();
            Minecraft.getInstance().getRenderTypeBuffers().getBufferSource().finish(RenderType.cutout());
        }
    }

    private void renderModel(IBakedModel modelIn, ItemStack stack, int combinedLightIn, int combinedOverlayIn, MatrixStack matrixStackIn, IVertexBuilder bufferIn) {
        Random random = new Random();
        long i = 42L;

//        matrixStackIn.scale(1/16f,1/16f,1/16f);
//        matrixStackIn.rotate(new Quaternion(Vector3f.YN, 90, true));
//        matrixStackIn.rotate(new Quaternion(Vector3f.ZP, 180, true));
//        matrixStackIn.translate(0,-12,0);
        for(Direction direction : Direction.values()) {
            random.setSeed(i);
            Minecraft.getInstance().getItemRenderer().renderQuads(matrixStackIn, bufferIn, modelIn.getQuads(null, direction, random), stack, combinedLightIn, combinedOverlayIn);
        }

        random.setSeed(i);
        Minecraft.getInstance().getItemRenderer().renderQuads(matrixStackIn, bufferIn, modelIn.getQuads(null, null, random), stack, combinedLightIn, combinedOverlayIn);
    }
}