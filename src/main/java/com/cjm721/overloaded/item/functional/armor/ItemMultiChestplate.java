package com.cjm721.overloaded.item.functional.armor;

import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class ItemMultiChestplate extends AbstractMultiArmor {

  public ItemMultiChestplate(Properties properties) {
    super(ArmorType.CHESTPLATE,  properties);

    //        setTranslationKey("multi_chestplate");
  }

//  @Nullable
//  @Override
//  @OnlyIn(Dist.CLIENT)
//  public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A baseModel) {
//    if (!OverloadedConfig.INSTANCE.textureResolutions.multiArmorFancyModel) {
//      return super.getArmorModel(entityLiving, itemStack, armorSlot, baseModel);
//    }
//
//    if (RenderMultiChestplate.INSTANCE == null) {
//      RenderMultiChestplate.INSTANCE = new RenderMultiChestplate(baseModel);
//    }
//
//    return (A) RenderMultiChestplate.INSTANCE;
//  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
//    ImageUtil.registerDynamicTexture(
//        new ResourceLocation(MODID, "textures/item/multi_body.png"),
//        OverloadedConfig.INSTANCE.textureResolutions.multiArmorResolution);
//    ImageUtil.registerDynamicTexture(
//        new ResourceLocation(MODID, "textures/item/multi_left_arm.png"),
//        OverloadedConfig.INSTANCE.textureResolutions.multiArmorResolution);
//    ImageUtil.registerDynamicTexture(
//        new ResourceLocation(MODID, "textures/item/multi_right_arm.png"),
//        OverloadedConfig.INSTANCE.textureResolutions.multiArmorResolution);
  }
}
