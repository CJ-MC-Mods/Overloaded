package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.item.ModItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

public class ItemLinkingCard extends ModItem {

  public ItemLinkingCard(Properties properties) {
    super(properties.stacksTo(1));
  }


  @Override
  public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
    CompoundTag tag = new CompoundTag();//stack.getTag();
    if (tag != null && tag.contains("TYPE")) {
      String type = tag.getString("TYPE");
      int x = tag.getInt("X");
      int y = tag.getInt("Y");
      int z = tag.getInt("Z");
      String worldID = tag.getString("WORLD");

      tooltipComponents.add(
          Component.literal(
              String.format("Bound to %s at %s: %d,%d,%d", type, worldID, x, y, z)));
    }
    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
//    ModelResourceLocation location =
//        new ModelResourceLocation(new ResourceLocation(MODID, "linking_card"), null);
//    //        ModelLoader.setCustomModelResourceLocation(this, 0, location);
//
//    ImageUtil.registerDynamicTexture(
//        new ResourceLocation(MODID, "textures/item/linking_card.png"),
//        OverloadedConfig.INSTANCE.textureResolutions.itemResolution);
  }
}
