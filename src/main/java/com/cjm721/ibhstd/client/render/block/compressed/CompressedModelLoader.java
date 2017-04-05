package com.cjm721.ibhstd.client.render.block.compressed;

import com.cjm721.ibhstd.common.ModStart;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

import java.util.HashMap;
import java.util.Map;

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
        return modelLocation.getResourceDomain().equals(ModStart.MODID) && modelLocation.getResourcePath().startsWith("compressed");
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
