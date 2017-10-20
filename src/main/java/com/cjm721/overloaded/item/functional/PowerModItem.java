package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.item.ModItem;
import com.cjm721.overloaded.util.itemwrapper.IntEnergyWrapper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.text.NumberFormat;
import java.util.List;

import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public abstract class PowerModItem extends ModItem {

    public PowerModItem() {
        setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        IEnergyStorage handler = stack.getCapability(ENERGY, null);
        tooltip.add("Energy Stored: " + NumberFormat.getInstance().format(handler.getEnergyStored()));

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public boolean showDurabilityBar(ItemStack p_showDurabilityBar_1_) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        IEnergyStorage storage = stack.getCapability(ENERGY, null);

        if (storage != null)
            return 1D - storage.getEnergyStored() / (double) storage.getMaxEnergyStored();

        return 1D;
    }

    @Override
    public boolean getShareTag() {
        return true;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new IntEnergyWrapper(stack);
    }

}
