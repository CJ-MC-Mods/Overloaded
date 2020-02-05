package com.cjm721.overloaded.block.reactor;

import com.cjm721.overloaded.block.ModBlockTile;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.config.OverloadedConfig;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockFusionCore extends ModBlockTile {

  public BlockFusionCore() {
    super(getDefaultProperties());
    setRegistryName("fusion_core");
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    //        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new
    // ModelResourceLocation(getRegistryName(), null));
//    ClientRegistry.bindTileEntityRenderer(ModTiles.fusionCore, new FusionCoreRenderer());

    ResizeableTextureGenerator.addToTextureQueue(
        new ResizeableTextureGenerator.ResizableTexture(
            new ResourceLocation(MODID, "textures/block/sun/yellow.png"),
            new ResourceLocation(MODID, "textures/dynamic/blocks/sun/yellow.png"),
            OverloadedConfig.INSTANCE.textureResolutions.blockResolution));
    ResizeableTextureGenerator.addToTextureQueue(
        new ResizeableTextureGenerator.ResizableTexture(
            new ResourceLocation(MODID, "textures/block/sun/red_two.png"),
            new ResourceLocation(MODID, "textures/dynamic/blocks/sun/red_two.png"),
            OverloadedConfig.INSTANCE.textureResolutions.blockResolution));
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TileFusionCore();
  }
}
