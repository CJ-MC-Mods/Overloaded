package com.cjm721.overloaded.block.basic.container;

import com.cjm721.overloaded.tile.infinity.TileInfiniteTank;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.storage.IHyperHandler;
import com.cjm721.overloaded.storage.LongFluidStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;
import static com.cjm721.overloaded.util.CapabilityHyperFluid.HYPER_FLUID_HANDLER;
import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

public class BlockInfiniteTank extends AbstractBlockInfiniteContainer {

  public BlockInfiniteTank() {
    super(getDefaultProperties());
    setRegistryName("infinite_tank");
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    ModelResourceLocation location =
        new ModelResourceLocation(new ResourceLocation(MODID, "infinite_tank"), null);
    //        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);

    ResizeableTextureGenerator.addToTextureQueue(
        new ResizeableTextureGenerator.ResizableTexture(
            new ResourceLocation(MODID, "textures/blocks/infinite_tank.png"),
            new ResourceLocation(MODID, "textures/dynamic/blocks/infinite_tank.png"),
            OverloadedConfig.INSTANCE.textureResolutions.blockResolution));
  }

  @Override
  @Nonnull
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TileInfiniteTank();
  }

  @Override
  public void onBlockClicked(BlockState state, World world, BlockPos pos, PlayerEntity player) {
    if (!world.isRemote) {
      ItemStack heldItem = player.getActiveItemStack();
      if (heldItem.isEmpty() && player.getActiveHand() == Hand.MAIN_HAND) {
        sendPlayerStatus(world, pos, player);
        return;
      } else {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileInfiniteTank) {
          IFluidHandler handler = te.getCapability(FLUID_HANDLER_CAPABILITY).orElse(null);
          FluidUtil.interactWithFluidHandler(player, player.getActiveHand(), handler);
        }
      }
    }
    super.onBlockClicked(state, world, pos, player);
  }

  @Override
  protected void sendPlayerStatus(World world, BlockPos pos, PlayerEntity player) {
    LongFluidStack storedFluid =
        ((TileInfiniteTank) world.getTileEntity(pos)).getStorage().getFluidStack();
    if (storedFluid == null || storedFluid.fluidStack == null) {
      player.sendStatusMessage(new StringTextComponent("Fluid: EMPTY"), false);
    } else {
      player.sendStatusMessage(
          new StringTextComponent(
              String.format(
                  "Fluid: %s Amount %,d",
                  storedFluid.fluidStack.getLocalizedName(), storedFluid.amount)),
          false);
    }
  }

  @Nonnull
  @Override
  <T extends IHyperHandler> Capability<T> getHyperCapabilityType() {
    return (Capability<T>) HYPER_FLUID_HANDLER;
  }
}
