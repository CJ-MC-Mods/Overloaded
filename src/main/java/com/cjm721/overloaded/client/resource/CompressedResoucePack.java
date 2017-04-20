package com.cjm721.overloaded.client.resource;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by CJ on 4/19/2017.
 */
public enum CompressedResoucePack implements IResourcePack {
    INSTANCE;

    private Map<ResourceLocation, BufferedImage> images = Maps.newHashMap();
    private Set<String> domains = Sets.newHashSet();

    public void addImage(ResourceLocation res, BufferedImage image) {
        images.put(res, image);
    }

    public void addDomain(String domain) {
        domains.add(domain);
    }

    public void inject() {
        List<IResourcePack> defaultResourcePacks = ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "field_110449_ao",  "defaultResourcePacks");
        defaultResourcePacks.add(this);
    }

    @Override
    @Nonnull
    public InputStream getInputStream(@Nonnull ResourceLocation location) throws IOException {
        BufferedImage image = images.get(location);
        if (image != null) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);
            return new ByteArrayInputStream(os.toByteArray());
        }
        return null;
    }

    @Override
    public boolean resourceExists(@Nonnull ResourceLocation location) {
        return images.containsKey(location);
    }

    @Override
    @Nonnull
    public Set<String> getResourceDomains() {
        return ImmutableSet.copyOf(domains);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IMetadataSection> T getPackMetadata(@Nonnull MetadataSerializer metadataSerializer,@Nonnull String metadataSectionName) throws IOException {
        return null;
    }

    @Override
    public BufferedImage getPackImage() throws IOException {
        return null;
    }

    @Override
    public String getPackName() {
        return "Overloaded Compressed Textures";
    }
}
