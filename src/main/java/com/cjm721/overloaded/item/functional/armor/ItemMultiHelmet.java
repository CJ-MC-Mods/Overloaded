package com.cjm721.overloaded.item.functional.armor;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.network.packets.MultiArmorSettingsMessage;
import com.cjm721.overloaded.storage.IGenericDataStorage;
import com.google.common.primitives.Floats;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Map;

import static com.cjm721.overloaded.capabilities.CapabilityGenericDataStorage.GENERIC_DATA_STORAGE_ITEM;
import static com.cjm721.overloaded.item.functional.armor.MultiArmorConstants.DataKeys;

public class ItemMultiHelmet extends AbstractMultiArmor {

  public ItemMultiHelmet(Item.Properties properties) {
    super(ArmorType.HELMET, properties);
  }

//  @Nullable
//  @Override
//  @OnlyIn(Dist.CLIENT)
//  public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A baseModel) {
//    if (!OverloadedConfig.INSTANCE.textureResolutions.multiArmorFancyModel) {
//      return super.getArmorModel(entityLiving, itemStack, armorSlot, baseModel);
//    }
//
//    if (RenderMultiHelmet.INSTANCE == null) {
//      RenderMultiHelmet.INSTANCE = new RenderMultiHelmet(baseModel);
//    }
//
//    return (A) RenderMultiHelmet.INSTANCE;
//  }
//
  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
//    ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
//    //    ModelLoader.setCustomModelResourceLocation(this, 0, location);
//
//    ImageUtil.registerDynamicTexture(
//        new ResourceLocation(MODID, "textures/item/multi_helmet.png"),
//        OverloadedConfig.INSTANCE.textureResolutions.multiArmorResolution);
  }
//
//  public void updateSettings(
//      ServerPlayerEntity ServerPlayerEntity, MultiArmorSettingsMessage message) {
//    for (ItemStack itemStack : ServerPlayerEntity.getArmorSlots()) {
//      if (itemStack.getItem() == this) {
//        updateSettings(itemStack, message);
//      }
//    }
//  }
//
//  @Override
//  public Collection<ICapabilityProvider> collectCapabilities(
//      @Nonnull Collection<ICapabilityProvider> collection,
//      ItemStack stack,
//      @Nullable CompoundTag nbt) {
//    collection.add(new GenericDataCapabilityProviderWrapper(stack));
//    return super.collectCapabilities(collection, stack, nbt);
//  }

  private void updateSettings(ItemStack itemStack, MultiArmorSettingsMessage message) {
    IGenericDataStorage opSettings = itemStack.getCapability(GENERIC_DATA_STORAGE_ITEM);
    if (opSettings == null) {
      Overloaded.logger.warn("MultiHelmet has no GenericData Capability? NBT: " + itemStack.getAttributeModifiers());
      return;
    }

    opSettings.suggestUpdate();

    Map<String, Float> floats = opSettings.getFloatMap();
    floats.put(
        DataKeys.FLIGHT_SPEED,
        Floats.constrainToRange(
            message.flightSpeed,
            0,
            (float) OverloadedConfig.INSTANCE.multiArmorConfig.maxFlightSpeed));
    floats.put(
        DataKeys.GROUND_SPEED,
        Floats.constrainToRange(
            message.groundSpeed,
            0,
            (float) OverloadedConfig.INSTANCE.multiArmorConfig.maxGroundSpeed));

    Map<String, Boolean> booleans = opSettings.getBooleanMap();
    booleans.put(DataKeys.NOCLIP_FLIGHT_LOCK, message.noclipFlightLock);
    booleans.put(DataKeys.FLIGHT, message.flight);
    booleans.put(DataKeys.FEED, message.feed);
    booleans.put(DataKeys.HEAL, message.heal);
    booleans.put(DataKeys.REMOVE_HARMFUL, message.removeHarmful);
    booleans.put(DataKeys.GIVE_AIR, message.air);
    booleans.put(DataKeys.EXTINGUISH, message.extinguish);

    opSettings.suggestSave();
  }
}
