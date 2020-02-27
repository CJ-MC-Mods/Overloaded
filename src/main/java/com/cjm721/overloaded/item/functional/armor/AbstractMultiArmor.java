package com.cjm721.overloaded.item.functional.armor;

import com.cjm721.overloaded.OverloadedItemGroups;
import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.storage.builder.CapabilityContainer;
import com.cjm721.overloaded.storage.itemwrapper.IntEnergyWrapper;
import com.cjm721.overloaded.util.IModRegistrable;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
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

abstract class AbstractMultiArmor extends ArmorItem implements IModRegistrable, IMultiArmor {
  private static final UUID[] ARMOR_MODIFIERS =
      new UUID[] {
        UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
        UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
        UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
        UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")
      };
  private static final IArmorMaterial pureMatter =
      new IArmorMaterial() {
        @Override
        public int getDurability(@Nonnull EquipmentSlotType equipmentSlotType) {
          return -1;
        }

        @Override
        public int getDamageReductionAmount(@Nonnull EquipmentSlotType equipmentSlotType) {
          return 100;
        }

        @Override
        public int getEnchantability() {
          return 100;
        }

        @Override
        @Nonnull
        public SoundEvent getSoundEvent() {
          return SoundEvents.ITEM_ARMOR_EQUIP_GOLD;
        }

        @Override
        @Nonnull
        public Ingredient getRepairMaterial() {
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
      };

  AbstractMultiArmor(EquipmentSlotType equipmentSlot) {
    super(
        pureMatter,
        equipmentSlot,
        new Properties()
            .maxDamage(-1)
            .maxStackSize(1)
            .rarity(Rarity.EPIC)
            .group(OverloadedItemGroups.TECH));

    DispenserBlock.registerDispenseBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
    ModItems.addToSecondaryInit(this);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void addInformation(
      ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    stack
        .getCapability(ENERGY)
        .ifPresent(
            handler ->
                tooltip.add(
                    new StringTextComponent(
                        "Energy Stored: "
                            + NumberFormat.getInstance().format(handler.getEnergyStored()))));

    super.addInformation(stack, worldIn, tooltip, flagIn);
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
  public boolean isDamageable() {
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

  @Override
  @Nonnull
  public Multimap<String, AttributeModifier> getAttributeModifiers(
      @Nullable EquipmentSlotType equipmentSlot) {
    return HashMultimap.create();
  }

  @Override
  public Multimap<String, AttributeModifier> getAttributeModifiers(
      EquipmentSlotType slot, ItemStack stack) {
    if (hasPower(stack)) {
      Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot);

      if (slot == this.getEquipmentSlot()) {
        multimap.put(
            SharedMonsterAttributes.ARMOR.getName(),
            new AttributeModifier(
                ARMOR_MODIFIERS[slot.getIndex()],
                "Armor modifier",
                this.damageReduceAmount,
                AttributeModifier.Operation.ADDITION));
        multimap.put(
            SharedMonsterAttributes.ARMOR_TOUGHNESS.getName(),
            new AttributeModifier(
                ARMOR_MODIFIERS[slot.getIndex()],
                "Armor toughness",
                this.toughness,
                AttributeModifier.Operation.ADDITION));
        multimap.put(
            SharedMonsterAttributes.MAX_HEALTH.getName(),
            new AttributeModifier(
                ARMOR_MODIFIERS[slot.getIndex()],
                "Max Health",
                this.damageReduceAmount / 2.0,
                AttributeModifier.Operation.ADDITION));
      }

      return multimap;
    } else {
      return getAttributeModifiers(slot);
    }
  }

  private boolean hasPower(ItemStack stack) {
    return stack.getCapability(ENERGY).map(storage -> storage.getEnergyStored() > 0).orElse(false);
  }

  @Override
  @Nonnull
  public ITextComponent getDisplayName(@Nonnull ItemStack stack) {
    return super.getDisplayName(stack).applyTextStyle(TextFormatting.GOLD);
  }

  @Override
  public boolean hasEffect(ItemStack stack) {
    return false;
  }
}
