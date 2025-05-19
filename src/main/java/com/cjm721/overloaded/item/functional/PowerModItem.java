package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.item.ModItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.text.NumberFormat;
import java.util.List;

import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

abstract class PowerModItem extends ModItem {

  PowerModItem() {
    super(new Properties().stacksTo(1));
  }

  PowerModItem(Properties properties) {
    super(properties.stacksTo(1));
  }

  @Override
  public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

    @Nullable IEnergyStorage energy = stack
            .getCapability(Capabilities.EnergyStorage.ITEM, null);
    if (energy != null)
                tooltipComponents.add(
                    Component.literal(
                        "Energy Stored: "
                            + NumberFormat.getInstance().format(energy.getEnergyStored())));

    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
  }

//  @Override
//  public boolean canBeDepleted() {
//    return false;
//  }
//
//  @Override
//  public boolean showDurabilityBar(ItemStack p_showDurabilityBar_1_) {
//    return true;
//  }
//
//  @Override
//  public double getDurabilityForDisplay(ItemStack stack) {
//    return stack
//        .getCapability(ENERGY, null)
//        .map(storage -> 1D - storage.getEnergyStored() / (double) storage.getMaxEnergyStored())
//        .orElse(1D);
//  }
//
//  @Nullable
//  @Override
//  public CompoundTag getShareTag(ItemStack stack) {
//    return stack.getTag();
//  }
//
//  @Nullable
//  @Override
//  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
//    return new CapabilityContainer()
//        .addCapability(collectCapabilities(new LinkedList<>(), stack, nbt));
//  }
//
//  Collection<ICapabilityProvider> collectCapabilities(
//      @Nonnull Collection<ICapabilityProvider> collection,
//      ItemStack stack,
//      @Nullable CompoundTag nbt) {
//    collection.add(new IntEnergyWrapper(stack));
//
//    return collection;
//  }
}
