package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.item.ModItem;
import com.cjm721.overloaded.network.OverloadedGuiHandler;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.InteractionResult;
import net.minecraft.util.Hand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.World;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;

import net.minecraft.world.item.Item.Properties;

public class ItemSettingEditor extends ModItem {

  public ItemSettingEditor() {
    super(new Properties().stacksTo(1));
    setRegistryName("settings_editor");
  }

  @Override
  @Nonnull
  public ActionResult<ItemStack> use(
      World worldIn, PlayerEntity playerIn, Hand handIn) {
    if (worldIn.isClientSide) {
      OverloadedGuiHandler.openMultiArmorGUI();
    }

    return new ActionResult<>(InteractionResult.SUCCESS, playerIn.getItemInHand(handIn));
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
