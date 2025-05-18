package com.cjm721.overloaded.block.basic.hyperTransfer;

import com.cjm721.overloaded.block.basic.hyperTransfer.base.AbstractBlockHyperReceiver;
import com.cjm721.overloaded.tile.hyperTransfer.TileHyperEnergyReceiver;
import com.cjm721.overloaded.tile.hyperTransfer.TileHyperItemReceiver;
import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.config.OverloadedConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.IBlockReader;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockHyperItemReceiver extends AbstractBlockHyperReceiver {

  public BlockHyperItemReceiver() {
    super(getDefaultProperties());
  }

  @Override
  public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new TileHyperEnergyReceiver(pos,state);
  }

  @Nonnull
  @Override
  protected String getType() {
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
