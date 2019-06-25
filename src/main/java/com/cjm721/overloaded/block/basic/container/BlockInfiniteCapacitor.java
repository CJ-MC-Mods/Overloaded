package com.cjm721.overloaded.block.basic.container;

import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.tile.infinity.TileInfiniteCapacitor;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.storage.IHyperHandler;
import com.cjm721.overloaded.storage.LongEnergyStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;
import static com.cjm721.overloaded.util.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;

public class BlockInfiniteCapacitor extends AbstractBlockInfiniteContainer {

  public BlockInfiniteCapacitor() {
    super(ModBlock.getDefaultProperties());
    setRegistryName("infinite_capacitor");
  }

  @Override
  @Nonnull
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TileInfiniteCapacitor();
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
    //        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);

    ResizeableTextureGenerator.addToTextureQueue(
        new ResizeableTextureGenerator.ResizableTexture(
            new ResourceLocation(MODID, "textures/blocks/infinite_capacitor.png"),
            new ResourceLocation(MODID, "textures/dynamic/blocks/infinite_capacitor.png"),
            OverloadedConfig.INSTANCE.textureResolutions.blockResolution));
  }

  @Override
  protected void sendPlayerStatus(World world, BlockPos pos, PlayerEntity player) {
    LongEnergyStack stack =
        ((TileInfiniteCapacitor) world.getTileEntity(pos)).getStorage().status();

    double percent = (double) stack.getAmount() / (double) Long.MAX_VALUE;
    player.sendStatusMessage(
        new StringTextComponent(
            String.format("Energy Amount: %,d  %,.4f%%", stack.getAmount(), percent)),
        false);
  }

  @Nonnull
  @Override
  <T extends IHyperHandler> Capability<T> getHyperCapabilityType() {
    return (Capability<T>) HYPER_ENERGY_HANDLER;
  }
}
