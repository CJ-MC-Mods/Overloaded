package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.tile.functional.TileEnergyInjectorChest;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class BlockEnergyInjectorChest extends AbstractModBlockFacing implements EntityBlock {

  public BlockEnergyInjectorChest(Properties properties) {
    super(properties);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    super.registerModel();

//    ImageUtil.registerDynamicTexture(
//        new ResourceLocation(MODID, "textures/block/energy_extractor.png"),
//        OverloadedConfig.INSTANCE.textureResolutions.blockResolution);
  }

  @Override
  public @org.jetbrains.annotations.Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new TileEnergyInjectorChest(pos,state); // .setFacing(Direction.byIndex(meta));
  }
}
