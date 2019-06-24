package com.cjm721.overloaded.item.crafting;

import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.item.ModItem;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.cjm721.overloaded.Overloaded.MODID;

public class ItemEnergyCore extends ModItem {

  public ItemEnergyCore() {
    super(new Properties().maxStackSize(64));
    setRegistryName("energy_core");
    //        setTranslationKey("energy_core");
  }

  @Override
  public int getItemStackLimit(ItemStack stack) {
    return 64;
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void registerModel() {
    ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
    //        ModelLoader.setCustomModelResourceLocation(this, 0, location);

    ImageUtil.registerDynamicTexture(
        new ResourceLocation(MODID, "textures/items/energy_core.png"),
        OverloadedConfig.INSTANCE.textureResolutions.itemResolution);
  }
}
