package com.cjm721.overloaded.item.functional.armor;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.client.render.entity.RenderMultiChestplate;
import com.cjm721.overloaded.client.render.entity.RenderMultiHelmet;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.network.packets.MultiArmorSettingsMessage;
import com.cjm721.overloaded.storage.IGenericDataStorage;
import com.cjm721.overloaded.storage.itemwrapper.GenericDataCapabilityProviderWrapper;
import com.google.common.primitives.Floats;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;

import static com.cjm721.overloaded.Overloaded.MODID;
import static com.cjm721.overloaded.capabilities.CapabilityGenericDataStorage.GENERIC_DATA_STORAGE;
import static com.cjm721.overloaded.item.functional.armor.MultiArmorConstants.DataKeys;

public class ItemMultiHelmet extends AbstractMultiArmor {

  public ItemMultiHelmet() {
    super(EquipmentSlotType.HEAD);

    setRegistryName("multi_helmet");
  }

  @Nullable
  @Override
  @OnlyIn(Dist.CLIENT)
  public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A baseModel) {
    if (!OverloadedConfig.INSTANCE.textureResolutions.multiArmorFancyModel) {
      return super.getArmorModel(entityLiving, itemStack, armorSlot, baseModel);
    }

    if (RenderMultiHelmet.INSTANCE == null) {
      RenderMultiHelmet.INSTANCE = new RenderMultiHelmet(baseModel);
    }

    return (A) RenderMultiHelmet.INSTANCE;
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
    //    ModelLoader.setCustomModelResourceLocation(this, 0, location);

    ImageUtil.registerDynamicTexture(
        new ResourceLocation(MODID, "textures/item/multi_helmet.png"),
        OverloadedConfig.INSTANCE.textureResolutions.multiArmorResolution);
  }

  public void updateSettings(
      ServerPlayerEntity ServerPlayerEntity, MultiArmorSettingsMessage message) {
    for (ItemStack itemStack : ServerPlayerEntity.getArmorInventoryList()) {
      if (itemStack.getItem() == this) {
        updateSettings(itemStack, message);
      }
    }
  }

  @Override
  public Collection<ICapabilityProvider> collectCapabilities(
      @Nonnull Collection<ICapabilityProvider> collection,
      ItemStack stack,
      @Nullable CompoundNBT nbt) {
    collection.add(new GenericDataCapabilityProviderWrapper(stack));
    return super.collectCapabilities(collection, stack, nbt);
  }

  private void updateSettings(ItemStack itemStack, MultiArmorSettingsMessage message) {
    LazyOptional<IGenericDataStorage> opSettings = itemStack.getCapability(GENERIC_DATA_STORAGE);
    if (!opSettings.isPresent()) {
      Overloaded.logger.warn("MultiHelmet has no GenericData Capability? NBT: " + itemStack.getTag());
      return;
    }

    IGenericDataStorage settings = opSettings.orElseThrow(() -> new RuntimeException("Impossible Condition"));
    settings.suggestUpdate();

    Map<String, Float> floats = settings.getFloatMap();
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

    Map<String, Boolean> booleans = settings.getBooleanMap();
    booleans.put(DataKeys.NOCLIP_FLIGHT_LOCK, message.noclipFlightLock);
    booleans.put(DataKeys.FLIGHT, message.flight);
    booleans.put(DataKeys.FEED, message.feed);
    booleans.put(DataKeys.HEAL, message.heal);
    booleans.put(DataKeys.REMOVE_HARMFUL, message.removeHarmful);
    booleans.put(DataKeys.GIVE_AIR, message.air);
    booleans.put(DataKeys.EXTINGUISH, message.extinguish);

    settings.suggestSave();
  }
}
