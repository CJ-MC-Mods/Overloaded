package com.cjm721.ibhstd.common.item;

import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by CJ on 4/9/2017.
 */
public class ModItems {
    public static ModItem linkingCard;


    public static void init() {
        linkingCard = new ItemLinkingCard();
    }

    @SideOnly(Side.CLIENT)
    public void registerModels() {
        linkingCard.registerModel();
    }
}
