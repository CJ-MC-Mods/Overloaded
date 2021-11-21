package com.cjm721.overloaded.client.resource;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

@OnlyIn(Dist.CLIENT)
public class BlockResourcePack extends AbstractInjectableResourcePack {

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
  @Nonnull
  public InputStream getRootResource(String fileName) {
    return null;
  }

  @Override
  public InputStream getResource(ResourcePackType type, ResourceLocation location)
      throws IOException {
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


  @Override
  public Collection<ResourceLocation> getResources(ResourcePackType type, String namespaceIn, String pathIn, int maxDepthIn, Predicate<String> filterIn) {
    return ImmutableList.<ResourceLocation>builder()
        .addAll(images.keySet())
        .addAll(blockStates.keySet())
        .build();
  }

  @Override
  public boolean hasResource(ResourcePackType type, ResourceLocation location) {
    return images.containsKey(location) || blockStates.containsKey(location);
  }

  @Override
  public Set<String> getNamespaces(ResourcePackType type) {
    return ImmutableSet.copyOf(domains);
  }

  @Nullable
  @Override
  public <T> T getMetadataSection(IMetadataSectionSerializer<T> deserializer) {
    return null;
  }

  @Override
  @Nonnull
  public String getName() {
    return "Overloaded Dynamic Textures";
  }

  @Override
  public void close() {}
}
