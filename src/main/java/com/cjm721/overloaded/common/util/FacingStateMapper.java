package com.cjm721.overloaded.common.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Created by CJ on 4/14/2017.
 */
public class FacingStateMapper extends StateMapperBase {

    private final ResourceLocation location;

    public FacingStateMapper(ResourceLocation location) {
        this.location = location;
    }

    @Override
    @Nonnull
    protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
        return new ModelResourceLocation(location, this.getPropertyString(state.getProperties()));
    }
}
