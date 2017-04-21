package com.cjm721.overloaded.client.resource;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.typesafe.config.ConfigException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.*;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

public enum CompressedResourcePack implements IResourcePack, IResourceManagerReloadListener {
    INSTANCE;


    @Override
    public void onResourceManagerReload(@Nonnull IResourceManager resourceManager) {
        inject();
    }

    private Map<ResourceLocation, BufferedImage> images = Maps.newHashMap();
    private Map<ResourceLocation, String> blockStates = Maps.newHashMap();

    private Set<String> domains = Sets.newHashSet();

    public void addImage(@Nonnull ResourceLocation res,@Nonnull BufferedImage image) {
        images.put(res, image);
        try {
            File file = new File("C:\\Users\\CJ\\Desktop\\Temp\\",res.toString().replace(":","/"));
            file.mkdirs();
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addBlockState(ResourceLocation res, String state) {
        blockStates.put(res,state);

        File file = new File("C:\\Users\\CJ\\Desktop\\Temp\\",res.toString().replace(":","/"));
        file.getParentFile().mkdirs();
        try {
            PrintWriter temp = new PrintWriter(file);
            temp.println(state);
            temp.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addDomain(String domain) {
        domains.add(domain);
    }

    public void inject() {
//        List<IResourcePack> defaultResourcePacks = ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "field_110449_ao",  "defaultResourcePacks");
//        defaultResourcePacks.add(this);

        Map<String, FallbackResourceManager> domainResourceManagers = ReflectionHelper.getPrivateValue(SimpleReloadableResourceManager.class, (SimpleReloadableResourceManager)Minecraft.getMinecraft().getResourceManager(), "","domainResourceManagers");
        domainResourceManagers.get("overloaded").addResourcePack(this);
    }

    @Override
    @Nonnull
    public InputStream getInputStream(@Nonnull ResourceLocation location) throws IOException {
        if(location.getResourcePath().endsWith(".png")) {
            return getImageInputStream(location);
        } else {
            String state = blockStates.get(location);

            if(state != null) {
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
    public <T extends IMetadataSection> T getPackMetadata(@Nonnull MetadataSerializer metadataSerializer,@Nonnull String metadataSectionName) throws IOException {
        return null;
    }

    @Override
    @Nonnull
    public BufferedImage getPackImage() throws IOException {
        return null;
    }

    @Override
    public String getPackName() {
        return "Overloaded Compressed Textures";
    }
}
