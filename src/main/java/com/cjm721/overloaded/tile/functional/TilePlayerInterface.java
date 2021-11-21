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
  public void load(@Nonnull BlockState state, @Nonnull CompoundNBT compound) {
    if (compound.contains("Placer")) placer = UUID.fromString(compound.getString("Placer"));

    super.load(state, compound);
  }

  @Override
  @Nonnull
  public CompoundNBT save(@Nonnull CompoundNBT compound) {
    if (placer != null) compound.putString("Placer", placer.toString());

    return super.save(compound);
  }

  public void setPlacer(@Nonnull LivingEntity placer) {
    if (placer instanceof PlayerEntity) this.placer = placer.getUUID();
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(
      @Nonnull Capability<T> capability, @Nullable Direction facing) {
    if (this.placer != null) {
      PlayerEntity player = this.getLevel().getPlayerByUUID(this.placer);

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
