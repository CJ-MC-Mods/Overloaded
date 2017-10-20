package com.cjm721.overloaded.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.Vec3d;

public final class MessageUtility {

    public static Vec3d vecFromBytes(ByteBuf buf) {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();

        return new Vec3d(x,y,z);
    }

    public static void toBytes(ByteBuf buf, Vec3d vector) {
        buf.writeDouble(vector.x);
        buf.writeDouble(vector.y);
        buf.writeDouble(vector.z);
    }
}
