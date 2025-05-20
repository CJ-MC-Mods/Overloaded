package com.cjm721.overloaded.config;

import com.cjm721.overloaded.Overloaded;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import javax.annotation.Nonnull;
import java.util.Map;

@EventBusSubscriber(modid = Overloaded.MODID, bus = EventBusSubscriber.Bus.MOD)
public class OverloadedConfig {

  @Nonnull public static final OverloadedConfig INSTANCE = new OverloadedConfig();

  private final Map<ModConfig.Type, ModConfigSpec> configSpecs = Maps.newConcurrentMap();

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
  public static void onLoading(ModConfigEvent.Loading loading) {
    INSTANCE.updateConfigs(loading.getConfig().getType());
  }

  @SubscribeEvent
  public static void onLoading(ModConfigEvent.Reloading loading) {
    INSTANCE.updateConfigs(loading.getConfig().getType());
  }

  public ModConfigSpec getConfig(ModConfig.Type type) {
    ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

    configsSections.forEach(c -> c.appendToBuilder(type, builder));

    ModConfigSpec spec = builder.build();
    configSpecs.put(type, spec);
    return spec;
  }

  private void updateConfigs(ModConfig.Type type) {
    configsSections.forEach(cs -> cs.update(type));
  }
}
