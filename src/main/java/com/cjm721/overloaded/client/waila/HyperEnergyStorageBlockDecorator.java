package com.cjm721.overloaded.client.waila;

import com.cjm721.overloaded.storage.energy.LongEnergyStorage;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class HyperEnergyStorageBlockDecorator implements IWailaDataProvider {

    @Nonnull
    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        LongEnergyStorage storage = new LongEnergyStorage(() -> {});
        storage.deserializeNBT(accessor.getNBTData().getCompoundTag("Energy"));
        tooltip.add(String.format("%,d HFE", storage.status().getAmount()));
        tooltip.add(String.format("%,.10f%%",storage.status().getAmount() / (double)Long.MAX_VALUE));
        return tooltip;
    }

    @Nonnull
    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
        return te.writeToNBT(tag);
    }
}
