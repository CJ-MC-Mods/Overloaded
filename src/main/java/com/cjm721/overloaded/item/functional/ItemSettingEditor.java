package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.OverloadedCreativeTabs;
import com.cjm721.overloaded.item.ModItem;
import com.cjm721.overloaded.network.OverloadedGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemSettingEditor extends ModItem {

    public ItemSettingEditor() {
        setRegistryName("settings_editor");
        setUnlocalizedName("settings_editor");
        setCreativeTab(OverloadedCreativeTabs.TECH);
    }

    @Override
    public void registerModel() {

    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if(worldIn.isRemote) {
            playerIn.openGui(Overloaded.instance, OverloadedGuiHandler.MULTI_ARMOR,worldIn,(int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
