package com.cjm721.overloaded.client.resource;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.*;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OverloadedCustomResourceManager implements IResourcePack, IResourceManagerReloadListener {

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
    public InputStream getInputStream(@Nonnull ResourceLocation location) throws IOException {
        return null;
    }

    @Override
    public boolean resourceExists(@Nonnull ResourceLocation location) {
        if(!domain.equals(location.getResourceDomain())) {
            return false;
        }
        return false;
    }

    @Override
    @Nonnull
    public Set<String> getResourceDomains() {
        return this.domains;
    }

    @Nullable
    @Override
    public <T extends IMetadataSection> T getPackMetadata(@Nonnull MetadataSerializer metadataSerializer,@Nonnull  String metadataSectionName) throws IOException {
        return null;
    }

    @Override
    @Nonnull
    public BufferedImage getPackImage() throws IOException {
        return null;
    }

    @Override
    @Nonnull
    public String getPackName() {
        return "Overloaded Compressed Blocks";
    }

    public void generateCompressedTexture(ResourceLocation baseTexture, ResourceLocation toPut) {
        //Minecraft.getMinecraft().getResourceManager().getResource(baseTexture);
    }
}
