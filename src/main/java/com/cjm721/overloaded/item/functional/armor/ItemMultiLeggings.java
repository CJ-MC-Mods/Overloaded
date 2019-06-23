package com.cjm721.overloaded.item.functional.armor;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.client.render.entity.RenderMultiLeggings;
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

  private RenderMultiLeggings armorModel;

  public ItemMultiLeggings() {
    super(EquipmentSlotType.LEGS);

    setRegistryName("multi_leggings");
    //    setTranslationKey("multi_leggings");
  }

  @Nullable
  @Override
  public <A extends BipedModel<?>> A getArmorModel(
      LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
    if (armorModel == null) armorModel = new RenderMultiLeggings();

    return (A) armorModel;
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
    //    ModelLoader.setCustomModelResourceLocation(this, 0, location);

    ImageUtil.registerDynamicTexture(
        new ResourceLocation(MODID, "textures/armors/multi_leg.png"),
        Overloaded.cachedConfig.textureResolutions.multiArmorResolution);
    ImageUtil.registerDynamicTexture(
        new ResourceLocation(MODID, "textures/armors/multi_belt.png"),
        Overloaded.cachedConfig.textureResolutions.multiArmorResolution);
  }
}
