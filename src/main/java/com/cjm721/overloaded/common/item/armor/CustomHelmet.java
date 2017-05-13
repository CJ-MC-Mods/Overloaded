package com.cjm721.overloaded.common.item.armor;


import com.cjm721.overloaded.client.render.entity.ModelCustomArmor;
import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class CustomHelmet extends ItemArmor {

    public CustomHelmet() {
        super(ArmorMaterial.DIAMOND, 0, EntityEquipmentSlot.HEAD);
        setMaxDamage(-1);

        setRegistryName("custom_helmet");
        setUnlocalizedName("custom_helmet");
        setCreativeTab(OverloadedCreativeTabs.TECH);

        GameRegistry.register(this);
    }

    @SideOnly(Side.CLIENT)
    public ModelBiped model;

    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
        ModelBiped armorModel = null;
        if(itemStack != null && itemStack.getItem() instanceof CustomHelmet)
        {
            armorModel = new ModelCustomArmor(1F);
            armorModel.bipedHead.showModel = false;
            armorModel.bipedHeadwear.showModel = true;
            armorModel.bipedBody.showModel = false;
            armorModel.bipedRightArm.showModel = false;
            armorModel.bipedLeftArm.showModel = false;
            armorModel.bipedRightLeg.showModel = false;
            armorModel.bipedLeftLeg.showModel = false;

            armorModel.isSneak = entityLiving.isSneaking();
            armorModel.isRiding = entityLiving.isRiding();
            armorModel.isChild = entityLiving.isChild();
        }
        return armorModel;
    }
}
