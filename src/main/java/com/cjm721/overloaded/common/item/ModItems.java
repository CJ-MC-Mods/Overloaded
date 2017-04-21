package com.cjm721.overloaded.common.item;

import com.cjm721.overloaded.common.item.functional.ItemLinkingCard;
import com.cjm721.overloaded.common.item.functional.ItemMultiTool;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {
    public static ModItem linkingCard;
    public static ItemMultiTool distanceBreaker;

    public static void init() {
        linkingCard = new ItemLinkingCard();
        distanceBreaker = new ItemMultiTool();
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        linkingCard.registerModel();
        distanceBreaker.registerModel();
    }
}
