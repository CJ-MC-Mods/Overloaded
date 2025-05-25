package com.cjm721.overloaded.block.basic.hyperTransfer;

import com.cjm721.overloaded.block.basic.hyperTransfer.base.AbstractBlockHyperReceiver;
import com.cjm721.overloaded.tile.hyperTransfer.TileHyperItemReceiver;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class BlockHyperItemReceiver extends AbstractBlockHyperReceiver {

  public BlockHyperItemReceiver(Properties properties) {
    super(properties);
  }

  @Override
  public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new TileHyperItemReceiver(pos, state);
  }

  @Nonnull
  @Override
  public String getType() {
    return "Item";
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void registerModel() {
    //    super.registerModel();
    //
    //    ImageUtil.registerDynamicTexture(
    //        new ResourceLocation(MODID, "textures/block/hyper_item_receiver.png"),
    //        OverloadedConfig.INSTANCE.textureResolutions.blockResolution);
  }
}
