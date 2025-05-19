package com.cjm721.overloaded.tile.functional;

import com.cjm721.overloaded.tile.ModTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class TileItemInterface extends BlockEntity implements IItemHandler {

  private ItemStack storedItem;

  public TileItemInterface(BlockPos pos, BlockState blockState) {
    super(ModTiles.itemInterface.get(), pos, blockState);
    storedItem = ItemStack.EMPTY;
  }

  @Override
  protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
    super.saveAdditional(compound, registries);
    if (!storedItem.isEmpty()) {
      compound.put("StoredItem", storedItem.save(registries));
    }
  }

  @Override
  protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
    super.loadAdditional(compound, registries);
    Tag itemTag = compound.get("StoredItem");
    if (itemTag != null)
    {
      storedItem = ItemStack.parse(registries, itemTag).orElse(ItemStack.EMPTY);
    }else{
      storedItem = ItemStack.EMPTY;
    }
  }
//

  @Override
  public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
    CompoundTag tag = super.getUpdateTag(registries);
    saveAdditional(tag, registries);
    return tag;
  }

  @Override
  public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
//    super.getUpdatePacket().;
//      CompoundTag tag = new CompoundTag();
//    saveAdditional(tag, this.getLevel().registryAccess());

    return ClientboundBlockEntityDataPacket.create(this);
  }


  @Override
  public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
    super.onDataPacket(net, pkt, lookupProvider);
    this.loadAdditional(pkt.getTag(), lookupProvider);
  }

  @Override
  public int getSlots() {
    return 1;
  }

  @Nonnull
  @Override
  public ItemStack getStackInSlot(int slot) {
    return storedItem;
  }

  @Nonnull
  @Override
  public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
    if (storedItem.isEmpty()) {
      if (stack.getCount() == 1) {
        if (!simulate) {
          this.storedItem = stack;
          updateClient();
          setChanged();
        }
        return ItemStack.EMPTY;
      }

      ItemStack storedCopy = stack.copy();
      storedCopy.setCount(1);

      ItemStack returnCopy = stack.copy();

      returnCopy.setCount(stack.getCount() - 1);

      if (!simulate) {
        this.storedItem = storedCopy;
        updateClient();
        setChanged();
      }

      return returnCopy;
    }
    return stack;
  }

  @Nonnull
  @Override
  public ItemStack extractItem(int slot, int amount, boolean simulate) {
    ItemStack copy = storedItem.copy();
    copy.setCount(Math.min(copy.getCount(), amount));

    if (!simulate) {
      storedItem.setCount(storedItem.getCount() - copy.getCount());
      if (storedItem.getCount() == 0) {
        storedItem = ItemStack.EMPTY;
      }
      setChanged();
      updateClient();
    }

    return copy;
  }

  private void updateClient() {
    BlockState state = getLevel().getBlockState(getBlockPos());
    getLevel().sendBlockUpdated(this.getBlockPos(), state, state, 3);
  }

  @Override
  public int getSlotLimit(int slot) {
    return 1;
  }

  @Override
  public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
    return true;
  }

//  @Nonnull
//  @Override
//  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
//    if ((side == Direction.UP || side == Direction.DOWN) && cap == ITEM_HANDLER_CAPABILITY) {
//      return ITEM_HANDLER_CAPABILITY
//          .orEmpty(ITEM_HANDLER_CAPABILITY, LazyOptional.of(() -> this))
//          .cast();
//    }
//
//    LazyOptional<T> t = storedItem.getCapability(cap, side);
//
//    if (t.isPresent()) return t;
//    return super.getCapability(cap, side);
//  }

  public ItemStack getStoredItem() {
    return storedItem;
  }

  public void breakBlock() {
    if (!storedItem.isEmpty())
      this.getLevel()
          .addFreshEntity(
              new ItemEntity(
                  this.getLevel(), getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), storedItem));
  }
}
