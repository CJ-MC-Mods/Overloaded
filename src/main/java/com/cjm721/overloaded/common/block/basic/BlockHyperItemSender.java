package com.cjm721.ibhstd.common.block.basic;

import com.cjm721.ibhstd.common.IBHSTDCreativeTabs;
import com.cjm721.ibhstd.common.block.ModBlock;
import com.cjm721.ibhstd.common.block.tile.TileHyperItemSender;
import com.cjm721.ibhstd.common.item.ModItems;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;

import static com.cjm721.ibhstd.IBHSTD.MODID;

/**
 * Created by CJ on 4/8/2017.
 */
public class BlockHyperItemSender extends ModBlock implements ITileEntityProvider {

    public BlockHyperItemSender() {
        super(Material.ROCK);

        setRegistryName("BlockHyperItemSender");
        setUnlocalizedName("BlockHyperItemSender");

        setHardness(10);
        setLightOpacity(0);
        setCreativeTab(IBHSTDCreativeTabs.UTILITY);
        register();
        GameRegistry.registerTileEntity(TileHyperItemSender.class, MODID + ":hyperItemSender");
    }

    @Override
    public void registerRecipe() {

    }

    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(MODID, "sideTest"), null);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);

        StateMapperBase ignoreState = new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
                return location;
            }
        };
        ModelLoader.setCustomStateMapper(this, ignoreState);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     *
     * @param worldIn
     * @param meta
     */
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileHyperItemSender();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(hand == EnumHand.MAIN_HAND) {
            if (heldItem == null) {
                String message = ((TileHyperItemSender) worldIn.getTileEntity(pos)).getRightClickMessage();
                playerIn.addChatComponentMessage(new TextComponentString(message));
            } else if (heldItem.getItem().equals(ModItems.linkingCard)) {
                NBTTagCompound tag = heldItem.getTagCompound();
                if (tag != null) {
                    int worldID = tag.getInteger("WORLD");
                    int x = tag.getInteger("X");
                    int y = tag.getInteger("Y");
                    int z = tag.getInteger("Z");

                    bindToPartner(worldIn, pos, worldID, new BlockPos(x, y, z));
                    heldItem.setTagCompound(null);
                    if (worldIn.isRemote) {
                        playerIn.addChatComponentMessage(new TextComponentString("Bound Hyper Nodes"));
                    }
                }
            }
            return true;
        }

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
    }

    protected void bindToPartner(World world, BlockPos pos, int partnerWorldId, BlockPos partnerPos) {
        ((TileHyperItemSender)world.getTileEntity(pos)).setPartnetInfo(partnerWorldId, partnerPos);
    }
}
