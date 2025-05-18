package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.block.ModBlockTile;
import com.cjm721.overloaded.tile.functional.TileCreativeGeneratorFE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class BlockCreativeGenerator extends ModBlockTile {

  public BlockCreativeGenerator() {
    super(getDefaultProperties().noOcclusion());
  }

  @Override
  public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new TileCreativeGeneratorFE();
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
}
