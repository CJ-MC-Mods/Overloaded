package com.cjm721.overloaded.item.functional.armor;

import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.client.render.entity.RenderMultiHelmet;
import com.cjm721.overloaded.client.render.entity.RenderMultiLeggings;
import com.cjm721.overloaded.config.OverloadedConfig;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;

public class ItemMultiLeggings extends AbstractMultiArmor {

  public ItemMultiLeggings() {
    super(EquipmentSlotType.LEGS);

    setRegistryName("multi_leggings");
    //    setTranslationKey("multi_leggings");
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
    //    ModelLoader.setCustomModelResourceLocation(this, 0, location);

    ImageUtil.registerDynamicTexture(
        new ResourceLocation(MODID, "textures/item/multi_leg.png"),
        OverloadedConfig.INSTANCE.textureResolutions.multiArmorResolution);
    ImageUtil.registerDynamicTexture(
        new ResourceLocation(MODID, "textures/item/multi_belt.png"),
        OverloadedConfig.INSTANCE.textureResolutions.multiArmorResolution);
  }

  @Nullable
  @Override
  public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A baseModel) {
    if (!OverloadedConfig.INSTANCE.textureResolutions.multiArmorFancyModel) {
      return super.getArmorModel(entityLiving, itemStack, armorSlot, baseModel);
    }

    if (RenderMultiLeggings.INSTANCE == null) {
      RenderMultiLeggings.INSTANCE = new RenderMultiLeggings(baseModel);
    }

    return (A) RenderMultiLeggings.INSTANCE;
  }

}
