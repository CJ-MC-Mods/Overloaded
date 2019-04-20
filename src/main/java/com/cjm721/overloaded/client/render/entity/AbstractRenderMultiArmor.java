package com.cjm721.overloaded.client.render.entity;

import net.minecraft.client.renderer.entity.model.ModelBiped;

import static com.cjm721.overloaded.Overloaded.cachedConfig;

public abstract class AbstractRenderMultiArmor extends ModelBiped {

    public AbstractRenderMultiArmor() {
        super(0, 0, cachedConfig.textureResolutions.multiArmorResolution, cachedConfig.textureResolutions.multiArmorResolution);
    }
}

