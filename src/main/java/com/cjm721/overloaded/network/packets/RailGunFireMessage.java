package com.cjm721.overloaded.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class RailGunFireMessage implements IMessage {

    public int id;
    public Vec3d moveVector;
    public EnumHand hand;

    public RailGunFireMessage() {
    }

    public RailGunFireMessage(int id, Vec3d vector, EnumHand hand) {
        this.id = id;
        this.moveVector = vector;
        this.hand = hand;
    }

    /**
     * Convert from the supplied buffer into your specific message type
     *
     * @param buf
     */
    @Override
    public void fromBytes(ByteBuf buf) {
        this.id = buf.readInt();
        this.moveVector = MessageUtility.vecFromBytes(buf);
        this.hand = EnumHand.valueOf(ByteBufUtils.readUTF8String(buf));
    }

    /**
     * Deconstruct your message into the supplied byte buffer
     *
     * @param buf
     */
    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(id);
        MessageUtility.toBytes(buf, moveVector);
        ByteBufUtils.writeUTF8String(buf, hand.name());
    }
}
