package com.cjm721.overloaded.item.functional.armor;

import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.util.IModRegistrable;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.dispenser.EquipmentDispenseItemBehavior;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.neoforge.capabilities.Capabilities;

import javax.annotation.Nonnull;
import java.text.NumberFormat;
import java.util.*;

abstract class AbstractMultiArmor extends ArmorItem implements IModRegistrable, IMultiArmor {
    private static final UUID[] ARMOR_MODIFIERS =
            new UUID[]{
                    UUID.fromString("d5764ecb-e212-448f-a472-bb0c41fbccc9"),
                    UUID.fromString("8814d238-8af0-4639-aa27-1822720375e1"),
                    UUID.fromString("7148eab4-7390-43f6-a675-9931750dbde3"),
                    UUID.fromString("7a1424b3-faca-4026-b104-9b3c81bdddee")
            };
    private static final ArmorMaterial pureMatter =
            new ArmorMaterial(-1,
                    Util.make(new EnumMap<>(ArmorType.class), map -> {
                        map.put(ArmorType.BOOTS, 100);
                        map.put(ArmorType.LEGGINGS, 100);
                        map.put(ArmorType.CHESTPLATE, 100);
                        map.put(ArmorType.HELMET, 100);
                        map.put(ArmorType.BODY, 100);
                    }), 100,
                    SoundEvents.ARMOR_EQUIP_GENERIC,
                    100,
                    100,
                    null,
                    null);

    AbstractMultiArmor(ArmorType equipmentSlot, Properties properties) {
        super(
                pureMatter,
                equipmentSlot,
                properties
                        .durability(-1)
                        .stacksTo(1)
                        .rarity(Rarity.EPIC)
                        .fireResistant()
                        .setNoCombineRepair());

        net.minecraft.world.level.block.DispenserBlock.registerBehavior(this, EquipmentDispenseItemBehavior.INSTANCE);
        ModItems.addToSecondaryInit(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(
                Component.literal(
                        "Energy Stored: "
                                + NumberFormat.getInstance().format(stack
                                .getCapability(Capabilities.EnergyStorage.ITEM).getEnergyStored())));
        super.appendHoverText(stack, context,tooltipComponents,tooltipFlag);
    }

    @Override
    public int getEnchantmentLevel(ItemStack stack, Holder<Enchantment> enchantment) {
        return 15;
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return stack.getCount() == 1 && super.supportsEnchantment(stack, enchantment);
    }

//    @Override
//    public double getDurabilityForDisplay(ItemStack stack) {
//        LazyOptional<IEnergyStorage> optionalStorage = stack.getCapability(ENERGY);
//
//        if (optionalStorage.isPresent()) {
//            return 1D
//                    - optionalStorage
//                    .map(energy -> energy.getEnergyStored() / (double) energy.getMaxEnergyStored())
//                    .orElseThrow(() -> new RuntimeException("Impossible Condition"));
//        }
//        return 1D;
//    }
//
//    @Nullable
//    @Override
//    public CompoundNBT getShareTag(ItemStack stack) {
//        return stack.getTag();
//    }
//
//    @Nullable
//    @Override
//    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
//        return new CapabilityContainer()
//                .addCapability(collectCapabilities(new LinkedList<>(), stack, nbt));
//    }
//
//    Collection<ICapabilityProvider> collectCapabilities(
//            @Nonnull Collection<ICapabilityProvider> collection,
//            ItemStack stack,
//            @Nullable CompoundNBT nbt) {
//        collection.add(new IntEnergyWrapper(stack));
//
//        return collection;
//    }

//  @Override
//  public Multimap<Attribute, AttributeModifier> getAttributeModifiers(
//      EquipmentSlotType slot, ItemStack stack) {
//    Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create(super.getAttributeModifiers(slot, stack));
//    if (slot == this.getSlot()) {
//      multimap.put(
//          Attributes.MAX_HEALTH,
//          new AttributeModifier(
//              ARMOR_MODIFIERS[slot.getIndex()],
//              "Max Health",
//              hasPower(stack) ? this.getDefense() / 2.0 : 0,
//              AttributeModifier.Operation.ADDITION));
//    }
//    return multimap;
//  }

    private boolean hasPower(ItemStack stack) {
        return stack.getCapability(Capabilities.EnergyStorage.ITEM).getEnergyStored() > 0;
    }

//    @Override
//    @Nonnull
//    public ITextComponent getName(@Nonnull ItemStack stack) {
//        ITextComponent name = super.getName(stack);
//        name.getStyle().applyFormat(TextFormatting.GOLD);
//        return name;
//    }

    @Override
    public boolean isFoil(@Nonnull ItemStack stack) {
        return false;
    }
}
