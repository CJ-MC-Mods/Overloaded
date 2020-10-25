package com.cjm721.overloaded.block.basic.container;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.storage.stacks.intint.LongFluidStack;
import com.cjm721.overloaded.tile.infinity.TileAlmostInfiniteTank;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;
import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

public class BlockAlmostInfiniteTank extends AbstractBlockHyperContainer {

  public BlockAlmostInfiniteTank() {
    super(getDefaultProperties());
    setRegistryName("almost_infinite_tank");
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    ModelResourceLocation location =
        new ModelResourceLocation(new ResourceLocation(MODID, "almost_infinite_tank"), null);
    //        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);

    ResizeableTextureGenerator.addToTextureQueue(
        new ResizeableTextureGenerator.ResizableTexture(
            new ResourceLocation(MODID, "textures/block/almost_infinite_tank.png"),
            new ResourceLocation(MODID, "textures/dynamic/blocks/almost_infinite_tank.png"),
            OverloadedConfig.INSTANCE.textureResolutions.blockResolution));
  }

  @Override
  @Nonnull
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TileAlmostInfiniteTank();
  }

  @Override
  @Nonnull
  public ActionResultType onBlockActivated(
      BlockState state,
      World world,
      BlockPos pos,
      PlayerEntity player,
      Hand handIn,
      BlockRayTraceResult hit) {
    ItemStack heldItem = player.getHeldItem(handIn);
    if (heldItem.isEmpty() && handIn == Hand.MAIN_HAND) {
      if (!world.isRemote) {
        sendPlayerStatus(world, pos, player);
      }
      return ActionResultType.SUCCESS;
    } else {
      TileEntity te = world.getTileEntity(pos);
      if (te instanceof TileAlmostInfiniteTank) {
        LazyOptional<IFluidHandler> opHandler = te.getCapability(FLUID_HANDLER_CAPABILITY);
        if (!opHandler.isPresent()) {
          Overloaded.logger.warn("Infinite Tank has no HyperFluid Capability? " + pos);
        } else {
          if (!world.isRemote) {
            return FluidUtil.interactWithFluidHandler(
                player,
                handIn,
                opHandler.orElseThrow(() -> new RuntimeException("Impossible Condition"))) ? ActionResultType.SUCCESS : ActionResultType.FAIL;
          }
          return ActionResultType.SUCCESS;
        }
      }
    }
    return ActionResultType.PASS;
  }

  @Override
  protected void sendPlayerStatus(World world, BlockPos pos, PlayerEntity player) {
    LongFluidStack storedFluid =
        ((TileAlmostInfiniteTank) world.getTileEntity(pos)).getStorage().getFluidStack();
    if (storedFluid == null || storedFluid.fluidStack == null) {
      player.sendStatusMessage(new StringTextComponent("Fluid: EMPTY"), false);
    } else {
      player.sendStatusMessage(
          new StringTextComponent("Fluid: ")
              .append(storedFluid.fluidStack.getDisplayName())
              .appendString(String.format(" Amount: %,d", storedFluid.amount)),
          false);
    }
  }
}
