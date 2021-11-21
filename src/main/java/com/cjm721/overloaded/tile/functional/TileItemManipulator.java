package com.cjm721.overloaded.tile.functional;

import com.cjm721.overloaded.tile.ModTiles;
import com.mojang.authlib.GameProfile;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.UUID;

import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;
import static net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;

public class TileItemManipulator extends TileEntity implements ITickableTileEntity {

  private static final GameProfile FAKEPLAYER =
      new GameProfile(
          UUID.fromString("85824917-23F6-4B28-8B12-FAED16A3F66B"), "[Overloaded:Item_Manipulator]");

  private EnergyStorage energyStorage;
  private ItemStackHandler itemStack;
  private WeakReference<FakePlayer> player;
  private Direction facing;

  public TileItemManipulator() {
    super(ModTiles.itemManipulator);
    itemStack = new ItemStackHandler();
    energyStorage = new EnergyStorage(Integer.MAX_VALUE, Integer.MAX_VALUE, 0);
  }

  @Override
  public void load(@Nonnull BlockState state, CompoundNBT compound) {
    if (compound.contains("Item")) {
      itemStack.deserializeNBT((CompoundNBT) compound.get("Item"));
    }

    if (compound.contains("Energy")) {
      energyStorage =
          new EnergyStorage(Integer.MAX_VALUE, Integer.MAX_VALUE, 0, compound.getInt("Energy"));
    }
  }

  @Override
  @Nonnull
  public CompoundNBT save(CompoundNBT compound) {
    compound.put("Item", itemStack.serializeNBT());
    compound.putInt("Energy", energyStorage.getEnergyStored());
    return super.save(compound);
  }

  @Override
  public void tick() {
    if (this.getLevel().isClientSide) {
      return;
    }

    ItemStack currentItem = itemStack.getStackInSlot(0);
    if (currentItem.isEmpty()) return;

    FakePlayer player = getPlayer();

    BlockPos.Mutable blockPos = this.getBlockPos().mutable();
    //        for (int i = 0; i < player.interactionManager.getBlockReachDistance(); i++) {
    //            if (!this.getWorld().isAirBlock(blockPos.move(this.facing))) {
    //                EnumActionResult result = currentItem.getItem().onItemUse(player, getWorld(),
    // blockPos, Hand.MAIN_HAND, facing.getOpposite(), 0.5f, 0.5f, 0.5f);
    //                System.out.println(result);
    //                currentItem.onItemUse(player,this.getWorld(),blockPos,Hand.MAIN_HAND,
    //                        this.facing.getOpposite(),0.5f,0.5f,0.5f);
    //
    // player.interactionManager.processRightClickBlock(player,this.getWorld(),currentItem,Hand.MAIN_HAND,
    //                        blockPos,this.facing.getOpposite(),0.5f,0.5f,0.5f);
    //                break;
    //            }
    //        }
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(
      @Nonnull Capability<T> capability, @Nullable Direction facing) {
    if (capability == ENERGY) return LazyOptional.of(() -> energyStorage).cast();
    if (capability == ITEM_HANDLER_CAPABILITY) return LazyOptional.of(() -> itemStack).cast();
    return super.getCapability(capability, facing);
  }

  public TileItemManipulator setFacing(Direction facing) {
    this.facing = facing;

    return this;
  }

  private FakePlayer getPlayer() {
    if (this.player == null || this.player.get() == null) {
      FakePlayer fakePlayer = FakePlayerFactory.get((ServerWorld) this.getLevel(), FAKEPLAYER);
      this.player = new WeakReference<>(fakePlayer);
      fakePlayer.moveTo(
          this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(), 0f, 0f);
      fakePlayer.inventory.clearContent();
    }

    return this.player.get();
  }

  public void breakBlock() {
    ItemStack storedItem = itemStack.getStackInSlot(0);
    if (!storedItem.isEmpty()) {
      InventoryHelper.dropItemStack(
          this.getLevel(), getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), storedItem);
    }
  }
}
