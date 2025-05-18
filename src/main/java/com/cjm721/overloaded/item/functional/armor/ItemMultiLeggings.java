package com.cjm721.overloaded.item.functional.armor;

import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class ItemMultiLeggings extends AbstractMultiArmor {

  public ItemMultiLeggings(Properties  properties) {
    super(ArmorType.LEGGINGS,  properties);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
//    ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
//    //    ModelLoader.setCustomModelResourceLocation(this, 0, location);
//
//    ImageUtil.registerDynamicTexture(
//        new ResourceLocation(MODID, "textures/item/multi_leg.png"),
//        OverloadedConfig.INSTANCE.textureResolutions.multiArmorResolution);
//    ImageUtil.registerDynamicTexture(
//        new ResourceLocation(MODID, "textures/item/multi_belt.png"),
//        OverloadedConfig.INSTANCE.textureResolutions.multiArmorResolution);
  }

//  @Nullable
//  @Override
//  @OnlyIn(Dist.CLIENT)
//  public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A baseModel) {
//    if (!OverloadedConfig.INSTANCE.textureResolutions.multiArmorFancyModel) {
//      return super.getArmorModel(entityLiving, itemStack, armorSlot, baseModel);
//    }
//
//    if (RenderMultiLeggings.INSTANCE == null) {
//      RenderMultiLeggings.INSTANCE = new RenderMultiLeggings(baseModel);
//    }
//
//    return (A) RenderMultiLeggings.INSTANCE;
//  }

}
