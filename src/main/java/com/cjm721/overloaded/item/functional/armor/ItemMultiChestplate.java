package com.cjm721.overloaded.item.functional.armor;

import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.client.render.entity.RenderMultiChestplate;
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

public class ItemMultiChestplate extends AbstractMultiArmor {

  public ItemMultiChestplate() {
    super(EquipmentSlotType.CHEST);

    setRegistryName("multi_chestplate");
    //        setTranslationKey("multi_chestplate");
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    ImageUtil.registerDynamicTexture(
        new ResourceLocation(MODID, "textures/item/multi_body.png"),
        OverloadedConfig.INSTANCE.textureResolutions.multiArmorResolution);
    ImageUtil.registerDynamicTexture(
        new ResourceLocation(MODID, "textures/item/multi_left_arm.png"),
        OverloadedConfig.INSTANCE.textureResolutions.multiArmorResolution);
    ImageUtil.registerDynamicTexture(
        new ResourceLocation(MODID, "textures/item/multi_right_arm.png"),
        OverloadedConfig.INSTANCE.textureResolutions.multiArmorResolution);
  }
}
