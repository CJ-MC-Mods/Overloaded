package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.tile.functional.TileItemInterface;
import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.client.render.tile.ItemInterfaceRenderer;
import com.cjm721.overloaded.config.OverloadedConfig;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockItemInterface extends ModBlock {

  public BlockItemInterface() {
    super(getDefaultProperties());
    setRegistryName("item_interface");
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    //        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new
    // ModelResourceLocation(getRegistryName(), null));
    ClientRegistry.bindTileEntitySpecialRenderer(
        TileItemInterface.class, new ItemInterfaceRenderer());

    ImageUtil.registerDynamicTexture(
        new ResourceLocation(MODID, "textures/blocks/block.png"),
        OverloadedConfig.INSTANCE.textureResolutions.blockResolution);
  }

  //  @Override
  //  public void breakBlock(World worldIn, BlockPos pos, BlockState state) {
  //    ((TileItemInterface) worldIn.getTileEntity(pos)).breakBlock();
  //    super.breakBlock(worldIn, pos, state);
  //  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TileItemInterface();
  }

  @OnlyIn(Dist.CLIENT)
  @Nonnull
  @Override
  public BlockRenderLayer getRenderLayer() {
    return BlockRenderLayer.TRANSLUCENT;
  }

  @Override
  public void onBlockClicked(BlockState state, World world, BlockPos pos, PlayerEntity player) {
    if (world.isRemote) return;

    TileEntity te = world.getTileEntity(pos);

    if (!(te instanceof TileItemInterface)) return;

    TileItemInterface anInterface = (TileItemInterface) te;

    ItemStack stack = anInterface.getStoredItem();
    if (stack.isEmpty()) {
      ItemStack handStack = player.getActiveItemStack();

      if (handStack.isEmpty()) return;

      ItemStack returnedItem = anInterface.insertItem(0, handStack, false);
      player.setHeldItem(player.getActiveHand(), returnedItem);
    } else {
      if (!player.getActiveItemStack().isEmpty()) return;

      ItemStack toSpawn = anInterface.extractItem(0, 1, false);
      if (toSpawn.isEmpty()) return;

      ItemHandlerHelper.giveItemToPlayer(player, toSpawn, player.inventory.currentItem);
    }
  }
}
