package com.cjm721.overloaded.common.item.functional.armor;

import com.cjm721.overloaded.client.render.entity.RenderMultiBoots;
import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.config.RecipeEnabledConfig;
import com.cjm721.overloaded.common.item.ModItems;
import com.cjm721.overloaded.common.util.IModRegistrable;
import com.cjm721.overloaded.common.util.IntEnergyWrapper;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ItemMultiBoots extends ItemArmor implements IModRegistrable {

    public ItemMultiBoots() {
        super(ArmorMaterial.DIAMOND, 0, EntityEquipmentSlot.FEET);
        setMaxDamage(-1);

        setRegistryName("multi_boots");
        setUnlocalizedName("multi_boots");
        setCreativeTab(OverloadedCreativeTabs.TECH);

        GameRegistry.register(this);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);

        ModItems.addToSecondaryInit(this);
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
        if(armorModel == null)
            armorModel = new RenderMultiBoots();
        return armorModel;
    }

    private RenderMultiBoots armorModel;

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
        ModelLoader.setCustomModelResourceLocation(this, 0, location);
    }

    @Override
    public void registerRecipe() {
        if(RecipeEnabledConfig.customBoots) {
            //GameRegistry.addRecipe(new ItemStack(this), "GII", "IRI", "III", 'G', Items.GOLD_NUGGET, 'I', Items.IRON_INGOT, 'R', Items.REDSTONE);
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new IntEnergyWrapper(stack,nbt);
    }
}
