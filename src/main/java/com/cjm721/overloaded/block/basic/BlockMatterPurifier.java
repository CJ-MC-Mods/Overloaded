package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.block.ModBlockTile;
import com.cjm721.overloaded.tile.functional.TileMatterPurifier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class BlockMatterPurifier extends ModBlockTile {

  public BlockMatterPurifier(Properties properties) {
    super(properties);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
//    ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), "inventory");
    //        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);
  }

  @Override
  public @org.jetbrains.annotations.Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new TileMatterPurifier(pos,state);
  }
}
