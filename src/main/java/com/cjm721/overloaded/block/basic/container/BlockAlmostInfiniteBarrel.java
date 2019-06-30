package com.cjm721.overloaded.block.basic.container;

import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.storage.stacks.intint.LongItemStack;
import com.cjm721.overloaded.tile.infinity.TileAlmostInfiniteBarrel;
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

import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockAlmostInfiniteBarrel extends AbstractBlockHyperContainer {

  public BlockAlmostInfiniteBarrel() {
    super(ModBlock.getDefaultProperties());
    setRegistryName("almost_infinite_barrel");
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    ModelResourceLocation location =
        new ModelResourceLocation(new ResourceLocation(MODID, "almost_infinite_barrel"), null);
    //            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
    // location);

    ResizeableTextureGenerator.addToTextureQueue(
        new ResizeableTextureGenerator.ResizableTexture(
            new ResourceLocation(MODID, "textures/block/infinite_barrel.png"),
            new ResourceLocation(MODID, "textures/dynamic/blocks/infinite_barrel.png"),
            OverloadedConfig.INSTANCE.textureResolutions.blockResolution));
  }

  @Override
  protected void sendPlayerStatus(World world, BlockPos pos, PlayerEntity player) {
    LongItemStack stack = ((TileAlmostInfiniteBarrel) world.getTileEntity(pos)).getStorage().status();
    if (stack.getItemStack().isEmpty()) {
      player.sendStatusMessage(new StringTextComponent("Item: EMPTY"), false);
    } else {
      player.sendStatusMessage(
          new StringTextComponent("Item: ")
              .appendSibling(stack.getItemStack().getTextComponent())
              .appendText(String.format(" Amount %,d", stack.getAmount())),
          false);
    }
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TileAlmostInfiniteBarrel();
  }
}
