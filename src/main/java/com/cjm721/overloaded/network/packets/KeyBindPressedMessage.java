package com.cjm721.overloaded.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import javax.annotation.Nonnull;

public class KeyBindPressedMessage implements IMessage {
    public KeyBind getBind() {
        return bind;
    }

    private KeyBind bind;

    public enum KeyBind {
        NO_CLIP
    }

    public KeyBindPressedMessage() {}

    public KeyBindPressedMessage(KeyBind bind) {
        this.bind = bind;
    }

    /**
     * Convert from the supplied buffer into your specific message type
     *
     * @param buf
     */
    @Override
    public void fromBytes(@Nonnull ByteBuf buf) {
        int length = buf.readInt();
        byte[] buffer = new byte[length];

        buf.readBytes(buffer);

        bind = KeyBind.valueOf(new String(buffer));
    }


    /**
     * Deconstruct your message into the supplied byte buffer
     *
     * @param buf
     */
    @Override
    public void toBytes(@Nonnull ByteBuf buf) {
        byte[] string = bind.toString().getBytes();

        buf.writeInt(string.length);
        buf.writeBytes(string);
    }
}
