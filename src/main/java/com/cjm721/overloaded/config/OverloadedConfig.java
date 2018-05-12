package com.cjm721.overloaded.config;

import com.cjm721.overloaded.config.syncer.SyncToClient;
import net.minecraftforge.common.config.Config;

public class OverloadedConfig {
    @Config.Name("Compressed Blocks")
    public CompressedConfig compressedConfig = new CompressedConfig();
    @SyncToClient
    @Config.Name("Multi-Tool")
    public MultiToolConfig multiToolConfig = new MultiToolConfig();
    @SyncToClient
    @Config.Name("Multi-Armor")
    public MultiArmorConfig multiArmorConfig = new MultiArmorConfig();
    @SyncToClient
    @Config.Name("Development")
    public DevelopmentConfig developmentConfig = new DevelopmentConfig();
    @Config.Name("Texture Resolutions")
    public ResolutionConfig textureResolutions = new ResolutionConfig();
    @SyncToClient
    @Config.Name("Matter Purifier")
    public PurifierConfig purifierConfig = new PurifierConfig();
    @Config.Name("Special Entires")
    public SpecialConfig specialConfig = new SpecialConfig();
    @SyncToClient
    @Config.Name("Ray Gun")
    public RayGunConfig rayGun = new RayGunConfig();
    @SyncToClient
    @Config.Name("Rail Gun")
    public RailGunConfig railGun = new RailGunConfig();
}
