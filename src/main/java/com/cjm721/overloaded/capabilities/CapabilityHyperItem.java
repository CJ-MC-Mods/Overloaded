package com.cjm721.overloaded.capabilities;

import com.cjm721.overloaded.storage.item.IHyperHandlerItem;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BlockCapability;

import static com.cjm721.overloaded.Overloaded.MODID;

public class CapabilityHyperItem {

  public static BlockCapability<IHyperHandlerItem, Direction> HYPER_ITEM_HANDLER = BlockCapability.createSided(
          ResourceLocation.fromNamespaceAndPath(MODID,"hyper_item"),
          IHyperHandlerItem.class
  );;

//  public static void register() {
//    CapabilityManager.INSTANCE.register(
//        IHyperHandlerItem.class,
//        new Capability.IStorage<IHyperHandlerItem>() {
//          @Override
//          public INBT writeNBT(
//              Capability<IHyperHandlerItem> capability,
//              @Nonnull IHyperHandlerItem instance,
//              Direction side) {
//            CompoundNBT tag = new CompoundNBT();
//            LongItemStack stack = instance.status();
//            if (!stack.getItemStack().isEmpty()) {
//              tag.putLong("Count", stack.getAmount());
//              tag.put("Item", stack.getItemStack().serializeNBT());
//            }
//            return tag;
//          }
//
//          @Override
//          public void readNBT(
//              Capability<IHyperHandlerItem> capability,
//              @Nonnull IHyperHandlerItem instance,
//              Direction side,
//              @Nonnull INBT nbt) {
//            CompoundNBT tag = (CompoundNBT) nbt;
//
//            if (tag.contains("Item")) {
//              LongItemStack stack =
//                  new LongItemStack(
//                      ItemStack.of((CompoundNBT) tag.get("Item")), tag.getLong("Count"));
//              instance.give(stack, false);
//            }
//          }
//        },
//        () -> new LongItemStorage(() -> {}));
//  }
}
