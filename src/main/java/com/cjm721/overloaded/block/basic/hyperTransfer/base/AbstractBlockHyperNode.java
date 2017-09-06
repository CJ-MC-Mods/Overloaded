package com.cjm721.overloaded.block.basic.hyperTransfer.base;

import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.config.OverloadedConfig;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;

public abstract class AbstractBlockHyperNode extends ModBlock implements ITileEntityProvider {
    AbstractBlockHyperNode(@Nonnull Material materialIn) {
        super(materialIn);
        setLightOpacity(0);
    }

    @Nonnull
    protected abstract String getType();

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);

        StateMapperBase ignoreState = new StateMapperBase() {
            @Override
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState iBlockState) {
                return location;
            }
        };
        ModelLoader.setCustomStateMapper(this, ignoreState);

        ResizeableTextureGenerator.addToTextureQueue(new ResizeableTextureGenerator.ResizableTexture(
                new ResourceLocation(MODID, "textures/blocks/hyper_item_sender.png"),
                new ResourceLocation(MODID, "textures/dynamic/blocks/hyper_item_sender.png"),
                OverloadedConfig.textureResolutions.blockResolution));
        ResizeableTextureGenerator.addToTextureQueue(new ResizeableTextureGenerator.ResizableTexture(
                new ResourceLocation(MODID, "textures/blocks/hyper_item_receiver.png"),
                new ResourceLocation(MODID, "textures/dynamic/blocks/hyper_item_receiver.png"),
                OverloadedConfig.textureResolutions.blockResolution));
        ResizeableTextureGenerator.addToTextureQueue(new ResizeableTextureGenerator.ResizableTexture(
                new ResourceLocation(MODID, "textures/blocks/hyper_fluid_sender.png"),
                new ResourceLocation(MODID, "textures/dynamic/blocks/hyper_fluid_sender.png"),
                OverloadedConfig.textureResolutions.blockResolution));
        ResizeableTextureGenerator.addToTextureQueue(new ResizeableTextureGenerator.ResizableTexture(
                new ResourceLocation(MODID, "textures/blocks/hyper_fluid_receiver.png"),
                new ResourceLocation(MODID, "textures/dynamic/blocks/hyper_fluid_receiver.png"),
                OverloadedConfig.textureResolutions.blockResolution));
        ResizeableTextureGenerator.addToTextureQueue(new ResizeableTextureGenerator.ResizableTexture(
                new ResourceLocation(MODID, "textures/blocks/hyper_energy_sender.png"),
                new ResourceLocation(MODID, "textures/dynamic/blocks/hyper_energy_sender.png"),
                OverloadedConfig.textureResolutions.blockResolution));
        ResizeableTextureGenerator.addToTextureQueue(new ResizeableTextureGenerator.ResizableTexture(
                new ResourceLocation(MODID, "textures/blocks/hyper_energy_receiver.png"),
                new ResourceLocation(MODID, "textures/dynamic/blocks/hyper_energy_receiver.png"),
                OverloadedConfig.textureResolutions.blockResolution));
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
}
