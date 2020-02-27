package com.cjm721.overloaded.config;

import com.cjm721.overloaded.Overloaded;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import javax.annotation.Nonnull;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Overloaded.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OverloadedConfig {

  @Nonnull
  public static final OverloadedConfig INSTANCE = new OverloadedConfig();

  private final Map<ModConfig.Type, ForgeConfigSpec> configSpecs = Maps.newConcurrentMap();

  public final MultiToolConfig multiToolConfig;
  public final MultiArmorConfig multiArmorConfig;
  public final DevelopmentConfig developmentConfig;
  public final ResolutionConfig textureResolutions;
  public final PurifierConfig purifierConfig;
  public final SpecialConfig specialConfig;
  public final RayGunConfig rayGun;
  public final RailGunConfig railGun;
  public final ProductionConfig productionConfig;

  private final ImmutableList<ConfigSectionHandler> configsSections;

  private OverloadedConfig() {
    multiToolConfig = new MultiToolConfig();
    multiArmorConfig = new MultiArmorConfig();
    developmentConfig = new DevelopmentConfig();
    textureResolutions = new ResolutionConfig();
    purifierConfig = new PurifierConfig();
    specialConfig = new SpecialConfig();
    rayGun = new RayGunConfig();
    railGun = new RailGunConfig();
    productionConfig = new ProductionConfig();

    configsSections =
        ImmutableList.of(
            multiToolConfig,
            multiArmorConfig,
            developmentConfig,
            textureResolutions,
            purifierConfig,
            specialConfig,
            rayGun,
            railGun,
            productionConfig);
  }


  @SubscribeEvent
  public static void onLoading(ModConfig.Loading loading) {
    INSTANCE.updateConfigs();
  }

  @SubscribeEvent
  public static void onConfigReloading(ModConfig.Reloading configReloading) {
    INSTANCE.updateConfigs();
  }

  public ForgeConfigSpec getConfig(ModConfig.Type type) {
    ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

    configsSections.forEach(c -> c.appendToBuilder(type, builder));

    ForgeConfigSpec spec = builder.build();
    configSpecs.put(type, spec);
    return spec;
  }

  private void updateConfigs() {
    configsSections.forEach(ConfigSectionHandler::update);
  }
}
