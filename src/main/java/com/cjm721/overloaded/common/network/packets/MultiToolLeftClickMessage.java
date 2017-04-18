package com.cjm721.overloaded.common.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * Created by CJ on 4/14/2017.
 */
public class MultiToolLeftClickMessage implements IMessage {

    private BlockPos pos;

    public MultiToolLeftClickMessage() { }

    public MultiToolLeftClickMessage(BlockPos pos) {
        this.pos = pos;
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

        this.pos = new BlockPos(x,y,z);
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
    }

    public BlockPos getPos() {
        return pos;
    }
}