package com.cjm721.overloaded.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.vector.Vector3d;

final class MessageUtility {

    public static Vector3d vecFromBytes(ByteBuf buf) {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();

        return new Vector3d(x, y, z);
    }

    public static void toBytes(ByteBuf buf, Vector3d vector) {
        buf.writeDouble(vector.x);
        buf.writeDouble(vector.y);
        buf.writeDouble(vector.z);
    }
}
