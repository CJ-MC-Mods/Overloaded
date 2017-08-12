package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.OverloadedCreativeTabs;
import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.block.tile.TileEnergyExtractor;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.util.FacingStateMapper;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
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

        setRegistryName("energy_extractor");
        setUnlocalizedName("energy_extractor");

        setHardness(10);
        setLightOpacity(0);
        setCreativeTab(OverloadedCreativeTabs.TECH);

        GameRegistry.registerTileEntity(TileEnergyExtractor.class, MODID + ":energy_extractor");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        TileEntity te = new TileEnergyExtractor().setFacing(EnumFacing.getFront(meta));

        return te;
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
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);

        FacingStateMapper stateMapper = new FacingStateMapper(getRegistryName());
        ModelLoader.setCustomStateMapper(this, stateMapper);

        ResizeableTextureGenerator.addToTextureQueue(new ResizeableTextureGenerator.ResizableTexture(
                new ResourceLocation(MODID,"textures/blocks/energy_extractor.png"),
                new ResourceLocation(MODID,"textures/dynamic/blocks/energy_extractor.png"),
                OverloadedConfig.textureResolutions.blockResolution));
    }
}
