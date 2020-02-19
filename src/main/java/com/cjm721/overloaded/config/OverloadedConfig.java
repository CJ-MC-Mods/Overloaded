package com.cjm721.overloaded.config;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.config.syncer.SyncToClient;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.google.common.collect.ImmutableList;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;

@Mod.EventBusSubscriber(modid = Overloaded.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OverloadedConfig {

  public static OverloadedConfig INSTANCE = new OverloadedConfig();

  @SyncToClient public MultiToolConfig multiToolConfig;

  @SyncToClient public MultiArmorConfig multiArmorConfig;

  @SyncToClient public DevelopmentConfig developmentConfig;

  public ResolutionConfig textureResolutions;

  @SyncToClient public PurifierConfig purifierConfig;

  public SpecialConfig specialConfig;

  @SyncToClient public RayGunConfig rayGun;

  @SyncToClient public RailGunConfig railGun;

  public ProductionConfig productionConfig;

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
  public static void onConfigRelaoding(ModConfig.Reloading configReloading) {
    INSTANCE.updateConfigs();
  }

  public ForgeConfigSpec getConfig(ModConfig.Type type) {
    ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

    configsSections.stream().forEach(c -> c.appendToBuilder(type, builder));

    return builder.build();
  }

  private void updateConfigs() {
    configsSections.stream().forEach(ConfigSectionHandler::update);
  }
}
