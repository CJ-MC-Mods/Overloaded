package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.block.ModBlockTile;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.tile.functional.TileCreativeGeneratorFE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockCreativeGenerator extends ModBlockTile {

  public BlockCreativeGenerator(Properties properties) {
    super(properties.noOcclusion());
  }

  @Override
  public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new TileCreativeGeneratorFE(pos,state);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
//    ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), "inventory");
//    //        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);
//
//    ImageUtil.registerDynamicTexture(
//        new ResourceLocation(MODID, "textures/block/creative_generator.png"),
//        OverloadedConfig.INSTANCE.textureResolutions.blockResolution);
  }

  @Override
  public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
    return blockEntityType == ModTiles.creativeGeneratorFE.get() ? TileCreativeGeneratorFE::tick : null;
  }
}
