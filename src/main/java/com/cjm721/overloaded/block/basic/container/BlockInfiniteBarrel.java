package com.cjm721.overloaded.block.basic.container;

import com.cjm721.overloaded.block.tile.infinity.TileInfiniteBarrel;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.storage.IHyperType;
import com.cjm721.overloaded.storage.LongItemStack;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;
import static com.cjm721.overloaded.util.CapabilityHyperItem.HYPER_ITEM_HANDLER;

public class BlockInfiniteBarrel extends AbstractBlockInfiniteContainer implements ITileEntityProvider {

    public BlockInfiniteBarrel() {
        super(Material.ROCK);

        setLightOpacity(0);
    }

    @Override
    public void baseInit() {
        setRegistryName("infinite_barrel");
        setUnlocalizedName("infinite_barrel");

        GameRegistry.registerTileEntity(TileInfiniteBarrel.class, MODID + ":infinite_barrel");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(MODID, "infinite_barrel"), null);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);

        ResizeableTextureGenerator.addToTextureQueue(new ResizeableTextureGenerator.ResizableTexture(
                new ResourceLocation(MODID, "textures/blocks/infinite_barrel.png"),
                new ResourceLocation(MODID, "textures/dynamic/blocks/infinite_barrel.png"),
                OverloadedConfig.textureResolutions.blockResolution));
    }

    @Override
    @Nonnull
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileInfiniteBarrel();
    }

    @Override
    public boolean onBlockActivated(@Nonnull World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            ItemStack heldItem = playerIn.getHeldItem(hand);
            if (heldItem.isEmpty() && hand == EnumHand.MAIN_HAND) {
                LongItemStack stack = ((TileInfiniteBarrel) worldIn.getTileEntity(pos)).getStorage().status();
                if (stack.getItemStack().isEmpty()) {
                    playerIn.sendStatusMessage(new TextComponentString("Item: EMPTY"), false);
                } else {
                    playerIn.sendStatusMessage(new TextComponentString("Item: ").appendSibling(stack.getItemStack().getTextComponent()).appendText(String.format(" Amount %,d", stack.getAmount())), false);
                }
                return true;
            }
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, side, hitX, hitY, hitZ);
    }

    @Override
    @Nullable
    protected IHyperType getHyperStack(IBlockAccess world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);

        if (te != null && te instanceof TileInfiniteBarrel) {
            return te.getCapability(HYPER_ITEM_HANDLER, EnumFacing.UP).status();
        }
        return null;
    }
}