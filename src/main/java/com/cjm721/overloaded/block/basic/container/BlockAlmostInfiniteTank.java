package com.cjm721.overloaded.block.basic.container;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.storage.stacks.intint.LongFluidStack;
import com.cjm721.overloaded.tile.infinity.TileAlmostInfiniteTank;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

public class BlockAlmostInfiniteTank extends AbstractBlockHyperContainer {

  public BlockAlmostInfiniteTank(Properties properties) {
    super(properties);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
//    ModelResourceLocation location =
//        new ModelResourceLocation(new ResourceLocation(MODID, "almost_infinite_tank"), null);
//    //        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);
//
//    ResizeableTextureGenerator.addToTextureQueue(
//        new ResizeableTextureGenerator.ResizableTexture(
//            new ResourceLocation(MODID, "textures/block/almost_infinite_tank.png"),
//            new ResourceLocation(MODID, "textures/dynamic/blocks/almost_infinite_tank.png"),
//            OverloadedConfig.INSTANCE.textureResolutions.blockResolution));
  }

  @Override
  public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new TileAlmostInfiniteTank(pos,state);
  }

  @Override
  protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hitResult) {
    ItemStack heldItem = player.getItemInHand(handIn);
    if (heldItem.isEmpty() && handIn == InteractionHand.MAIN_HAND) {
      if (!world.isClientSide) {
        sendPlayerStatus(world, pos, player);
      }
      return ItemInteractionResult.SUCCESS;
    } else {
      BlockEntity te = world.getBlockEntity(pos);
      if (te instanceof TileAlmostInfiniteTank) {
        IFluidHandler opHandler = world.getCapability(Capabilities.FluidHandler.BLOCK, pos, hitResult.getDirection());
        if (opHandler == null) {
            Overloaded.logger.warn("Infinite Tank has no HyperFluid Capability? {}", pos);
        } else {
          if (!world.isClientSide) {
            return FluidUtil.interactWithFluidHandler(
                player,
                handIn,
                opHandler) ? ItemInteractionResult.SUCCESS : ItemInteractionResult.FAIL;
          }
          return ItemInteractionResult.SUCCESS;
        }
      }
    }
    return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
  }

  @Override
  protected void sendPlayerStatus(Level world, BlockPos pos, Player player) {
    LongFluidStack storedFluid =
        ((TileAlmostInfiniteTank) world.getBlockEntity(pos)).getStorage().getFluidStack();
    if (storedFluid == null || storedFluid.fluidStack == null) {
      player.displayClientMessage(Component.literal("Fluid: EMPTY"), false);
    } else {
      player.displayClientMessage(
          Component.literal("Fluid: ")
              .append(storedFluid.fluidStack.getHoverName())
              .append(String.format(" Amount: %,d", storedFluid.amount)),
          false);
    }
  }
}
