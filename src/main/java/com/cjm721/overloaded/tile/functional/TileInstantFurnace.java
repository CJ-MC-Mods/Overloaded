package com.cjm721.overloaded.tile.functional;

import com.cjm721.overloaded.network.container.InstantFurnaceContainer;
import com.cjm721.overloaded.storage.crafting.FurnaceProcessor;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class TileInstantFurnace extends BaseContainerBlockEntity implements IDataUpdate {

  @Nonnull private final FurnaceProcessor processingStorage;
//  @Nonnull private final LazyOptional<FurnaceProcessor> capability;

  public TileInstantFurnace(BlockPos pos, BlockState blockState) {
    super(ModTiles.instantFurnace.get(), pos, blockState);

    processingStorage = new FurnaceProcessor(this::getLevel, Integer.MAX_VALUE, 9, this);
//    capability = LazyOptional.of(() -> processingStorage);
  }

  @Override
  @Nonnull
  protected Component getDefaultName() {
    return Component.literal("Instant Furnace");
  }

  @Override
  protected NonNullList<ItemStack> getItems() {
    return null;
  }

  @Override
  protected void setItems(NonNullList<ItemStack> items) {

  }

  @Override
  protected AbstractContainerMenu createMenu(int id, Inventory playerInventory) {
    return new InstantFurnaceContainer(id, playerInventory);
  }
//
//  @Nonnull
//  @Override
//  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
//    if ( cap == ENERGY) {
//      return capability.cast();
//    }
//
//    if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
//      if (side == null) {
//        side = Direction.NORTH;
//      }
//      switch (side) {
//        case UP:
//          return capability.lazyMap(FurnaceProcessor::inputIItemHandler).cast();
//        case NORTH:
//        case EAST:
//        case SOUTH:
//        case WEST:
//          return capability.cast();
//        case DOWN:
//          return capability.lazyMap(FurnaceProcessor::outputIItemHandler).cast();
//      }
//    }
//
//    return super.getCapability(cap, side);
//  }

  @Override
  public int getContainerSize() {
    return processingStorage.getSlots();
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  @Nonnull
  public net.minecraft.world.item.ItemStack getItem(int index) {
    return processingStorage.getStackInSlot(index);
  }

  @Override
  @Nonnull
  public ItemStack removeItem(int index, int count) {
    return processingStorage.extractItem(index, count, false);
  }

  @Override
  @Nonnull
  public ItemStack removeItemNoUpdate(int index) {
    return processingStorage.extractItem(index, Integer.MAX_VALUE, false);
  }

  @Override
  public void setItem(int index, @Nonnull ItemStack stack) {
    processingStorage.setItem(index, stack);
  }

  @Override
  public boolean stillValid(@Nonnull Player player) {
    // TODO Do I want to make sure the player is nearby?
    return true;
  }

  @Override
  public void clearContent() {
    throw new RuntimeException("clear is called");
  }

//  @Override
//  public void load(@Nonnull BlockState state, @Nonnull CompoundTag compound) {
//    super.load(state, compound);
//    if (compound.contains("Processor")) {
//      processingStorage.deserializeNBT((CompoundTag) compound.get("Processor"));
//    }
//  }
//
////  @Override
//  @Nonnull
//  public CompoundTag save(CompoundTag compound) {
//    compound.put("Processor", processingStorage.serializeNBT());
//    return super.save(compound);
//  }

  @Override
  public void dataUpdated() {
    setChanged();
  }
}
