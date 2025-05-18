package com.cjm721.overloaded.block.basic.container;

import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.storage.stacks.intint.LongItemStack;
import com.cjm721.overloaded.tile.infinity.TileAlmostInfiniteBarrel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class BlockAlmostInfiniteBarrel extends AbstractBlockHyperContainer {

  public BlockAlmostInfiniteBarrel() {
    super(ModBlock.getDefaultProperties());
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
//    ModelResourceLocation location =
//        new ModelResourceLocation(new ResourceLocation(MODID, "almost_infinite_barrel"), null);
//    //            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
//    // location);
//
//    ResizeableTextureGenerator.addToTextureQueue(
//        new ResizeableTextureGenerator.ResizableTexture(
//            new ResourceLocation(MODID, "textures/block/almost_infinite_barrel.png"),
//            new ResourceLocation(MODID, "textures/dynamic/blocks/almost_infinite_barrel.png"),
//            OverloadedConfig.INSTANCE.textureResolutions.blockResolution));
  }

  @Override
  protected void sendPlayerStatus(Level world, BlockPos pos, Player player) {
    LongItemStack stack = ((TileAlmostInfiniteBarrel) world.getBlockEntity(pos)).getStorage().status();
    if (stack.getItemStack().isEmpty()) {
      player.displayClientMessage(Component.literal("Item: EMPTY"), false);
    } else {
      player.displayClientMessage(
              Component.literal("Item: ")
              .append(stack.getItemStack().getDisplayName())
              .append(String.format(" Amount %,d", stack.getAmount())),
          false);
    }
  }

  @Override
  public @org.jetbrains.annotations.Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new TileAlmostInfiniteBarrel(pos,state);
  }
}
