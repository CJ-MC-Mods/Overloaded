package com.cjm721.ibhstd.client.render.block.compressed;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

import java.util.HashMap;
import java.util.Map;

import static com.cjm721.ibhstd.IBHSTD.MODID;

/**
 * Created by CJ on 4/3/2017.
 */
public class CompressedModelLoader implements ICustomModelLoader {
    private static CompressedModelLoader INSTANCE;

    private Map<String, IModel> resourceMap;

    public CompressedModelLoader() {
        this.resourceMap = new HashMap<>();
        INSTANCE = this;
    }

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return modelLocation.getResourceDomain().equals(MODID) && modelLocation.getResourcePath().startsWith("compressed");
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) throws Exception {
        return resourceMap.get(modelLocation.getResourcePath());
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        //resourceMap.clear();
    }

    public static void addModel(ResourceLocation location, IBlockState state) {
        INSTANCE.resourceMap.put(location.getResourcePath(),new CompressedModel(state));
    }
}
