package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.tile.functional.TileCreativeGeneratorFE;
import com.cjm721.overloaded.tile.functional.TileEnergyExtractor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class BlockEnergyExtractor extends AbstractModBlockFacing implements EntityBlock  {

  public BlockEnergyExtractor(Properties properties) {
    super(properties);
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void registerModel() {
    super.registerModel();
//
//    ImageUtil.registerDynamicTexture(
//        new ResourceLocation(MODID, "textures/block/energy_extractor.png"),
//        OverloadedConfig.INSTANCE.textureResolutions.blockResolution);
  }

  @Override
  public @org.jetbrains.annotations.Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new TileEnergyExtractor(pos, state); // .setFacing(Direction.byIndex(meta));
  }

  @Override
  public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
    return blockEntityType == ModTiles.energyExtractor.get() ? TileEnergyExtractor::tick : null;
  }
}
