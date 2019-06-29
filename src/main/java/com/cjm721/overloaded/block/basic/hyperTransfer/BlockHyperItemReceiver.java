package com.cjm721.overloaded.block.basic.hyperTransfer;

import com.cjm721.overloaded.block.basic.hyperTransfer.base.AbstractBlockHyperReceiver;
import com.cjm721.overloaded.tile.hyperTransfer.TileHyperItemReceiver;
import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.config.OverloadedConfig;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockHyperItemReceiver extends AbstractBlockHyperReceiver {

  public BlockHyperItemReceiver() {
    super(getDefaultProperties());
    setRegistryName("hyper_item_receiver");
  }

  @Override
  @Nonnull
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TileHyperItemReceiver();
  }

  @Nonnull
  @Override
  protected String getType() {
    return "Item";
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void registerModel() {
    super.registerModel();

    ImageUtil.registerDynamicTexture(
        new ResourceLocation(MODID, "textures/block/hyper_item_receiver.png"),
        OverloadedConfig.INSTANCE.textureResolutions.blockResolution);
  }
}
