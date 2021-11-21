package com.cjm721.overloaded.tile.hyperTransfer.base;

import com.cjm721.overloaded.storage.IHyperHandler;
import com.cjm721.overloaded.storage.IHyperType;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractTileHyperSender<T extends IHyperType, H extends IHyperHandler<T>>
    extends TileEntity implements ITickableTileEntity {

  private int delayTicks;

  private BlockPos partnerBlockPos;
  private RegistryKey<World> partnerWorldID;

  private final Capability<H> capability;

  protected AbstractTileHyperSender(TileEntityType<?> type, Capability<H> capability) {
    super(type);
    this.capability = capability;
  }

  @Override
  @Nonnull
  public CompoundNBT save(@Nonnull CompoundNBT compound) {
    super.save(compound);

    if (partnerBlockPos != null) {
      compound.putInt("X", partnerBlockPos.getX());
      compound.putInt("Y", partnerBlockPos.getY());
      compound.putInt("Z", partnerBlockPos.getZ());
      compound.putString("WORLD", partnerWorldID.location().toString());
    }

    return compound;
  }

  @Override
  public void load(@Nonnull BlockState state, @Nonnull CompoundNBT compound) {
    super.load(state, compound);

    if (compound.contains("X")) {
      int x = compound.getInt("X");
      int y = compound.getInt("Y");
      int z = compound.getInt("Z");

      partnerBlockPos = new BlockPos(x, y, z);
      partnerWorldID = RegistryKey.create(Registry.DIMENSION_REGISTRY, ResourceLocation.tryParse(compound.getString("WORLD")));
    }
  }

  /** Like the old updateEntity(), except more generic. */
  @Override
  public void tick() {
    if (getLevel().isClientSide) return;

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
    World world = this.getLevel().getServer().getLevel(partnerWorldID);
    if (world != null && world.hasChunkAt(partnerBlockPos)) {
      TileEntity partnerTE = world.getBlockEntity(partnerBlockPos);

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
      TileEntity te = this.getLevel().getBlockEntity(this.getBlockPos().offset(side.getNormal()));

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
    if (itemStack.getAmount().longValue() > 0) {
      T leftOvers = partner.receive(itemStack);
      if (leftOvers.getAmount() != itemStack.getAmount()) {
        T tookOut =
            handler
                .orElse(null)
                .take(
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

  protected abstract boolean isCorrectPartnerType(TileEntity te);

  public void setPartnerInfo(String registryLocation, BlockPos partnerPos) {
    this.partnerWorldID = RegistryKey.create(Registry.DIMENSION_REGISTRY, ResourceLocation.tryParse(registryLocation));
    this.partnerBlockPos = partnerPos;
  }

  @Nonnull
  public String getRightClickMessage() {
    if (partnerBlockPos != null) {
      return String.format(
          "Bound to Receiver at %s %d,%d,%d",
          partnerWorldID.location(), partnerBlockPos.getX(), partnerBlockPos.getY(), partnerBlockPos.getZ());
    }
    return "Not bound to anything";
  }
}
