package com.cjm721.ibhstd.common.block.basic;

import com.cjm721.ibhstd.common.IBHSTDCreativeTabs;
import com.cjm721.ibhstd.common.block.ModBlock;
import com.cjm721.ibhstd.common.block.tile.TileInfiniteBarrel;
import com.cjm721.ibhstd.common.block.tile.TileInfiniteCapacitor;
import com.cjm721.ibhstd.common.block.tile.TileInfiniteTank;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static com.cjm721.ibhstd.IBHSTD.MODID;

/**
 * Created by CJ on 4/8/2017.
 */
public class BlockInfiniteCapacitor extends ModBlock implements ITileEntityProvider {

    public BlockInfiniteCapacitor() {
        super(Material.ROCK);

        setRegistryName("BlockInfiniteCapacitor");
        setUnlocalizedName("BlockInfiniteCapacitor");

        setHardness(10);
        setLightOpacity(0);
        setCreativeTab(IBHSTDCreativeTabs.ENERGY_BLOCKS);
        register();
        GameRegistry.registerTileEntity(TileInfiniteBarrel.class, MODID + ":infiniteCapacitor");
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     *
     * @param worldIn
     * @param meta
     */
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileInfiniteCapacitor();
    }

    @Override
    public void registerRecipe() {

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(MODID, "capacitor"), null);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);

        StateMapperBase ignoreState = new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
                return location;
            }
        };
        ModelLoader.setCustomStateMapper(this, ignoreState);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            if(heldItem == null && hand == EnumHand.MAIN_HAND) {
                TileInfiniteCapacitor barrel = (TileInfiniteCapacitor) worldIn.getTileEntity(pos);

                // TODO Make the exact number show in a tooltip so it can be easier to read at a glance
                double percent = (double) barrel.getStoredAmount() / (double) Long.MAX_VALUE;
                playerIn.addChatComponentMessage(new TextComponentString(String.format("Emergy Amount: %,d  %,.4f%%", barrel.getStoredAmount(), percent)));
                return true;
            }
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
    }
}
