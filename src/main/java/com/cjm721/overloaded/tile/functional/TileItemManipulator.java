package com.cjm721.overloaded.tile.functional;

import com.cjm721.overloaded.tile.ModTiles;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.UUID;

public class TileItemManipulator extends BlockEntity {

  private static final GameProfile FAKEPLAYER =
      new GameProfile(
          UUID.fromString("85824917-23F6-4B28-8B12-FAED16A3F66B"), "[Overloaded:Item_Manipulator]");

  private EnergyStorage energyStorage;
  private ItemStackHandler itemStack;
  private WeakReference<FakePlayer> player;
  private Direction facing;

  public TileItemManipulator(BlockPos pos, BlockState blockState) {
    super(ModTiles.itemManipulator.get(), pos, blockState);
    itemStack = new ItemStackHandler();
    energyStorage = new EnergyStorage(Integer.MAX_VALUE, Integer.MAX_VALUE, 0);
  }

  @Override
  protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
    if (compound.contains("Item")) {
      itemStack.deserializeNBT(registries, (CompoundTag) compound.get("Item"));
    }

    if (compound.contains("Energy")) {
      energyStorage =
          new EnergyStorage(Integer.MAX_VALUE, Integer.MAX_VALUE, 0, compound.getInt("Energy"));
    }
  }

  @Override
  protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
    compound.put("Item", itemStack.serializeNBT(registries));
    compound.putInt("Energy", energyStorage.getEnergyStored());
    super.saveAdditional(compound, registries);
  }

  public void tick() {
    if (this.getLevel().isClientSide) {
      return;
    }

    ItemStack currentItem = itemStack.getStackInSlot(0);
    if (currentItem.isEmpty()) return;

    FakePlayer player = getPlayer();

    BlockPos.MutableBlockPos blockPos = this.getBlockPos().mutable();
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

  public TileItemManipulator setFacing(Direction facing) {
    this.facing = facing;

    return this;
  }

  private FakePlayer getPlayer() {
    if (this.player == null || this.player.get() == null) {
      FakePlayer fakePlayer = FakePlayerFactory.get((ServerLevel) this.getLevel(), FAKEPLAYER);
      this.player = new WeakReference<>(fakePlayer);
      fakePlayer.moveTo(
          this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(), 0f, 0f);
      fakePlayer.getInventory().clearContent();
    }

    return this.player.get();
  }

  public void breakBlock() {
    ItemStack storedItem = itemStack.getStackInSlot(0);
    if (!storedItem.isEmpty()) {
      Containers.dropItemStack(
          this.getLevel(), getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), storedItem);
    }
  }
}
