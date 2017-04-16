package com.cjm721.overloaded.common.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * Created by CJ on 4/15/2017.
 */
public class MultiToolRightClickMessage implements IMessage {

    private EnumFacing hitSide;
    private BlockPos pos;

    public MultiToolRightClickMessage() { }

    public MultiToolRightClickMessage(BlockPos pos, EnumFacing hitSide) {
        this.pos = pos;
        this.hitSide = hitSide;
    }



     /**
     * Convert from the supplied buffer into your specific message type
     *
     * @param buf
     */
    @Override
    public void fromBytes(ByteBuf buf) {
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        int facing = buf.readInt();

        this.pos = new BlockPos(x,y,z);
        this.hitSide = EnumFacing.getFront(facing);
    }

    /**
     * Deconstruct your message into the supplied byte buffer
     *
     * @param buf
     */
    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeInt(hitSide.getIndex());
    }

    public BlockPos getPos() {
        return pos;
    }

    public EnumFacing getHitSide() {
        return hitSide;
    }
}
