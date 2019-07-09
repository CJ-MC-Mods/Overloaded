package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.item.ModItem;
import com.cjm721.overloaded.network.OverloadedGuiHandler;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;

public class ItemSettingEditor extends ModItem {

  public ItemSettingEditor() {
    super(new Properties().maxStackSize(1));
    setRegistryName("settings_editor");
  }

  @Override
  @Nonnull
  public ActionResult<ItemStack> onItemRightClick(
      World worldIn, PlayerEntity playerIn, Hand handIn) {
    if (worldIn.isRemote) {
      OverloadedGuiHandler.openMultiArmorGUI();
    }

    return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    ModelResourceLocation location =
        new ModelResourceLocation(new ResourceLocation(MODID, "settings_editor"), null);
    //        ModelLoader.setCustomModelResourceLocation(this, 0, location);

    ImageUtil.registerDynamicTexture(
        new ResourceLocation(MODID, "textures/item/settings_editor.png"),
        OverloadedConfig.INSTANCE.textureResolutions.itemResolution);
  }
}
