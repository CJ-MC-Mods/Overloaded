package com.cjm721.overloaded.item.functional.armor;

import com.cjm721.overloaded.OverloadedCreativeTabs;
import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.util.IModRegistrable;
import net.minecraft.block.BlockDispenser;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.UUID;

public abstract class AbstractMultiArmor extends ItemArmor implements IModRegistrable, IMultiArmor {

    protected static final UUID[] ARMOR_MODIFIERS = new UUID[] {UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
    public static ArmorMaterial pureMatter =  EnumHelper.addArmorMaterial("pureMatter", "", 100,new int[]{30,60,80,30}, 50, SoundEvents.ITEM_ARMOR_EQUIP_GOLD,10);

    public AbstractMultiArmor(int p_i46750_2_, EntityEquipmentSlot p_i46750_3_) {
        super(pureMatter, p_i46750_2_, p_i46750_3_);

        setMaxDamage(-1);

        setCreativeTab(OverloadedCreativeTabs.TECH);
        ModItems.addToSecondaryInit(this);
    }
}
