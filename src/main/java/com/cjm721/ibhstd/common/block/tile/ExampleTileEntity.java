package com.cjm721.ibhstd.common.block.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.GameType;

/**
 * Created by CJ on 4/4/2017.
 */
public class ExampleTileEntity extends TileEntity implements ITickable {
    @Override
    public void update() {
        EntityPlayer player = this.getWorld().getClosestPlayer(this.getPos().getX(),this.getPos().getY(), this.getPos().getZ(), 10, false);

        if(player != null)
            player.setGameType(GameType.SURVIVAL);
    }
}
