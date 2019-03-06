package com.cjm721.overloaded.block.basic.container;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.block.tile.infinity.TileInfiniteCapacitor;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.storage.IHyperType;
import com.cjm721.overloaded.storage.LongEnergyStack;
import mcjty.theoneprobe.api.*;
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
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;
import static com.cjm721.overloaded.util.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;

@Optional.Interface(iface = "mcjty.theoneprobe.api.IProbeInfoAccessor", modid = "theoneprobe")
public class BlockInfiniteCapacitor extends AbstractBlockInfiniteContainer implements ITileEntityProvider, IProbeInfoAccessor {

    public BlockInfiniteCapacitor() {
        super(Material.ROCK);

        setLightOpacity(0);
    }

    @Override
    public void baseInit() {
        setRegistryName("infinite_capacitor");
        setTranslationKey("infinite_capacitor");

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
                Overloaded.cachedConfig.textureResolutions.blockResolution));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            ItemStack heldItem = playerIn.getHeldItem(hand);
            if (heldItem.isEmpty() && hand == EnumHand.MAIN_HAND) {
                LongEnergyStack stack = ((TileInfiniteCapacitor) worldIn.getTileEntity(pos)).getStorage().status();

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

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        TileEntity te = world.getTileEntity(data.getPos());
        if (te != null && te instanceof TileInfiniteCapacitor) {
            IProgressStyle style = probeInfo.defaultProgressStyle().showText(true).numberFormat(NumberFormat.COMPACT).suffix("RF");
            probeInfo.progress(((TileInfiniteCapacitor) te).getStorage().status().getAmount(), Long.MAX_VALUE, style);
        }
    }
}
