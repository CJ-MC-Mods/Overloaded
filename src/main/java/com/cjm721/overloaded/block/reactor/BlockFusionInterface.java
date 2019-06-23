package com.cjm721.overloaded.block.reactor;

import com.cjm721.overloaded.block.ModBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class BlockFusionInterface extends ModBlock {

  public BlockFusionInterface() {
    super(getDefaultProperties());
  }

  @Override
  public void baseInit() {
    setRegistryName("fusion_interface");
    //        setTranslationKey("fusion_interface");

    //        GameRegistry.registerTileEntity(TileFusionCore.class, MODID + ":fusion_interface");
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
    //        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TileFusionInterface();
  }
}
