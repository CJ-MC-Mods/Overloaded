package com.cjm721.overloaded.tile.hyperTransfer.base;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.item.functional.ItemLinkingCard;
import com.cjm721.overloaded.storage.IHyperHandler;
import com.cjm721.overloaded.storage.IHyperType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.neoforged.neoforge.capabilities.BlockCapability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractTileHyperSender<T extends IHyperType, H extends IHyperHandler<T>>
    extends BlockEntity {

  private int delayTicks;

  private BlockPos partnerBlockPos;
  private ResourceKey<Level> partnerWorldID;

  private final BlockCapability<H, Direction> capability;

  protected AbstractTileHyperSender(
      BlockEntityType<?> type,
      BlockCapability<H, Direction> capability,
      BlockPos pos,
      BlockState state) {
    super(type, pos, state);
    this.capability = capability;
    this.delayTicks = ThreadLocalRandom.current().nextInt();
  }

  @Override
  protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
    super.saveAdditional(compound, registries);

    if (partnerBlockPos != null) {
      compound.putInt("X", partnerBlockPos.getX());
      compound.putInt("Y", partnerBlockPos.getY());
      compound.putInt("Z", partnerBlockPos.getZ());
      compound.putString("WORLD", partnerWorldID.location().toString());
    }
  }

  @Override
  protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
    super.loadAdditional(compound, registries);

    if (compound.contains("X")) {
      int x = compound.getInt("X");
      int y = compound.getInt("Y");
      int z = compound.getInt("Z");

      partnerBlockPos = new BlockPos(x, y, z);
      partnerWorldID =
          ResourceKey.create(
              Registries.DIMENSION, ResourceLocation.tryParse(compound.getString("WORLD")));
    }
  }

  @SuppressWarnings("unchecked") // Should already be checked by base Minecraft Code
  public static <T extends BlockEntity> void tick(
      Level level, BlockPos blockPos, BlockState blockState, T t) {
    if (level.isClientSide) return;

    if (!(t instanceof AbstractTileHyperSender)) {
      Overloaded.logger.error(
          "BlockEntity is not an AbstractTileHyperSender when expected. Pos: " + blockPos);
      return;
    }
    AbstractTileHyperSender blockEntity = (AbstractTileHyperSender) t;

    if (blockEntity.delayTicks % 20 == 0) {
      if (blockEntity.partnerBlockPos == null) return;

      AbstractTileHyperReceiver<?, ?> partner = blockEntity.findPartner();
      if (partner != null) {
        blockEntity.send(partner);
      }
    }
    blockEntity.delayTicks++;
  }

  @Nullable
  private AbstractTileHyperReceiver<T, H> findPartner() {
    Level world = this.getLevel().getServer().getLevel(partnerWorldID);
    if (world != null && world.isLoaded(partnerBlockPos)) {
      BlockEntity partnerTE = world.getBlockEntity(partnerBlockPos);

      if (partnerTE == null || !isCorrectPartnerType(partnerTE)) {
        this.partnerBlockPos = null;
        return null;
      } else {
        return (AbstractTileHyperReceiver<T, H>) partnerTE;
      }
    }
    return null;
  }

  private void send(@Nonnull AbstractTileHyperReceiver<T, H> partner) {
    for (Direction side : Direction.values()) {
      BlockEntity te = this.getLevel().getBlockEntity(this.getBlockPos().offset(side.getNormal()));

      if (te == null) {
        continue;
      }

      H cap = partner.getLevel().getCapability(capability, te.getBlockPos(), side.getOpposite());

      if (cap == null) {
        continue;
      }

      send(partner, te, side);
    }
  }

  private void send(
      @Nonnull AbstractTileHyperReceiver<T, H> partner,
      @Nonnull BlockEntity te,
      @Nonnull Direction side) {
    H handler = level.getCapability(capability, te.getBlockPos(), side.getOpposite());
    if (handler == null) {
      return;
    }

    T itemStack = handler.take(generate(Long.MAX_VALUE), false);
    if (itemStack.getAmount().longValue() > 0) {
      T leftOvers = partner.receive(itemStack);
      if (leftOvers.getAmount() != itemStack.getAmount()) {
        T tookOut =
            handler.take(
                generate(itemStack.getAmount().longValue() - leftOvers.getAmount().longValue()),
                true);
        if (tookOut.getAmount().longValue()
            != itemStack.getAmount().longValue() - leftOvers.getAmount().longValue()) {
          throw new RuntimeException("IHyperHandler Take was not consistent");
        }
      }
    }
  }

  @Nonnull
  protected abstract T generate(long amount);

  protected abstract boolean isCorrectPartnerType(BlockEntity te);

  public void setPartnerInfo(ItemLinkingCard.DimensionLocation location) {
    this.partnerWorldID = ResourceKey.create(Registries.DIMENSION, location.level());
    this.partnerBlockPos = location.pos();
  }

  @Nonnull
  public String getRightClickMessage() {
    if (partnerBlockPos != null) {
      return String.format(
          "Bound to Receiver at %s %d,%d,%d",
          partnerWorldID.location(),
          partnerBlockPos.getX(),
          partnerBlockPos.getY(),
          partnerBlockPos.getZ());
    }
    return "Not bound to anything";
  }
}
