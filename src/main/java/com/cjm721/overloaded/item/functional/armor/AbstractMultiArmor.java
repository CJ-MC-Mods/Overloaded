package com.cjm721.overloaded.item.functional.armor;

import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.util.IModRegistrable;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.ArmorMaterial;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.text.NumberFormat;
import java.util.*;

import static com.cjm721.overloaded.Overloaded.MODID;

public abstract class AbstractMultiArmor extends ArmorItem implements IModRegistrable, IMultiArmor {

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, MODID);

    private static final UUID[] ARMOR_MODIFIERS =
            new UUID[]{
                    UUID.fromString("d5764ecb-e212-448f-a472-bb0c41fbccc9"),
                    UUID.fromString("8814d238-8af0-4639-aa27-1822720375e1"),
                    UUID.fromString("7148eab4-7390-43f6-a675-9931750dbde3"),
                    UUID.fromString("7a1424b3-faca-4026-b104-9b3c81bdddee")
            };
    public static final Holder<ArmorMaterial> pureMatter =
            ARMOR_MATERIALS.register("multi_armor", () -> new ArmorMaterial(
                    Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 100);
                        map.put(ArmorItem.Type.LEGGINGS, 100);
                        map.put(ArmorItem.Type.CHESTPLATE, 100);
                        map.put(ArmorItem.Type.HELMET, 100);
                        map.put(ArmorItem.Type.BODY, 100);
                    }), 100,
                    SoundEvents.ARMOR_EQUIP_GENERIC,
                    () -> Ingredient.of(),
                    List.of(),
                    100,
                    100));

    AbstractMultiArmor(ArmorItem.Type equipmentSlot, Properties properties) {
        super(
                pureMatter,
                equipmentSlot,
                properties
                        .durability(-1)
                        .stacksTo(1)
                        .rarity(Rarity.EPIC)
                        .fireResistant());

        net.minecraft.world.level.block.DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
        ModItems.addToSecondaryInit(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        @Nullable IEnergyStorage energy = stack
                .getCapability(Capabilities.EnergyStorage.ITEM);
        if (energy != null) {
            tooltipComponents.add(
                    Component.literal(
                            "Energy Stored: "
                                    + NumberFormat.getInstance().format(energy.getEnergyStored())));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
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
//    public CompoundTag getShareTag(ItemStack stack) {
//        return stack.getTag();
//    }
//
//    @Nullable
//    @Override
//    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
//        return new CapabilityContainer()
//                .addCapability(collectCapabilities(new LinkedList<>(), stack, nbt));
//    }
//
//    Collection<ICapabilityProvider> collectCapabilities(
//            @Nonnull Collection<ICapabilityProvider> collection,
//            ItemStack stack,
//            @Nullable CompoundTag nbt) {
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
