package com.cjm721.overloaded.client.resource;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Map;
import java.util.Set;

@SideOnly(Side.CLIENT)
public class BlockResourcePack extends AbstractInjectableResoucePack {

    public static final BlockResourcePack INSTANCE = new BlockResourcePack();

    private BlockResourcePack() {}

    private final Map<ResourceLocation, BufferedImage> images = Maps.newHashMap();
    private final Map<ResourceLocation, String> blockStates = Maps.newHashMap();

    private final Set<String> domains = Sets.newHashSet();

    public void addImage(@Nonnull ResourceLocation res, @Nonnull BufferedImage image) {
        images.put(res, image);
    }

    public void addBlockState(ResourceLocation res, String state) {
        blockStates.put(res, state);
    }

    public void addDomain(String domain) {
        domains.add(domain);
    }

    @Override
    @Nonnull
    public InputStream getInputStream(@Nonnull ResourceLocation location) throws IOException {
        if (location.getPath().endsWith(".png")) {
            return getImageInputStream(location);
        } else {
            String state = blockStates.get(location);

            if (state != null) {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                os.write(state.getBytes());
                return new ByteArrayInputStream(os.toByteArray());
            }
        }
        throw new FileNotFoundException(location.toString());
    }

    private InputStream getImageInputStream(@Nonnull ResourceLocation location) throws IOException {
        BufferedImage image = images.get(location);
        if (image != null) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);
            return new ByteArrayInputStream(os.toByteArray());
        }
        throw new FileNotFoundException(location.toString());
    }


    @Override
    public boolean resourceExists(@Nonnull ResourceLocation location) {
        return images.containsKey(location) || blockStates.containsKey(location);
    }

    @Override
    @Nonnull
    public Set<String> getResourceDomains() {
        return ImmutableSet.copyOf(domains);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IMetadataSection> T getPackMetadata(@Nonnull MetadataSerializer metadataSerializer, @Nonnull String metadataSectionName) {
        return null;
    }

    @Override
    @Nonnull
    public BufferedImage getPackImage() {
        return null;
    }

    @Override
    @Nonnull
    public String getPackName() {
        return "Overloaded Compressed Textures";
    }
}
