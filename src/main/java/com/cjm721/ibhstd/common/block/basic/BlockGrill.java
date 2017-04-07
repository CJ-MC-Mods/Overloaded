package com.cjm721.ibhstd.common.block.basic;

import com.cjm721.ibhstd.common.IBHSTDCreativeTabs;
import com.cjm721.ibhstd.common.block.ModBlock;
import com.cjm721.ibhstd.common.block.tile.TileCreativeGenerator;
import com.cjm721.ibhstd.common.block.tile.TileGrill;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import java.util.Random;

import static com.cjm721.ibhstd.IBHSTD.MODID;

/**
 * Created by CJ on 4/7/2017.
 */
public class BlockGrill extends BlockFurnace implements ITileEntityProvider {

    public BlockGrill() {
        super(false);
        setRegistryName("BlockGrill");
        setUnlocalizedName("BlockGrill");

        setHardness(10);
        setLightOpacity(0);
        setCreativeTab(IBHSTDCreativeTabs.UTILITY);
        GameRegistry.register(this);
        registerItemForm();
        GameRegistry.registerTileEntity(TileCreativeGenerator.class, MODID + ":grill");
    }

    private void registerItemForm() {
        GameRegistry.register(new ItemBlock(this), getRegistryName());
    }


    @SideOnly(Side.CLIENT)
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(MODID, "grill"), null);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);

        StateMapperBase ignoreState = new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
                return location;
            }
        };
        ModelLoader.setCustomStateMapper(this, ignoreState);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(this);
    }

}
