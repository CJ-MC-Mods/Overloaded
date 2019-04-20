package com.cjm721.overloaded.block.fluid;

import com.cjm721.overloaded.proxy.CommonProxy;
import com.cjm721.overloaded.util.IModRegistrable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class BlockPureMatterFluid extends BlockFluidClassic implements IModRegistrable {

    public BlockPureMatterFluid() {
        super(CommonProxy.pureMatter, Material.WATER);
        setRegistryName("pure_matter");
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void registerModel() {
        ModelLoader.setCustomStateMapper(this, new FluidStateMapper());
    }

    @OnlyIn(Dist.CLIENT)
    @Nonnull
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    private class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition {
        final ModelResourceLocation location;

        FluidStateMapper() {
            this.location = new ModelResourceLocation(BlockPureMatterFluid.this.getRegistryName(), "all");
        }

        @Nonnull
        @Override
        protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
            return location;
        }

        @Nonnull
        @Override
        public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack) {
            return location;
        }
    }
}
