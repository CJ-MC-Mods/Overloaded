package com.cjm721.overloaded.client.resource;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.FallbackResourceManager;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.SimpleReloadableResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractInjectableResourcePack implements IResourcePack {
    public final void inject() {

        List<IResourcePack> defaultResourcePacks =
                ObfuscationReflectionHelper.getPrivateValue(
                        Minecraft.class, Minecraft.getInstance(), "field_110449_ao");
        defaultResourcePacks.add(this);

        Map<String, FallbackResourceManager> domainResourceManagers =
                ObfuscationReflectionHelper.getPrivateValue(
                        SimpleReloadableResourceManager.class,
                        (SimpleReloadableResourceManager) Minecraft.getInstance().getResourceManager(),
                        "field_110548_a");
        domainResourceManagers.get("overloaded").add(this);
    }
}
