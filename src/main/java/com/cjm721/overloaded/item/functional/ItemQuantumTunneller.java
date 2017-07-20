package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.OverloadedCreativeTabs;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.item.ModItem;
import com.cjm721.overloaded.util.itemwrapper.IntEnergyWrapper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.text.NumberFormat;
import java.util.List;

import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class ItemQuantumTunneller extends ModItem {

    public ItemQuantumTunneller() {
        setMaxStackSize(1);
        setRegistryName("quantum_tunneller");
        setUnlocalizedName("quantum_tunneller");
        setCreativeTab(OverloadedCreativeTabs.TECH);
        Overloaded.proxy.itemToRegister.add(this);
    }

    @Override
    public void registerModel() {

    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
        IEnergyStorage energy = stack.getCapability(ENERGY,null);

        int extracted = energy.extractEnergy(OverloadedConfig.multiArmorConfig.noClipEnergyPerTick,false);

        if(extracted == OverloadedConfig.multiArmorConfig.noClipEnergyPerTick)
            player.noClip = true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        IEnergyStorage handler = stack.getCapability(ENERGY, null);
        tooltip.add("Energy Stored: " + NumberFormat.getInstance().format(handler.getEnergyStored()));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    @Nonnull
    public EnumAction getItemUseAction(ItemStack stack) {
        IEnergyStorage energy = stack.getCapability(ENERGY,null);
        if (energy.getEnergyStored() >= OverloadedConfig.multiArmorConfig.noClipEnergyPerTick)
            return EnumAction.BLOCK;
        return EnumAction.NONE;
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn,@Nonnull EntityPlayer playerIn,@Nonnull EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        IEnergyStorage energy = stack.getCapability(ENERGY,null);
        if (energy.getEnergyStored() >= OverloadedConfig.multiArmorConfig.noClipEnergyPerTick) {
            playerIn.setActiveHand(handIn);
            onUsingTick(stack,playerIn,0);
            return new ActionResult<>(EnumActionResult.SUCCESS,stack);
        }
        return new ActionResult<>(EnumActionResult.FAIL,stack);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        IEnergyStorage storage = stack.getCapability(ENERGY, null);

        if(storage != null)
            return 1D - storage.getEnergyStored() / (double)storage.getMaxEnergyStored();

        return 1D;
    }

    @Override
    public boolean getShareTag() {
        return true;
    }

    @Override
    public boolean showDurabilityBar(ItemStack p_showDurabilityBar_1_) {
        return true;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new IntEnergyWrapper(stack);
    }
}
