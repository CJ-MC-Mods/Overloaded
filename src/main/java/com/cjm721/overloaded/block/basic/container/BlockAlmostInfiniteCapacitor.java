package com.cjm721.overloaded.block.basic.container;

import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.tile.infinity.TileAlmostInfiniteCapacitor;
import com.cjm721.overloaded.storage.stacks.intint.LongEnergyStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class BlockAlmostInfiniteCapacitor extends AbstractBlockHyperContainer {

  public BlockAlmostInfiniteCapacitor(Properties properties) {
    super(properties);
  }

  @Override
  public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new TileAlmostInfiniteCapacitor(pos,state);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
//    ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
//    //        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);
//
//    ResizeableTextureGenerator.addToTextureQueue(
//        new ResizeableTextureGenerator.ResizableTexture(
//            new ResourceLocation(MODID, "textures/block/almost_infinite_capacitor.png"),
//            new ResourceLocation(MODID, "textures/dynamic/blocks/almost_infinite_capacitor.png"),
//            OverloadedConfig.INSTANCE.textureResolutions.blockResolution));
  }

  @Override
  protected void sendPlayerStatus(Level world, BlockPos pos, Player player) {
    LongEnergyStack stack =
        ((TileAlmostInfiniteCapacitor) world.getBlockEntity(pos)).getStorage().status();

    double percent = 100 * (double) stack.getAmount() / (double) Long.MAX_VALUE;
    player.displayClientMessage(Component.literal(
            String.format("Energy Amount: %,d  %,.4f%%", stack.getAmount(), percent)),
        false);
  }
}
