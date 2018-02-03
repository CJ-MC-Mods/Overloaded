package com.cjm721.overloaded.network;

import com.cjm721.overloaded.client.gui.MultiArmorGuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class OverloadedGuiHandler implements IGuiHandler {

    public static final int MULTI_ARMOR = 0;


    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch(ID) {
            case MULTI_ARMOR:
                return null;
        }

        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch(ID) {
            case MULTI_ARMOR:
                return new MultiArmorGuiScreen();
        }
        return null;
    }
}
