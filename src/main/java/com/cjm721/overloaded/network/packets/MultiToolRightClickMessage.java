package com.cjm721.overloaded.network.packets;

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
    private float hitX;
    private float hitY;
    private float hitZ;

    public MultiToolRightClickMessage() {
    }

    public MultiToolRightClickMessage(BlockPos pos, EnumFacing hitSide, float hitX, float hitY, float hitZ) {
        this.pos = pos;
        this.hitSide = hitSide;
        this.hitX = hitX;
        this.hitY = hitY;
        this.hitZ = hitZ;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        int facing = buf.readInt();

        this.pos = new BlockPos(x, y, z);
        this.hitSide = EnumFacing.getFront(facing);


        hitX = buf.readFloat();
        hitY = buf.readFloat();
        hitZ = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeInt(hitSide.getIndex());

        buf.writeFloat(hitX);
        buf.writeFloat(hitY);
        buf.writeFloat(hitZ);
    }

    public BlockPos getPos() {
        return pos;
    }

    public EnumFacing getHitSide() {
        return hitSide;
    }

    public float getHitX() {
        return hitX;
    }

    public float getHitY() {
        return hitY;
    }

    public float getHitZ() {
        return hitZ;
    }
}
