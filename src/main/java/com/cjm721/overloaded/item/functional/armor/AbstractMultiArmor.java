package com.cjm721.overloaded.item.functional.armor;

import com.cjm721.overloaded.OverloadedItemGroups;
import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.storage.builder.CapabilityContainer;
import com.cjm721.overloaded.storage.itemwrapper.IntEnergyWrapper;
import com.cjm721.overloaded.util.IModRegistrable;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

import net.minecraft.item.Item.Properties;

abstract class AbstractMultiArmor extends ArmorItem implements IModRegistrable, IMultiArmor {
  private static final UUID[] ARMOR_MODIFIERS =
      new UUID[] {
        UUID.fromString("d5764ecb-e212-448f-a472-bb0c41fbccc9"),
        UUID.fromString("8814d238-8af0-4639-aa27-1822720375e1"),
        UUID.fromString("7148eab4-7390-43f6-a675-9931750dbde3"),
        UUID.fromString("7a1424b3-faca-4026-b104-9b3c81bdddee")
      };
  private static final IArmorMaterial pureMatter =
      new IArmorMaterial() {
        @Override
        public int getDurabilityForSlot(@Nonnull EquipmentSlotType equipmentSlotType) {
          return -1;
        }

        @Override
        public int getDefenseForSlot(@Nonnull EquipmentSlotType equipmentSlotType) {
          return 100;
        }

        @Override
        public int getEnchantmentValue() {
          return 100;
        }

        @Override
        @Nonnull
        public SoundEvent getEquipSound() {
          return SoundEvents.ARMOR_EQUIP_GOLD;
        }

        @Override
        @Nonnull
        public Ingredient getRepairIngredient() {
          return Ingredient.EMPTY;
        }

        @Override
        public String getName() {
          return "overloaded:pure_matter";
        }

        @Override
        public float getToughness() {
          return 100;
        }

        @Override
        public float getKnockbackResistance() {
          return 100;
        }
      };

  AbstractMultiArmor(EquipmentSlotType equipmentSlot) {
    super(
        pureMatter,
        equipmentSlot,
        new Properties()
            .durability(-1)
            .stacksTo(1)
            .rarity(Rarity.EPIC)
            .fireResistant()
            .tab(OverloadedItemGroups.TECH));

    DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
    ModItems.addToSecondaryInit(this);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void appendHoverText(
      ItemStack stack, @Nullable World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
    stack
        .getCapability(ENERGY)
        .ifPresent(
            handler ->
                tooltip.add(
                    new StringTextComponent(
                        "Energy Stored: "
                            + NumberFormat.getInstance().format(handler.getEnergyStored()))));

    super.appendHoverText(stack, worldIn, tooltip, flagIn);
  }

  @Override
  public int getItemEnchantability(ItemStack stack) {
    return 15;
  }

  @Override
  public boolean isEnchantable(@Nonnull ItemStack stack) {
    return this.getItemStackLimit(stack) == 1;
  }

  @Override
  public boolean canBeDepleted() {
    return false;
  }

  @Override
  public boolean showDurabilityBar(ItemStack p_showDurabilityBar_1_) {
    return true;
  }

  @Override
  public double getDurabilityForDisplay(ItemStack stack) {
    LazyOptional<IEnergyStorage> optionalStorage = stack.getCapability(ENERGY);

    if (optionalStorage.isPresent()) {
      return 1D
          - optionalStorage
              .map(energy -> energy.getEnergyStored() / (double) energy.getMaxEnergyStored())
              .orElseThrow(() -> new RuntimeException("Impossible Condition"));
    }
    return 1D;
  }

  @Nullable
  @Override
  public CompoundNBT getShareTag(ItemStack stack) {
    return stack.getTag();
  }

  @Nullable
  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
    return new CapabilityContainer()
        .addCapability(collectCapabilities(new LinkedList<>(), stack, nbt));
  }

  Collection<ICapabilityProvider> collectCapabilities(
      @Nonnull Collection<ICapabilityProvider> collection,
      ItemStack stack,
      @Nullable CompoundNBT nbt) {
    collection.add(new IntEnergyWrapper(stack));

    return collection;
  }

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
    return stack.getCapability(ENERGY).map(storage -> storage.getEnergyStored() > 0).orElse(false);
  }

  @Override
  @Nonnull
  public ITextComponent getName(@Nonnull ItemStack stack) {
    ITextComponent name = super.getName(stack);
    name.getStyle().applyFormat(TextFormatting.GOLD);
    return name;
  }

  @Override
  public boolean isFoil(@Nonnull ItemStack stack) {
    return false;
  }
}
