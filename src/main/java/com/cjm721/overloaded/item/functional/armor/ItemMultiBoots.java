package com.cjm721.overloaded.item.functional.armor;

import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.client.render.entity.RenderMultiBoots;
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

public class ItemMultiBoots extends AbstractMultiArmor {

  public ItemMultiBoots() {
    super(EquipmentSlotType.FEET);

    setRegistryName("multi_boots");
    //        setTranslationKey("multi_boots");
  }

  @Nullable
  @Override
  public <A extends BipedModel<?>> A getArmorModel(
      LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
    if (armorModel == null) armorModel = new RenderMultiBoots();
    return (A) armorModel;
  }

  private RenderMultiBoots armorModel;

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
    //    ModelLoader.setCustomModelResourceLocation(this, 0, location);

    ImageUtil.registerDynamicTexture(
        new ResourceLocation(MODID, "textures/armors/multi_boot.png"),
        OverloadedConfig.INSTANCE.textureResolutions.multiArmorResolution);
  }
}
