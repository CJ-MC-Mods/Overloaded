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
    private final String domain = "overloaded";

    public OverloadedCustomResourceManager() {
        this.domains = new HashSet<>();
        this.domains.add(domain);
    }

    @Override
    public void onResourceManagerReload(@Nonnull IResourceManager resourceManager) {
        // TODO: Regenerate Textures to use the newly loaded ones
    }

    @Override
    @Nonnull
    public Set<String> getResourceDomains() {
        return this.domains;
    }

    @Override
    public IResource getResource(@Nonnull ResourceLocation location) throws IOException {
        return null;
    }

    @Override
    public List<IResource> getAllResources(@Nonnull ResourceLocation location) throws IOException {
        return null;
    }

    @Override
    public void reloadResources(@Nonnull List<IResourcePack> resourcesPacksList) {

    }

    @Override
    public void registerReloadListener(IResourceManagerReloadListener reloadListener) {
        // TODO Reload base resouce pack
    }
}
