package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.tile.functional.TileEnergyInjectorChest;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockEnergyInjectorChest extends AbstractModBlockFacing {

  public BlockEnergyInjectorChest() {
    super(getDefaultProperties());
    setRegistryName("energy_injector_chest");
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    super.registerModel();

    ImageUtil.registerDynamicTexture(
        new ResourceLocation(MODID, "textures/block/energy_extractor.png"),
        OverloadedConfig.INSTANCE.textureResolutions.blockResolution);
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TileEnergyInjectorChest(); // .setFacing(Direction.byIndex(meta));
  }
}
