package com.cjm721.overloaded.tile.hyperTransfer.base;

import com.cjm721.overloaded.storage.IHyperHandler;
import com.cjm721.overloaded.storage.IHyperType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractTileHyperSender<T extends IHyperType, H extends IHyperHandler<T>>
    extends TileEntity implements ITickableTileEntity {

  private int delayTicks;

  private BlockPos partnerBlockPos;
  private int partnerWorldID;

  private final Capability<H> capability;

  protected AbstractTileHyperSender(TileEntityType<?> type, Capability<H> capability) {
    super(type);
    this.capability = capability;
  }

  @Override
  @Nonnull
  public CompoundNBT write(@Nonnull CompoundNBT compound) {
    super.write(compound);

    if (partnerBlockPos != null) {
      compound.putInt("X", partnerBlockPos.getX());
      compound.putInt("Y", partnerBlockPos.getY());
      compound.putInt("Z", partnerBlockPos.getZ());
      compound.putInt("WORLD", partnerWorldID);
    }

    return compound;
  }

  @Override
  public void read(@Nonnull CompoundNBT compound) {
    super.read(compound);

    if (compound.contains("X")) {
      int x = compound.getInt("X");
      int y = compound.getInt("Y");
      int z = compound.getInt("Z");

      partnerBlockPos = new BlockPos(x, y, z);
      partnerWorldID = compound.getInt("WORLD");
    }
  }

  /** Like the old updateEntity(), except more generic. */
  @Override
  public void tick() {
    if (getWorld().isRemote) return;

    if (delayTicks % 20 == 0) {
      if (partnerBlockPos == null) return;

      AbstractTileHyperReceiver<T, H> partner = findPartner();
      if (partner != null) {
        send(partner);
      }
    }
    delayTicks++;
  }

  @Nullable
  private AbstractTileHyperReceiver<T, H> findPartner() {
    World world =
        DimensionManager.getWorld(
            this.getWorld().getServer(), DimensionType.getById(partnerWorldID), false, false);
    if (world != null && world.isBlockLoaded(partnerBlockPos)) {
      TileEntity partnerTE = world.getTileEntity(partnerBlockPos);

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
      TileEntity te = this.getWorld().getTileEntity(this.getPos().add(side.getDirectionVec()));

      if (te == null) {
        continue;
      }

      LazyOptional<H> cap = te.getCapability(capability, side.getOpposite());

      if (!cap.isPresent()) {
        continue;
      }

      send(partner, te, side);
    }
  }

  private void send(
      @Nonnull AbstractTileHyperReceiver<T, H> partner,
      @Nonnull TileEntity te,
      @Nonnull Direction side) {
    LazyOptional<H> handler = te.getCapability(capability, side.getOpposite());
    if (!handler.isPresent()) {
      return;
    }

    T itemStack = handler.orElse(null).take(generate(Long.MAX_VALUE), false);
    if (itemStack.getAmount() > 0) {
      T leftOvers = partner.receive(itemStack);
      if (leftOvers.getAmount() != itemStack.getAmount()) {
        T tookOut =
            handler
                .orElse(null)
                .take(generate(itemStack.getAmount() - leftOvers.getAmount()), true);
        if (tookOut.getAmount() != itemStack.getAmount() - leftOvers.getAmount()) {
          throw new RuntimeException("IHyperHandler Take was not consistent");
        }
      }
    }
  }

  @Nonnull
  protected abstract T generate(long amount);

  protected abstract boolean isCorrectPartnerType(TileEntity te);

  public void setPartnerInfo(int partnerWorldId, BlockPos partnerPos) {
    this.partnerWorldID = partnerWorldId;
    this.partnerBlockPos = partnerPos;
  }

  @Nonnull
  public String getRightClickMessage() {
    if (partnerBlockPos != null) {
      return String.format(
          "Bound to Receiver at %d:%d,%d,%d",
          partnerWorldID, partnerBlockPos.getX(), partnerBlockPos.getY(), partnerBlockPos.getZ());
    }
    return "Not bound to anything";
  }
}
