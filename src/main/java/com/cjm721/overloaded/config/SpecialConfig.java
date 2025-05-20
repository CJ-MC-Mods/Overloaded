package com.cjm721.overloaded.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.fml.config.ModConfig;

public class SpecialConfig implements ConfigSectionHandler {
  public boolean noClipRenderFix;
  private ModConfigSpec.BooleanValue noClipRenderFixSpec;

  public boolean infinityBarrelAdditionalSlot;
  private ModConfigSpec.BooleanValue infinityBarrelAdditionalSlotSpec;

  @Override
  public void appendToBuilder(ModConfig.Type type, ModConfigSpec.Builder builder) {
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
  public void update(ModConfig.Type type) {
    if (type != ModConfig.Type.COMMON) {
      return;
    }
    noClipRenderFix = noClipRenderFixSpec.get();
    infinityBarrelAdditionalSlot = infinityBarrelAdditionalSlotSpec.get();
  }
}
