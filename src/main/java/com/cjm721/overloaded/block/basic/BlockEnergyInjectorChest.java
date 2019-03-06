package com.cjm721.overloaded.block.basic;


import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.block.tile.TileEnergyInjectorChest;
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

public class BlockEnergyInjectorChest extends AbstractModBlockFacing implements ITileEntityProvider {

    public BlockEnergyInjectorChest() {
        super(Material.ROCK);

        setLightOpacity(0);
    }

    @Override
    public void baseInit() {
        setRegistryName("energy_injector_chest");
        setTranslationKey("energy_injector_chest");

        GameRegistry.registerTileEntity(TileEnergyInjectorChest.class, MODID + ":energy_injector_chest");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        super.registerModel();

        ImageUtil.registerDynamicTexture(
                new ResourceLocation(MODID, "textures/blocks/energy_extractor.png"),
                Overloaded.cachedConfig.textureResolutions.blockResolution);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     *
     * @param worldIn
     * @param meta
     */
    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileEnergyInjectorChest().setFacing(EnumFacing.byIndex(meta));
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isBlockNormalCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }


}
