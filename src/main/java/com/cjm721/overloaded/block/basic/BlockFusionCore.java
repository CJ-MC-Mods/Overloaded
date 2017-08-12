package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.client.render.tile.FusionCoreRenderer;
import com.cjm721.overloaded.OverloadedCreativeTabs;
import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.block.tile.TileFusionCore;
import com.cjm721.overloaded.config.OverloadedConfig;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;
import static net.minecraft.util.BlockRenderLayer.TRANSLUCENT;

public class BlockFusionCore extends ModBlock implements ITileEntityProvider {

    public BlockFusionCore() {
        super(Material.ROCK);

        setRegistryName("fusion_core");
        setUnlocalizedName("fusion_core");

        setHardness(10);
        setCreativeTab(OverloadedCreativeTabs.TECH);

        GameRegistry.registerTileEntity(TileFusionCore.class, MODID + ":fusion_core");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), null));
        ClientRegistry.bindTileEntitySpecialRenderer(TileFusionCore.class, new FusionCoreRenderer());

        ResizeableTextureGenerator.addToTextureQueue(new ResizeableTextureGenerator.ResizableTexture(
                new ResourceLocation(MODID,"textures/blocks/sun/yellow.png"),
                new ResourceLocation(MODID,"textures/dynamic/blocks/sun/yellow.png"),
                OverloadedConfig.textureResolutions.blockResolution));
        ResizeableTextureGenerator.addToTextureQueue(new ResizeableTextureGenerator.ResizableTexture(
                new ResourceLocation(MODID,"textures/blocks/sun/red_two.png"),
                new ResourceLocation(MODID,"textures/dynamic/blocks/sun/red_two.png"),
                OverloadedConfig.textureResolutions.blockResolution));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileFusionCore();
    }

    @Override
    public boolean isBlockNormalCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return TRANSLUCENT;
    }

    @Override
    @Nonnull
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }
}
