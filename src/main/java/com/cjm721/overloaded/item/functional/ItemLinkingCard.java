package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.item.ModItem;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

import static com.cjm721.overloaded.Overloaded.MODID;

public class ItemLinkingCard extends ModItem {

  public ItemLinkingCard() {
    super(new Properties().maxStackSize(1));
    setRegistryName("linking_card");
    //        setTranslationKey("linking_card");
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void addInformation(
      ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    CompoundNBT tag = stack.getTag();
    if (tag != null && tag.contains("TYPE")) {
      String type = tag.getString("TYPE");
      int x = tag.getInt("X");
      int y = tag.getInt("Y");
      int z = tag.getInt("Z");
      int worldID = tag.getInt("WORLD");

      tooltip.add(
          new StringTextComponent(
              String.format("Bound to %s at %d:%d,%d,%d", type, worldID, x, y, z)));
    }
    super.addInformation(stack, worldIn, tooltip, flagIn);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    ModelResourceLocation location =
        new ModelResourceLocation(new ResourceLocation(MODID, "linking_card"), null);
    //        ModelLoader.setCustomModelResourceLocation(this, 0, location);

    ImageUtil.registerDynamicTexture(
        new ResourceLocation(MODID, "textures/item/linking_card.png"),
        OverloadedConfig.INSTANCE.textureResolutions.itemResolution);
  }
}
