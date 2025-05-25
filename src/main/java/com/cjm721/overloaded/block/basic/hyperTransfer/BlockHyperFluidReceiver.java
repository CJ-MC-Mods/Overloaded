package com.cjm721.overloaded.block.basic.hyperTransfer;

import com.cjm721.overloaded.block.basic.hyperTransfer.base.AbstractBlockHyperReceiver;
import com.cjm721.overloaded.tile.hyperTransfer.TileHyperFluidReceiver;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class BlockHyperFluidReceiver extends AbstractBlockHyperReceiver {

  public BlockHyperFluidReceiver(Properties properties) {
    super(properties);
  }

  @Override
  @Nonnull
  public String getType() {
    return "Fluid";
  }

  @Override
  public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new TileHyperFluidReceiver(pos, state);
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void registerModel() {
    //    super.registerModel();
    //
    //    ImageUtil.registerDynamicTexture(
    //        new ResourceLocation(MODID, "textures/block/hyper_fluid_receiver.png"),
    //        OverloadedConfig.INSTANCE.textureResolutions.blockResolution);
  }
}
