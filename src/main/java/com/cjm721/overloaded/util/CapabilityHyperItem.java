package com.cjm721.overloaded.util;

import com.cjm721.overloaded.storage.stacks.intint.LongItemStack;
import com.cjm721.overloaded.storage.item.IHyperHandlerItem;
import com.cjm721.overloaded.storage.item.LongItemStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nonnull;

public class CapabilityHyperItem {
  @CapabilityInject(IHyperHandlerItem.class)
  public static Capability<IHyperHandlerItem> HYPER_ITEM_HANDLER = null;

  public static void register() {
    CapabilityManager.INSTANCE.register(
        IHyperHandlerItem.class,
        new Capability.IStorage<IHyperHandlerItem>() {
          @Override
          public INBT writeNBT(
              Capability<IHyperHandlerItem> capability,
              @Nonnull IHyperHandlerItem instance,
              Direction side) {
            CompoundNBT tag = new CompoundNBT();
            LongItemStack stack = instance.status();
            if (!stack.getItemStack().isEmpty()) {
              tag.putLong("Count", stack.getAmount());
              tag.put("Item", stack.getItemStack().serializeNBT());
            }
            return tag;
          }

          @Override
          public void readNBT(
              Capability<IHyperHandlerItem> capability,
              @Nonnull IHyperHandlerItem instance,
              Direction side,
              @Nonnull INBT nbt) {
            CompoundNBT tag = (CompoundNBT) nbt;

            if (tag.contains("Item")) {
              LongItemStack stack =
                  new LongItemStack(
                      ItemStack.read((CompoundNBT) tag.get("Item")), tag.getLong("Count"));
              instance.give(stack, false);
            }
          }
        },
        () -> new LongItemStorage(() -> {}));
  }
}
