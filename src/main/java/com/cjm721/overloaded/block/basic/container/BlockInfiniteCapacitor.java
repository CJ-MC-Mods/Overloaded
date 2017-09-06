package com.cjm721.overloaded.block.basic.container;

import com.cjm721.overloaded.OverloadedCreativeTabs;
import com.cjm721.overloaded.block.tile.infinity.TileInfiniteCapacitor;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.storage.IHyperType;
import com.cjm721.overloaded.storage.LongEnergyStack;
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
import static com.cjm721.overloaded.util.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;

public class BlockInfiniteCapacitor extends AbstractBlockInfiniteContainer implements ITileEntityProvider {

    public BlockInfiniteCapacitor() {
        super(Material.ROCK);

        setRegistryName("infinite_capacitor");
        setUnlocalizedName("infinite_capacitor");

        setLightOpacity(0);
        setCreativeTab(OverloadedCreativeTabs.TECH);

        GameRegistry.registerTileEntity(TileInfiniteCapacitor.class, MODID + ":infinite_capacitor");
    }

    @Override
    @Nonnull
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileInfiniteCapacitor();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);

        ResizeableTextureGenerator.addToTextureQueue(new ResizeableTextureGenerator.ResizableTexture(
                new ResourceLocation(MODID, "textures/blocks/infinite_capacitor.png"),
                new ResourceLocation(MODID, "textures/dynamic/blocks/infinite_capacitor.png"),
                OverloadedConfig.textureResolutions.blockResolution));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            ItemStack heldItem = playerIn.getHeldItem(hand);
            if (heldItem.isEmpty() && hand == EnumHand.MAIN_HAND) {
                LongEnergyStack stack = ((TileInfiniteCapacitor) worldIn.getTileEntity(pos)).getStorage().status();

                // TODO Make the exact number show in a tooltip so it can be easier to read at a glance
                double percent = (double) stack.getAmount() / (double) Long.MAX_VALUE;
                playerIn.sendStatusMessage(new TextComponentString(String.format("Energy Amount: %,d  %,.4f%%", stack.getAmount(), percent)), false);
                return true;
            }
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, side, hitX, hitY, hitZ);
    }

    @Nullable
    @Override
    protected IHyperType getHyperStack(IBlockAccess world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);

        if (te != null && te instanceof TileInfiniteCapacitor) {
            return te.getCapability(HYPER_ENERGY_HANDLER, EnumFacing.UP).status();
        }
        return null;
    }
}
