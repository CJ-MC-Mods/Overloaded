package com.cjm721.overloaded.item.functional.armor;

import com.cjm721.overloaded.OverloadedCreativeTabs;
import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.util.IModRegistrable;
import com.cjm721.overloaded.util.itemwrapper.IntEnergyWrapper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.NumberFormat;
import java.util.List;
import java.util.UUID;

import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public abstract class AbstractMultiArmor extends ItemArmor implements IModRegistrable, IMultiArmor {

    private static final UUID[] ARMOR_MODIFIERS = new UUID[]{UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
    public static ArmorMaterial pureMatter = EnumHelper.addArmorMaterial("pureMatter", "overloaded:na", 100, new int[]{6, 12, 16, 6}, 50, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 4);

    public AbstractMultiArmor(int render_index, EntityEquipmentSlot equipmentSlot) {
        super(pureMatter, render_index, equipmentSlot);

        setMaxDamage(-1);

        setCreativeTab(OverloadedCreativeTabs.TECH);
        ModItems.addToSecondaryInit(this);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        IEnergyStorage handler = stack.getCapability(ENERGY, null);
        tooltip.add("Energy Stored: " + NumberFormat.getInstance().format(handler.getEnergyStored()));

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public boolean showDurabilityBar(ItemStack p_showDurabilityBar_1_) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        IEnergyStorage storage = stack.getCapability(ENERGY, null);

        if (storage != null)
            return 1D - storage.getEnergyStored() / (double) storage.getMaxEnergyStored();

        return 1D;
    }

    @Override
    public boolean getShareTag() {
        return true;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new IntEnergyWrapper(stack);
    }

    @Override
    @Nonnull
    @Deprecated
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(@Nonnull EntityEquipmentSlot equipmentSlot) {
        return HashMultimap.create();
    }

    @Nonnull
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(@Nonnull EntityEquipmentSlot equipmentSlot, ItemStack stack) {
        if (hasPower(stack)) {
            Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

            if (equipmentSlot == this.armorType) {
                multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor modifier", (double) this.damageReduceAmount, 0));
                multimap.put(SharedMonsterAttributes.ARMOR_TOUGHNESS.getName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor toughness", (double) this.toughness, 0));
                multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Max Health", this.damageReduceAmount / 2, 0));
            }

            return multimap;
        } else
            return HashMultimap.<String, AttributeModifier>create();
    }

    protected boolean hasPower(ItemStack stack) {
        IEnergyStorage energy = stack.getCapability(ENERGY, null);

        return energy.getEnergyStored() > 0;
    }

    @Override
    @Nonnull
    public String getItemStackDisplayName(@Nonnull ItemStack stack) {
        return TextFormatting.GOLD + super.getItemStackDisplayName(stack);
    }
}
