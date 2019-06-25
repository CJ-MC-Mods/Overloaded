package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.tile.functional.TileEnergyExtractor;
import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.config.OverloadedConfig;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockEnergyExtractor extends AbstractModBlockFacing {

  public BlockEnergyExtractor() {
    super(getDefaultProperties());
    setRegistryName("energy_extractor");
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TileEnergyExtractor(); // .setFacing(Direction.byIndex(meta));
  }

  @OnlyIn(Dist.CLIENT)
  @Nonnull
  @Override
  public BlockRenderLayer getRenderLayer() {
    return BlockRenderLayer.CUTOUT;
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void registerModel() {
    super.registerModel();

    ImageUtil.registerDynamicTexture(
        new ResourceLocation(MODID, "textures/blocks/energy_extractor.png"),
        OverloadedConfig.INSTANCE.textureResolutions.blockResolution);
  }
}
