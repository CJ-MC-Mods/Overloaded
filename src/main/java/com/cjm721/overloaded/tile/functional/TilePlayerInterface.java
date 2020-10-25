package com.cjm721.overloaded.tile.functional;

import com.cjm721.overloaded.tile.ModTiles;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class TilePlayerInterface extends TileEntity {

  private UUID placer;

  public TilePlayerInterface() {
    super(ModTiles.playerInterface);
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
    this.read(this.getBlockState(), pkt.getNbtCompound());
  }

  @Override
  public void read(@Nonnull BlockState state, @Nonnull CompoundNBT compound) {
    if (compound.contains("Placer")) placer = UUID.fromString(compound.getString("Placer"));

    super.read(state, compound);
  }

  @Override
  @Nonnull
  public CompoundNBT write(@Nonnull CompoundNBT compound) {
    if (placer != null) compound.putString("Placer", placer.toString());

    return super.write(compound);
  }

  public void setPlacer(@Nonnull LivingEntity placer) {
    if (placer instanceof PlayerEntity) this.placer = placer.getUniqueID();
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(
      @Nonnull Capability<T> capability, @Nullable Direction facing) {
    if (this.placer != null) {
      PlayerEntity player = this.getWorld().getPlayerByUuid(this.placer);

      if (player != null) {
        return player.getCapability(capability, facing);
      }
    }

    return super.getCapability(capability, facing);
  }

  public UUID getPlacer() {
    return placer;
  }
}
