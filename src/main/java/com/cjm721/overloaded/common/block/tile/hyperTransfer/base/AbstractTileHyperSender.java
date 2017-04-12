package com.cjm721.overloaded.common.block.tile.hyperTransfer.base;

import com.cjm721.overloaded.common.storage.IHyperHandler;
import com.cjm721.overloaded.common.storage.IHyperType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractTileHyperSender<T extends IHyperType,H extends IHyperHandler<T>> extends TileEntity implements ITickable {

    private int delayTicks;

    private BlockPos partnerBlockPos;
    private int partnerWorldID;


    private final Capability<H> capability;

    public AbstractTileHyperSender(Capability<H> capability) {
        this.capability = capability;
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        super.writeToNBT(compound);

        if(partnerBlockPos != null) {
            compound.setInteger("X", partnerBlockPos.getX());
            compound.setInteger("Y", partnerBlockPos.getY());
            compound.setInteger("Z", partnerBlockPos.getZ());
            compound.setInteger("WORLD", partnerWorldID);
        }

        return compound;
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        super.readFromNBT(compound);

        if (compound.hasKey("X")) {
            int x = compound.getInteger("X");
            int y = compound.getInteger("Y");
            int z = compound.getInteger("Z");

            partnerBlockPos = new BlockPos(x, y, z);
            partnerWorldID = compound.getInteger("WORLD");
        }
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    @Override
    public void update() {
        if(delayTicks % 20 == 0) {
            if(partnerBlockPos == null)
                return;

            AbstractTileHyperReceiver<T,H> partner = findPartner();
            if(partner != null) {
                send(partner);
            }
        }
        delayTicks++;
    }

    @Nullable
    private AbstractTileHyperReceiver<T,H> findPartner() {
        WorldServer world = DimensionManager.getWorld(partnerWorldID);
        if(world != null && world.isBlockLoaded(partnerBlockPos)) {
            TileEntity partnerTE = world.getTileEntity(partnerBlockPos);

            if(partnerTE == null || !isCorrectPartnerType(partnerTE)) {
                this.partnerBlockPos = null;
                return null;
            } else {
                return (AbstractTileHyperReceiver<T,H>) partnerTE;
            }
        }
        return null;
    }

    protected void send(AbstractTileHyperReceiver<T,H> partner) {
        for(EnumFacing side: EnumFacing.values()) {
            TileEntity te = this.getWorld().getTileEntity(this.getPos().add(side.getDirectionVec()));

            if(te == null || !te.hasCapability(capability, side.getOpposite()))
                continue;

            send(partner,te,side);
        }
    }

    protected void send(AbstractTileHyperReceiver<T,H> partner, TileEntity te, EnumFacing side) {
        H handler = te.getCapability(capability, side.getOpposite());
        T itemStack = handler.take(generate(Long.MAX_VALUE), false);
        if(itemStack.getAmount() > 0) {
            T leftOvers = partner.receive(itemStack);
            if(leftOvers.getAmount() != itemStack.getAmount()) {
                T tookOut = handler.take(generate(itemStack.getAmount() - leftOvers.getAmount()), true);
                if(tookOut.getAmount() != itemStack.getAmount() - leftOvers.getAmount()) {
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
        if(partnerBlockPos != null) {
            return String.format("Bound to Receiver at %d:%d,%d,%d", partnerWorldID, partnerBlockPos.getX(),partnerBlockPos.getY(),partnerBlockPos.getZ());
        }
        return "Not bound to anything";
    }
}
