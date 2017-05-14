package com.cjm721.overloaded.common.block.basic;

import com.cjm721.overloaded.client.render.tile.ItemInterfaceRenderer;
import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.block.ModBlock;
import com.cjm721.overloaded.common.block.tile.TileItemInterface;
import com.cjm721.overloaded.common.config.RecipeEnabledConfig;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
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

public class BlockItemInterface extends ModBlock implements ITileEntityProvider {

    public BlockItemInterface() {
        super(Material.ROCK);

        setRegistryName("item_interface");
        setUnlocalizedName("item_interface");

        setHardness(10);
        setCreativeTab(OverloadedCreativeTabs.TECH);
        register();

        GameRegistry.registerTileEntity(TileItemInterface.class, MODID + ":item_interface");
    }

    @Override
    public void registerRecipe() {
        if(RecipeEnabledConfig.itemInterface) {
            GameRegistry.addRecipe(new ItemStack(this), "NDN", "NEN", "NNN", 'N', Items.NETHER_STAR, 'E', Blocks.CHEST, 'D', Blocks.DRAGON_EGG);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), null));
        ClientRegistry.bindTileEntitySpecialRenderer(TileItemInterface.class, new ItemInterfaceRenderer());
    }

    @Override
    public void breakBlock(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        ((TileItemInterface)worldIn.getTileEntity(pos)).breakBlock();
        super.breakBlock(worldIn, pos, state);
    }

    @Nonnull
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileItemInterface();
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
    public boolean shouldSideBeRendered(IBlockState blockState, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos, EnumFacing side) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(worldIn.isRemote)
            return true;

        TileEntity te = worldIn.getTileEntity(pos);

        if(!(te instanceof TileItemInterface))
            return true;

        TileItemInterface anInterface = (TileItemInterface)te;

        ItemStack stack = anInterface.getStoredItem();
        if(stack == null) {
            ItemStack handStack = playerIn.getHeldItem(hand);

            if(handStack == null)
                return true;

            ItemStack returnedItem = anInterface.insertItem(0, handStack, false);
            playerIn.setHeldItem(hand,returnedItem);
        } else {
            if(playerIn.getHeldItem(hand) != null)
                return true;

            ItemStack toSpawn = anInterface.extractItem(0, 1,false);
            if(toSpawn == null)
               return true;

            worldIn.spawnEntityInWorld(new EntityItem(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, toSpawn));
        }

        return true;
    }
}
