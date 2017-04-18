package com.cjm721.overloaded.client.resource;

import net.minecraft.client.resources.*;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OverloadedCustomResourceManager implements IReloadableResourceManager, IResourceManagerReloadListener {

    private final HashSet<String> domains;

    public OverloadedCustomResourceManager() {
        this.domains = new HashSet<>();
        this.domains.add("overloaded");
    }


    @Override
    @Nonnull
    public Set<String> getResourceDomains() {
        return domains;
    }

    @Override
    @Nonnull
    public IResource getResource(@Nonnull ResourceLocation location) throws IOException {
        return null;
    }

    @Override
    @Nonnull
    public List<IResource> getAllResources(@Nonnull ResourceLocation location) throws IOException {
        return null;
    }

    @Override
    public void reloadResources(List<IResourcePack> resourcesPacksList) {

    }

    @Override
    public void registerReloadListener(IResourceManagerReloadListener reloadListener) {

    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }
}
