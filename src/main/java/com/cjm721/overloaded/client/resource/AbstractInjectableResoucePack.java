package com.cjm721.overloaded.client.resource;

import net.minecraft.resources.IResourcePack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractInjectableResoucePack implements IResourcePack {
  public final void inject() {
    //    List<IResourcePack> defaultResourcePacks =
    //        ReflectionHelper.getPrivateValue(
    //            Minecraft.class, Minecraft.getInstance(), "field_110449_ao",
    // "defaultResourcePacks");
    //    defaultResourcePacks.add(this);
    //
    //    Map<String, FallbackResourceManager> domainResourceManagers =
    //        ReflectionHelper.getPrivateValue(
    //            SimpleReloadableResourceManager.class,
    //            (SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager(),
    //            "field_110548_a",
    //            "domainResourceManagers");
    //    domainResourceManagers.get("overloaded").addResourcePack(this);
  }
}
