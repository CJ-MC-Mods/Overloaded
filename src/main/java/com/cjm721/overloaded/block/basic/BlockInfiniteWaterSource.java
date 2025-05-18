package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.tile.functional.TileInfiniteWaterSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class BlockInfiniteWaterSource extends ModBlock implements EntityBlock {

  public BlockInfiniteWaterSource() {
    super(getDefaultProperties().noOcclusion());
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
//    ModelResourceLocation location =
//        new ModelResourceLocation(new ResourceLocation(MODID, "infinite_water_source"), null);
//    //        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);
//
//    ImageUtil.registerDynamicTexture(
//        new ResourceLocation(MODID, "textures/block/infinite_water_source.png"),
//        OverloadedConfig.INSTANCE.textureResolutions.blockResolution);
  }

  @Override
  @Nonnull
  public InteractionResult useItemOn(
          ItemStack heldItem,
      BlockState state,
      Level world,
      BlockPos pos,
          Player player,
          InteractionHand handIn,
          BlockHitResult hit) {
    if (!heldItem.isEmpty()) {
      BlockEntity te = world.getBlockEntity(pos);
      if (te instanceof TileInfiniteWaterSource) {
        IFluidHandler opHandler = world.getCapability(Capabilities.FluidHandler.BLOCK,pos, hit.getDirection());
        if (opHandler == null) {
            Overloaded.logger.warn("Infinite Tank has no HyperFluid Capability? {}", pos);
        } else {
          if (!world.isClientSide) {
            return FluidUtil.interactWithFluidHandler(
                    player,
                    handIn,
                    opHandler)
                ? InteractionResult.CONSUME
                : InteractionResult.FAIL;
          }
          return InteractionResult.CONSUME;
        }
      }
    }
    return InteractionResult.PASS;
  }

  @Override
  public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new TileInfiniteWaterSource(pos, state);
  }
}
