package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.block.tile.TileEnergyExtractor;
import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.util.FacingStateMapper;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockEnergyExtractor extends AbstractModBlockFacing implements ITileEntityProvider {

    public BlockEnergyExtractor() {
        super(Material.ROCK);

        setLightOpacity(0);
    }

    @Override
    public void baseInit() {
        setRegistryName("energy_extractor");
        setTranslationKey("energy_extractor");

        GameRegistry.registerTileEntity(TileEnergyExtractor.class, MODID + ":energy_extractor");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileEnergyExtractor().setFacing(EnumFacing.byIndex(meta));
    }

    @Override
    public boolean isBlockNormalCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }


    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerModel() {
        super.registerModel();

        ImageUtil.registerDynamicTexture(
                new ResourceLocation(MODID, "textures/blocks/energy_extractor.png"),
                Overloaded.cachedConfig.textureResolutions.blockResolution);
    }
}
