package com.cjm721.overloaded.block.basic.hyperTransfer;

import com.cjm721.overloaded.block.basic.hyperTransfer.base.AbstractBlockHyperReceiver;
import com.cjm721.overloaded.block.tile.hyperTransfer.TileHyperEnergyReceiver;
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

public class BlockHyperEnergyReceiver extends AbstractBlockHyperReceiver {

  public BlockHyperEnergyReceiver() {
    super(getDefaultProperties());
  }

  @Override
  public void baseInit() {
    setRegistryName("hyper_energy_receiver");
    //        setTranslationKey("hyper_energy_receiver");

    //        GameRegistry.registerTileEntity(TileHyperEnergyReceiver.class, MODID +
    // ":hyper_energy_receiver");
  }

  @Override
  @Nonnull
  protected String getType() {
    return "Energy";
  }

  @Override
  @Nonnull
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TileHyperEnergyReceiver();
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void registerModel() {
    super.registerModel();

    ImageUtil.registerDynamicTexture(
        new ResourceLocation(MODID, "textures/blocks/hyper_energy_receiver.png"),
        OverloadedConfig.INSTANCE.textureResolutions.blockResolution);
  }
}
