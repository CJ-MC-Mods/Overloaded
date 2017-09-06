package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.OverloadedCreativeTabs;
import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.block.tile.TileInfiniteWaterSource;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.config.OverloadedConfig;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;
import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

public class BlockInfiniteWaterSource extends ModBlock implements ITileEntityProvider {

    public BlockInfiniteWaterSource() {
        super(Material.GLASS);

        setRegistryName("infinite_water_source");
        setUnlocalizedName("infinite_water_source");

        setLightOpacity(0);
        setCreativeTab(OverloadedCreativeTabs.TECH);

        GameRegistry.registerTileEntity(TileInfiniteWaterSource.class, MODID + ":infinite_water_source");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(MODID, "infinite_water_source"), null);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);

        ResizeableTextureGenerator.addToTextureQueue(new ResizeableTextureGenerator.ResizableTexture(
                new ResourceLocation(MODID, "textures/blocks/infinite_water_source.png"),
                new ResourceLocation(MODID, "textures/dynamic/blocks/infinite_water_source.png"),
                OverloadedConfig.textureResolutions.blockResolution));
    }

    @SideOnly(Side.CLIENT)
    @Override
    @Nonnull
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @Nonnull
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileInfiniteWaterSource();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote && hand == EnumHand.MAIN_HAND) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te != null && te instanceof TileInfiniteWaterSource) {
                IFluidHandler handler = te.getCapability(FLUID_HANDLER_CAPABILITY, facing);
                FluidActionResult result = FluidUtil.tryFillContainerAndStow(playerIn.getHeldItem(hand), handler, null, Integer.MAX_VALUE, playerIn); // FluidUtil.interactWithFluidHandler(playerIn.getHeldItem(hand), te.getCapability(FLUID_HANDLER_CAPABILITY, facing), playerIn);

                if (result.isSuccess())
                    playerIn.setHeldItem(hand, result.getResult());
            }
        }
        return true;
    }
}
