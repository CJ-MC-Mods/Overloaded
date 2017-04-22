package com.cjm721.overloaded.common.block.basic;

import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.block.ModBlock;
import com.cjm721.overloaded.common.block.tile.infinity.TileInfiniteBarrel;
import com.cjm721.overloaded.common.config.RecipeEnabledConfig;
import com.cjm721.overloaded.common.item.ModItems;
import com.cjm721.overloaded.common.storage.LongItemStack;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockInfiniteBarrel extends ModBlock implements ITileEntityProvider {

    public BlockInfiniteBarrel() {
        super(Material.ROCK);

        setRegistryName("infinite_barrel");
        setUnlocalizedName("infinite_barrel");

        setHardness(10);
        setLightOpacity(0);
        setCreativeTab(OverloadedCreativeTabs.TECH);
        register();
        GameRegistry.registerTileEntity(TileInfiniteBarrel.class, MODID + ":infinite_barrel");
    }

    @Override
    public void registerRecipe() {
        if(RecipeEnabledConfig.infinityBarrel)
            GameRegistry.addRecipe(new ItemStack(this), "GDG", "DCD", "GDG", 'G', Blocks.IRON_BLOCK, 'D', Blocks.DIAMOND_BLOCK, 'C', ModItems.itemCore);

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(MODID, "infinite_barrel"), null);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);
    }

    @Override
    @Nonnull
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileInfiniteBarrel();
    }


    @Override
    public boolean onBlockActivated(@Nonnull World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            ItemStack heldItem = playerIn.getHeldItem(hand);
            if(heldItem.isEmpty() && hand == EnumHand.MAIN_HAND) {
                LongItemStack stack = ((TileInfiniteBarrel) worldIn.getTileEntity(pos)).getStorage().status();
                if(stack.itemStack == null) {
                    playerIn.sendStatusMessage(new TextComponentString("Item: EMPTY"), false);
                } else {
                    playerIn.sendStatusMessage(new TextComponentString("Item: ").appendSibling(stack.itemStack.getTextComponent()).appendText(String.format(" Amount %,d", stack.amount)),false);
                }
                return true;
            }
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, side, hitX, hitY, hitZ);
    }
}