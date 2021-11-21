package com.cjm721.overloaded.tile.functional;

import com.cjm721.overloaded.tile.ModTiles;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;

public class TileItemInterface extends TileEntity implements IItemHandler {

  private ItemStack storedItem;

  public TileItemInterface() {
    super(ModTiles.itemInterface);
    storedItem = ItemStack.EMPTY;
  }

  @Override
  @Nonnull
  public CompoundNBT save(@Nonnull CompoundNBT compound) {
    compound.put("StoredItem", storedItem.serializeNBT());

    return super.save(compound);
  }

  @Override
  public void load(@Nonnull BlockState state, @Nonnull CompoundNBT compound) {
    storedItem = ItemStack.of((CompoundNBT) compound.get("StoredItem"));

    super.load(state, compound);
  }

  @Override
  @Nonnull
  public CompoundNBT getUpdateTag() {
    return save(new CompoundNBT());
  }

  @Nullable
  @Override
  public SUpdateTileEntityPacket getUpdatePacket() {
    CompoundNBT tag = new CompoundNBT();
    save(tag);

    return new SUpdateTileEntityPacket(getBlockPos(), 1, tag);
  }

  @Override
  public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
    this.load(this.getBlockState(), pkt.getTag());
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

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    if ((side == Direction.UP || side == Direction.DOWN) && cap == ITEM_HANDLER_CAPABILITY) {
      return ITEM_HANDLER_CAPABILITY
          .orEmpty(ITEM_HANDLER_CAPABILITY, LazyOptional.of(() -> this))
          .cast();
    }

    LazyOptional<T> t = storedItem.getCapability(cap, side);

    if (t.isPresent()) return t;
    return super.getCapability(cap, side);
  }

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
