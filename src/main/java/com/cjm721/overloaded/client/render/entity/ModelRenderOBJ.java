package com.cjm721.overloaded.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.*;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;
import org.lwjgl.opengl.GL11;

import java.util.Random;

import static com.cjm721.overloaded.Overloaded.MODID;

@OnlyIn(Dist.CLIENT)
public class ModelRenderOBJ extends ModelRenderer {

    private IBakedModel objModel;

    public ModelRenderOBJ(Model baseModel, IBakedModel model) {
        super(baseModel);
        objModel = model;
    }

    @Override
    public void render(MatrixStack matrix, IVertexBuilder vertex, int light, int overlay, float r, float g, float b, float a) {
        if(this.showModel) {
            matrix.push();
            RenderType.entityTranslucent(PlayerContainer.LOCATION_BLOCKS_TEXTURE);
            this.renderModel(objModel, ItemStack.EMPTY, light, overlay, matrix, vertex);
            matrix.pop();
        }
    }

    private void renderModel(IBakedModel modelIn, ItemStack stack, int combinedLightIn, int combinedOverlayIn, MatrixStack matrixStackIn, IVertexBuilder bufferIn) {
        Random random = new Random();
        long i = 42L;

        for(Direction direction : Direction.values()) {
            random.setSeed(i);
            matrixStackIn.scale(1/16f,1/16f,1/16f);
            Minecraft.getInstance().getItemRenderer().renderQuads(matrixStackIn, bufferIn, modelIn.getQuads(null, direction, random), stack, combinedLightIn, combinedOverlayIn);
        }

        random.setSeed(i);
        Minecraft.getInstance().getItemRenderer().renderQuads(matrixStackIn, bufferIn, modelIn.getQuads(null, null, random), stack, combinedLightIn, combinedOverlayIn);
    }
}