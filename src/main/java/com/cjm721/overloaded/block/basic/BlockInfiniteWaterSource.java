package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.block.tile.TileInfiniteWaterSource;
import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;
import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

public class BlockInfiniteWaterSource extends ModBlock {

    public BlockInfiniteWaterSource() {
        super(getDefaultProperties());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public void baseInit() {
        setRegistryName("infinite_water_source");
//        setTranslationKey("infinite_water_source");

//        GameRegistry.registerTileEntity(TileInfiniteWaterSource.class, MODID + ":infinite_water_source");
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(MODID, "infinite_water_source"), null);
//        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);

        ImageUtil.registerDynamicTexture(
                new ResourceLocation(MODID, "textures/blocks/infinite_water_source.png"),
                Overloaded.cachedConfig.textureResolutions.blockResolution);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    @Nonnull
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @Nonnull
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileInfiniteWaterSource();
    }

    @Override
    public void onBlockClicked(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (!world.isRemote && player.getActiveHand() == Hand.MAIN_HAND) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof TileInfiniteWaterSource) {
                IFluidHandler handler = te.getCapability(FLUID_HANDLER_CAPABILITY).orElse(null);
                FluidActionResult result = FluidUtil.tryFillContainerAndStow(player.getActiveItemStack(), handler, null, Integer.MAX_VALUE, player, true);

                if (result.isSuccess()){
                    player.setHeldItem(Hand.MAIN_HAND, result.getResult());
                }
            }
        }
    }
}
