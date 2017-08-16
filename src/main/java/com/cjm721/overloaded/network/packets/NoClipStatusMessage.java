package com.cjm721.overloaded.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class NoClipStatusMessage implements IMessage {

    public boolean isEnabled() {
        return enabled;
    }

    private boolean enabled;

    public NoClipStatusMessage() {
    }

    public NoClipStatusMessage(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Convert from the supplied buffer into your specific message type
     *
     * @param buf
     */
    @Override
    public void fromBytes(ByteBuf buf) {
        enabled = buf.readBoolean();
    }

    /**
     * Deconstruct your message into the supplied byte buffer
     *
     * @param buf
     */
    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(enabled);
    }
}
