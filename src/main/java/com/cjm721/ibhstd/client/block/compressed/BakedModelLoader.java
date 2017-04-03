package com.cjm721.ibhstd.client.block.compressed;

import com.cjm721.ibhstd.ModStart;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

/**
 * Created by CJ on 4/3/2017.
 */
public class BakedModelLoader implements ICustomModelLoader {
    public static final CompressedModel EXAMPLE_MODEL = new CompressedModel();


    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return modelLocation.getResourceDomain().equals(ModStart.MODID) && "bakedmodelblock".equals(modelLocation.getResourcePath());
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) throws Exception {
        return EXAMPLE_MODEL;
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }
}
