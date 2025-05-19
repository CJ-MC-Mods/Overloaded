package com.cjm721.overloaded.block.basic.hyperTransfer;

import com.cjm721.overloaded.block.basic.hyperTransfer.base.AbstractBlockHyperSender;
import com.cjm721.overloaded.tile.hyperTransfer.TileHyperEnergySender;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class BlockHyperEnergySender extends AbstractBlockHyperSender {

  public BlockHyperEnergySender(Properties properties) {
    super(properties);
  }

  @Override
  @Nonnull
  public String getType() {
    return "Energy";
  }

  @Override
  public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new TileHyperEnergySender(pos,state);
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void registerModel() {
//    super.registerModel();
//    ImageUtil.registerDynamicTexture(
//        new ResourceLocation(MODID, "textures/block/hyper_energy_sender.png"),
//        OverloadedConfig.INSTANCE.textureResolutions.blockResolution);
  }
}
