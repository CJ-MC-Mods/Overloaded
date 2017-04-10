package com.cjm721.overloaded.common.block.tile.base;

import com.cjm721.overloaded.common.block.tile.TileHyperItemReceiver;
import com.cjm721.overloaded.common.storage.IHyperHandler;
import com.cjm721.overloaded.common.storage.IHyperType;
import com.cjm721.overloaded.common.storage.LongItemStack;
import com.cjm721.overloaded.common.storage.item.IHyperHandlerItem;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;

import static com.cjm721.overloaded.common.util.CapabilityHyperItem.HYPER_ITEM_HANDLER;

/**
 * Created by CJ on 4/10/2017.
 */
public abstract class AbstractTileHyperSender<T extends IHyperType,H extends IHyperHandler<T>, C extends Capability<H>> extends TileEntity implements ITickable {

    private int delayTicks;

    private BlockPos partnerBlockPos;
    private int partnerWorldID;


    private final C capability;

    public AbstractTileHyperSender(C capability) {
        this.capability = capability;
    }

    @Override
    @MethodsReturnNonnullByDefault
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
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
    public void readFromNBT(NBTTagCompound compound) {
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

            AbstractTileHyperReceiver<T,H,C> partner = findPartner();
            if(partner != null) {
                send(partner);
            }
        }
        delayTicks++;
    }

    private AbstractTileHyperReceiver<T,H,C> findPartner() {
        WorldServer world = DimensionManager.getWorld(partnerWorldID);
        if(world != null && world.isBlockLoaded(partnerBlockPos)) {
            TileEntity partnerTE = world.getTileEntity(partnerBlockPos);

            if(partnerTE == null || !(partnerTE instanceof TileHyperItemReceiver)) {
                this.partnerBlockPos = null;
                return null;
            } else {
                return (AbstractTileHyperReceiver<T,H,C>) partnerTE;
            }
        }
        return null;
    }

    protected void send(AbstractTileHyperReceiver<T,H,C> partner) {
        for(EnumFacing side: EnumFacing.values()) {
            TileEntity te = this.getWorld().getTileEntity(this.getPos().add(side.getDirectionVec()));

            if(te == null || !te.hasCapability(capability, side.getOpposite()))
                continue;

            send(partner,te,side);
        }
    }

    protected void send(AbstractTileHyperReceiver<T,H, C> partner, TileEntity te, EnumFacing side) {
        H handler = te.getCapability(capability, side.getOpposite());
        T itemStack = handler.take(generate(Long.MAX_VALUE), false);
        if(itemStack != null && itemStack.getAmount() > 0) {
            T leftOvers = partner.receive(itemStack);
            if(leftOvers.getAmount() != itemStack.getAmount()) {
                T tookOut = handler.take(generate(itemStack.getAmount() - leftOvers.getAmount()), true);
                if(tookOut.getAmount() != itemStack.getAmount() - leftOvers.getAmount()) {
                    new RuntimeException("IHyperHandler Take was not consistent");
                }
            }
        }
    }

    protected abstract T generate(long amount);

    public void setPartnerInfo(int partnerWorldId, BlockPos partnerPos) {
        this.partnerWorldID = partnerWorldId;
        this.partnerBlockPos = partnerPos;
    }

    public String getRightClickMessage() {
        if(partnerBlockPos != null) {
            return String.format("Bound to Receiver at %d:%d,%d,%d", partnerWorldID, partnerBlockPos.getX(),partnerBlockPos.getY(),partnerBlockPos.getZ());
        }
        return "Not bound to anything";
    }
}
