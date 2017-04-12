package com.cjm721.overloaded.common.block.basic;

import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.block.ModBlock;
import com.cjm721.overloaded.common.block.tile.infinity.TileInfiniteBarrel;
import com.cjm721.overloaded.common.storage.LongItemStack;
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
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockInfiniteBarrel extends ModBlock implements ITileEntityProvider {

    public BlockInfiniteBarrel() {
        super(Material.ROCK);

        defaultRegistery();

        setHardness(10);
        setLightOpacity(0);
        setCreativeTab(OverloadedCreativeTabs.UTILITY);
        register();
        GameRegistry.registerTileEntity(TileInfiniteBarrel.class, MODID + ":infiniteBarrel");
    }

    @Override
    public void registerRecipe() {

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(MODID, "infiniteBarrel"), null);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);

        StateMapperBase ignoreState = new StateMapperBase() {
            @Override
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState iBlockState) {
                return location;
            }
        };
        ModelLoader.setCustomStateMapper(this, ignoreState);
    }

    @Override
    @Nonnull
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileInfiniteBarrel();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            if(heldItem == null && hand == EnumHand.MAIN_HAND) {
                LongItemStack stack = ((TileInfiniteBarrel) worldIn.getTileEntity(pos)).getStorage().status();
                if(stack.itemStack == null) {
                    playerIn.addChatComponentMessage(new TextComponentString("Item: EMPTY"));
                } else {
                    playerIn.addChatComponentMessage(new TextComponentString("Item: ").appendSibling(stack.itemStack.getTextComponent()).appendText(String.format(" Amount %,d", stack.amount)));
                }
                return true;
            }
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
    }
}