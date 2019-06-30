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
  public CompoundNBT write(@Nonnull CompoundNBT compound) {
    compound.put("StoredItem", storedItem.serializeNBT());

    return super.write(compound);
  }

  @Override
  public void read(@Nonnull CompoundNBT compound) {
    storedItem = ItemStack.read((CompoundNBT) compound.get("StoredItem"));

    super.read(compound);
  }

  @Override
  @Nonnull
  public CompoundNBT getUpdateTag() {
    return write(new CompoundNBT());
  }

  @Nullable
  @Override
  public SUpdateTileEntityPacket getUpdatePacket() {
    CompoundNBT tag = new CompoundNBT();
    write(tag);

    return new SUpdateTileEntityPacket(getPos(), 1, tag);
  }

  @Override
  public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
    this.read(pkt.getNbtCompound());
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
          markDirty();
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
        markDirty();
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
      markDirty();
      updateClient();
    }

    return copy;
  }

  private void updateClient() {
    BlockState state = getWorld().getBlockState(getPos());
    getWorld().notifyBlockUpdate(this.getPos(), state, state, 3);
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
      this.getWorld()
          .addEntity(
              new ItemEntity(
                  this.getWorld(), getPos().getX(), getPos().getY(), getPos().getZ(), storedItem));
  }
}
