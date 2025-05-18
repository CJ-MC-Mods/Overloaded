package com.cjm721.overloaded.block.basic.hyperTransfer;

import com.cjm721.overloaded.block.basic.hyperTransfer.base.AbstractBlockHyperSender;
import com.cjm721.overloaded.tile.hyperTransfer.TileHyperItemSender;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nonnull;

public class BlockHyperItemSender extends AbstractBlockHyperSender {

  public BlockHyperItemSender() {
    super(getDefaultProperties());
  }

  @Override
  public @org.jetbrains.annotations.Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new TileHyperItemSender(pos,state);
  }

  @Nonnull
  @Override
  public String getType() {
    return "Item";
  }

  @Override
  public void registerModel() {
//    super.registerModel();
//
//    ImageUtil.registerDynamicTexture(
//        new ResourceLocation(MODID, "textures/block/hyper_item_sender.png"),
//        OverloadedConfig.INSTANCE.textureResolutions.blockResolution);
  }
}
