//package com.cjm721.overloaded.client.resource;
//
//import net.minecraft.client.Minecraft;
//import net.minecraft.resources.FallbackResourceManager;
//import net.minecraft.server.packs.PackLocationInfo;
//import net.minecraft.server.packs.PackResources;
//import net.minecraft.resources.SimpleReloadableResourceManager;
//import net.minecraft.server.packs.PackSelectionConfig;
//import net.minecraft.server.packs.repository.Pack;
//import net.neoforged.api.distmarker.Dist;
//import net.neoforged.api.distmarker.OnlyIn;
//import net.neoforged.fml.common.ObfuscationReflectionHelper;
//
//import java.util.List;
//import java.util.Map;
//
//@OnlyIn(Dist.CLIENT)
//public abstract class AbstractInjectableResourcePack extends Pack {
//
//    public AbstractInjectableResourcePack(PackLocationInfo location, ResourcesSupplier resources, Metadata metadata, PackSelectionConfig selectionConfig) {
//        super(location, resources, metadata, selectionConfig);
//    }
//
//    public final void inject() {
//
//        Minecraft.getInstance().getResourcePackRepository().addPackFinder(consumer -> consumer.accept(this));
//        Minecraft.getInstance().getResourceManager()
//
//        Map<String, FallbackResourceManager> domainResourceManagers =
//                ObfuscationReflectionHelper.getPrivateValue(
//                        SimpleReloadableResourceManager.class,
//                        (SimpleReloadableResourceManager) Minecraft.getInstance().getResourceManager(),
//                        "field_110548_a");
//        domainResourceManagers.get("overloaded").add(this);
//    }
//}
