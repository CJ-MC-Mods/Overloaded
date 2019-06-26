package com.cjm721.overloaded.item.functional.armor;

import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;

import static com.cjm721.overloaded.Overloaded.MODID;
import static com.cjm721.overloaded.item.functional.armor.MultiArmorConstants.DataKeys;
import static com.cjm721.overloaded.storage.GenericDataStorage.GENERIC_DATA_STORAGE;

public class ItemMultiHelmet extends AbstractMultiArmor {

  private RenderMultiHelmet armorModel;

  public ItemMultiHelmet() {
    super(EquipmentSlotType.HEAD);

    setRegistryName("multi_helmet");
    //        setTranslationKey("multi_helmet");
  }

  @Nullable
  @Override
  @OnlyIn(Dist.CLIENT)
  public <A extends BipedModel<?>> A getArmorModel(
      LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
    if (armorModel == null) armorModel = new RenderMultiHelmet();
    return (A) armorModel;
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
    //    ModelLoader.setCustomModelResourceLocation(this, 0, location);

    ImageUtil.registerDynamicTexture(
        new ResourceLocation(MODID, "textures/armors/multi_helmet.png"),
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
    IGenericDataStorage settings = itemStack.getCapability(GENERIC_DATA_STORAGE).orElse(null);
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
