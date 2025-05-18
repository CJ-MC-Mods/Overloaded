package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.item.ModItem;
import com.cjm721.overloaded.network.OverloadedGuiHandler;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class ItemSettingEditor extends ModItem {

  public ItemSettingEditor() {
    super(new Properties().stacksTo(1));
  }

  @Override
  public InteractionResult use(Level worldIn, Player player, InteractionHand hand) {
    if (worldIn.isClientSide) {
      OverloadedGuiHandler.openMultiArmorGUI();
    }

    return InteractionResult.SUCCESS;
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
//    ModelResourceLocation location =
//        new ModelResourceLocation(new ResourceLocation(MODID, "settings_editor"), null);
//    //        ModelLoader.setCustomModelResourceLocation(this, 0, location);
//
//    ImageUtil.registerDynamicTexture(
//        new ResourceLocation(MODID, "textures/item/settings_editor.png"),
//        OverloadedConfig.INSTANCE.textureResolutions.itemResolution);
  }
}
