package com.cjm721.overloaded.client.render.block.compressed;


import com.google.common.base.Function;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.Collections;

@SideOnly(Side.CLIENT)
public class CompressedModel implements IModel {

    private final IBlockState state;

    CompressedModel(IBlockState state) {
        this.state = state;
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return new CompressedBakedModel(format, this.state, bakedTextureGetter);
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return Collections.singleton(new ResourceLocation("overloaded", "dynamic/logo"));
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        return Collections.singleton(new ResourceLocation("overloaded", "dynamic/logo"));
    }

    @Override
    public IModelState getDefaultState() {
        return TRSRTransformation.identity();
    }
}