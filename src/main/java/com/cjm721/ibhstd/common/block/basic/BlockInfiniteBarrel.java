package com.cjm721.ibhstd.common.block.basic;

import com.cjm721.ibhstd.common.IBHSTDCreativeTabs;
import com.cjm721.ibhstd.common.block.ModBlock;
import com.cjm721.ibhstd.common.block.tile.TileCreativeGenerator;
import com.cjm721.ibhstd.common.block.tile.TileInfiniteBarrel;
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
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import java.util.Iterator;
import java.util.List;

import static com.cjm721.ibhstd.IBHSTD.MODID;

/**
 * Created by CJ on 4/6/2017.
 */
public class BlockInfiniteBarrel extends ModBlock implements ITileEntityProvider {
    public BlockInfiniteBarrel() {
        super(Material.WOOD);

        setRegistryName("BlockInfiniteBarrel");
        setUnlocalizedName("BlockInfiniteBarrel");

        setHardness(10);
        setLightOpacity(0);
        setCreativeTab(IBHSTDCreativeTabs.UTILITY);
        register();
        GameRegistry.registerTileEntity(TileInfiniteBarrel.class, MODID + ":infiniteBarrel");
    }

    @Override
    public void registerRecipe() {

    }


    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(MODID, "barrel"), null);
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
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileInfiniteBarrel();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            if(heldItem == null && hand == EnumHand.MAIN_HAND) {
                TileInfiniteBarrel barrel = (TileInfiniteBarrel) worldIn.getTileEntity(pos);
                ItemStack storedItem = barrel.getStoredItem();
                if(storedItem == null) {
                    playerIn.addChatComponentMessage(new TextComponentString(String.format("Item: EMPTY  Amount: %,d", barrel.getStoredItem(), barrel.getStoredAmount())));
                } else {
                    playerIn.addChatComponentMessage(new TextComponentString("Item: ").appendSibling(barrel.getStoredItem().getTextComponent()).appendText(String.format(" Amount %,d", barrel.getStoredAmount())));
                }
                return true;
            }
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
    }
}