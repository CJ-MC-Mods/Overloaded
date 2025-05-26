package com.cjm721.overloaded.tile.functional;

import com.cjm721.overloaded.tile.ModTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.UUID;

public class TilePlayerInterface extends BlockEntity {

  private UUID placer;

  public TilePlayerInterface(BlockPos pos, BlockState blockState) {
    super(ModTiles.playerInterface.get(), pos, blockState);
  }

  @Override
  public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
    CompoundTag tag = new CompoundTag();
    saveAdditional(tag, registries);
    return tag;
  }

  @Override
  public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
    return ClientboundBlockEntityDataPacket.create(this);
  }

  @Override
  public void onDataPacket(
      Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
    this.loadAdditional(pkt.getTag(), lookupProvider);
  }

  @Override
  protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
    if (compound.contains("Placer")) placer = UUID.fromString(compound.getString("Placer"));

    super.loadAdditional(compound, registries);
  }

  @Override
  protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
    if (placer != null) compound.putString("Placer", placer.toString());

    super.saveAdditional(compound, registries);
  }

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

  public Optional<Player> getPlayer() {
    if (this.placer != null) {
      Player player = this.getLevel().getPlayerByUUID(this.placer);
      return Optional.ofNullable(player);
    }
    return Optional.empty();
  }
}
