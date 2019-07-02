package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.item.ModItem;
import com.cjm721.overloaded.storage.builder.CapabilityContainer;
import com.cjm721.overloaded.storage.itemwrapper.IntEnergyWrapper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

abstract class PowerModItem extends ModItem {

  PowerModItem() {
    super(new Properties().maxStackSize(1));
  }

  PowerModItem(Properties properties) {
    super(properties.maxStackSize(1));
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void addInformation(
      ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    stack
        .getCapability(ENERGY, null)
        .ifPresent(
            handler ->
                tooltip.add(
                    new StringTextComponent(
                        "Energy Stored: "
                            + NumberFormat.getInstance().format(handler.getEnergyStored()))));

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
    return stack
        .getCapability(ENERGY, null)
        .map(storage -> 1D - storage.getEnergyStored() / (double) storage.getMaxEnergyStored())
        .orElse(1D);
  }

  @Nullable
  @Override
  public CompoundNBT getShareTag(ItemStack stack) {
    return stack.getTag();
  }

  @Nullable
  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
    return new CapabilityContainer()
        .addCapability(collectCapabilities(new LinkedList<>(), stack, nbt));
  }

  Collection<ICapabilityProvider> collectCapabilities(
      @Nonnull Collection<ICapabilityProvider> collection,
      ItemStack stack,
      @Nullable CompoundNBT nbt) {
    collection.add(new IntEnergyWrapper(stack));

    return collection;
  }
}
