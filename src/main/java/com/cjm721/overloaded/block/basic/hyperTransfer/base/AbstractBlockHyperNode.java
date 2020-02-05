package com.cjm721.overloaded.block.basic.hyperTransfer.base;

import com.cjm721.overloaded.block.ModBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public abstract class AbstractBlockHyperNode extends ModBlock {
  AbstractBlockHyperNode(@Nonnull Properties materialIn) {
    super(materialIn.variableOpacity());
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @Nonnull
  protected abstract String getType();

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
    //        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);

    //        StateMapperBase ignoreState = new StateMapperBase() {
    //            @Override
    //            @Nonnull
    //            protected ModelResourceLocation getModelResourceLocation(@Nonnull BlockState
    // iBlockState) {
    //                return location;
    //            }
    //        };
    //        ModelLoader.setCustomStateMapper(this, ignoreState);
  }
}
