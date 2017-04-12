package com.cjm721.overloaded.common.item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {
    public static ModItem linkingCard;

    public static void init() {
        linkingCard = new ItemLinkingCard();
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        linkingCard.registerModel();
    }
}
