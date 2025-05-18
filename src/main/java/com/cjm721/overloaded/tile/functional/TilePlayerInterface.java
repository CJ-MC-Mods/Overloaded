package com.cjm721.overloaded.tile.functional;

import com.cjm721.overloaded.tile.ModTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nonnull;
import java.util.UUID;

public class TilePlayerInterface extends BlockEntity {

  private UUID placer;

  public TilePlayerInterface(BlockPos pos, BlockState blockState) {
    super(ModTiles.playerInterface, pos, blockState);
  }

//  @Override
//  @Nonnull
//  public CompoundNBT getUpdateTag() {
//    return save(new CompoundNBT());
//  }
//
//  @Nullable
//  @Override
//  public SUpdateTileEntityPacket getUpdatePacket() {
//    CompoundNBT tag = new CompoundNBT();
//    save(tag);
//
//    return new SUpdateTileEntityPacket(getBlockPos(), 1, tag);
//  }
//
//  @Override
//  public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
//    this.load(this.getBlockState(), pkt.getTag());
//  }
//
//  @Override
//  public void load(@Nonnull BlockState state, @Nonnull CompoundNBT compound) {
//    if (compound.contains("Placer")) placer = UUID.fromString(compound.getString("Placer"));
//
//    super.load(state, compound);
//  }
//
//  @Override
//  @Nonnull
//  public CompoundNBT save(@Nonnull CompoundNBT compound) {
//    if (placer != null) compound.putString("Placer", placer.toString());
//
//    return super.save(compound);
//  }

  public void setPlacer(@Nonnull LivingEntity placer) {
    if (placer instanceof Player) this.placer = placer.getUUID();
  }

//  @Nonnull
//  @Override
//  public <T> LazyOptional<T> getCapability(
//          @Nonnull Capability<T> capability, @Nullable Direction facing) {
//    if (this.placer != null) {
//      PlayerEntity player = this.getLevel().getPlayerByUUID(this.placer);
//
//      if (player != null) {
//        return player.getCapability(capability, facing);
//      }
//    }
//
//    return super.getCapability(capability, facing);
//  }

  public UUID getPlacer() {
    return placer;
  }
}
