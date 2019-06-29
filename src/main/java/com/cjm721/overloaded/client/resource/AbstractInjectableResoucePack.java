package com.cjm721.overloaded.client.resource;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.FallbackResourceManager;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.SimpleReloadableResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractInjectableResoucePack implements IResourcePack {
  public final void inject() {
    Minecraft.getInstance().getResourceManager().addResourcePack(this);

//        List<IResourcePack> defaultResourcePacks =
//            ReflectionHelper.getPrivateValue(
//                Minecraft.class, Minecraft.getInstance(), "field_110449_ao",
//     "defaultResourcePacks");
//        defaultResourcePacks.add(this);
//
//        Map<String, FallbackResourceManager> domainResourceManagers =
//            ReflectionHelper.getPrivateValue(
//                SimpleReloadableResourceManager.class,
//                (SimpleReloadableResourceManager) Minecraft.getInstance().getResourceManager(),
//                "field_110548_a",
//                "domainResourceManagers");
//        domainResourceManagers.get("overloaded").addResourcePack(this);
  }
}
