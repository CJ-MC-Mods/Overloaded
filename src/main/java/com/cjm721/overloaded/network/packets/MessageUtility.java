package com.cjm721.overloaded.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.phys.Vec3;

final class MessageUtility implements CustomPacketPayload {

    public static Vec3 vecFromBytes(ByteBuf buf) {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();

        return new Vec3(x, y, z);
    }

    public static void toBytes(ByteBuf buf, Vec3 vector) {
        buf.writeDouble(vector.x);
        buf.writeDouble(vector.y);
        buf.writeDouble(vector.z);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return null;
    }
}
