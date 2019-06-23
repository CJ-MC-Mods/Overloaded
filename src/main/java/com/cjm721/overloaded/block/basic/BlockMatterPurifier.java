package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.block.ModBlockTile;
import com.cjm721.overloaded.block.tile.TileMatterPurifier;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class BlockMatterPurifier extends ModBlockTile {

  public BlockMatterPurifier() {
    super(getDefaultProperties());
  }

  @Override
  public void baseInit() {
    setRegistryName("matter_purifier");
    //        setTranslationKey("matter_purifier");

    //        GameRegistry.registerTileEntity(TileMatterPurifier.class, MODID + ":matter_purifier");
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), "inventory");
    //        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TileMatterPurifier();
  }
}
