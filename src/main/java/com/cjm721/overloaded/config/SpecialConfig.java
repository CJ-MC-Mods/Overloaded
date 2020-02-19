package com.cjm721.overloaded.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class SpecialConfig implements ConfigSectionHandler {
  public boolean noClipRenderFix;
  private ForgeConfigSpec.BooleanValue noClipRenderFixSpec;

  public boolean infinityBarrelAdditionalSlot;
  private ForgeConfigSpec.BooleanValue infinityBarrelAdditionalSlotSpec;

  @Override
  public void appendToBuilder(ModConfig.Type type, ForgeConfigSpec.Builder builder) {
    if (type != ModConfig.Type.COMMON) {
      return;
    }

    builder.push("special");

    noClipRenderFixSpec =
        builder
            .comment(
                "Fix for rending while noClip is active. May cause issues with some other mods. [Default: true]")
            .define("noClipRenderFix", true);

    infinityBarrelAdditionalSlotSpec =
        builder
            .comment(
                "Do not change without reading https://github.com/CJ-MC-Mods/Overloaded/wiki/Minecraft-Inventory-Mechanic-Bugs#slots-for-infinity-barrel. Reduces performance for compatibility. [Default:: false]")
            .define("infinityBarrelAdditionalSlot", false);

    builder.pop();
  }

  @Override
  public void update() {
    noClipRenderFix = noClipRenderFixSpec.get();
    infinityBarrelAdditionalSlot = infinityBarrelAdditionalSlotSpec.get();
  }
}
