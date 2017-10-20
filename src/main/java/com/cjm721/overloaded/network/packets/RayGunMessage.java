package com.cjm721.overloaded.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class RayGunMessage implements IMessage {

    public Vec3d vector;

    public RayGunMessage() { }

    public RayGunMessage(Vec3d vector) {
        this.vector = vector;
    }

    /**
     * Convert from the supplied buffer into your specific message type
     *
     * @param buf
     */
    @Override
    public void fromBytes(ByteBuf buf) {
        this.vector = MessageUtility.vecFromBytes(buf);
    }

    /**
     * Deconstruct your message into the supplied byte buffer
     *
     * @param buf
     */
    @Override
    public void toBytes(ByteBuf buf) {
        MessageUtility.toBytes(buf,vector);
    }
}
