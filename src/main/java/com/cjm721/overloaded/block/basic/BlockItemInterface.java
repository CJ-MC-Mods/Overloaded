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
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
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
    ImageUtil.registerDynamicTexture(
        new ResourceLocation(MODID, "textures/block/item_interface.png"),
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
  public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
    ((TileItemInterface)world.getTileEntity(pos)).breakBlock();

    super.onBlockHarvested(world,pos,state,player);
  }

  @Override
  public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
    if (world.isRemote) return false;

    if (hand != Hand.MAIN_HAND) return false;

    TileEntity te = world.getTileEntity(pos);

    if (!(te instanceof TileItemInterface)) return false;

    TileItemInterface anInterface = (TileItemInterface) te;

    ItemStack stack = anInterface.getStoredItem();
    if (stack.isEmpty()) {
      ItemStack handStack = player.getHeldItem(hand);

      if (handStack.isEmpty()) return false;

      ItemStack returnedItem = anInterface.insertItem(0, handStack, false);
      player.setHeldItem(hand, returnedItem);
    } else {
      if (!player.getHeldItem(hand).isEmpty()) return false;

      ItemStack toSpawn = anInterface.extractItem(0, 1, false);
      if (toSpawn.isEmpty()) return false;

      ItemHandlerHelper.giveItemToPlayer(player, toSpawn, player.inventory.currentItem);
    }
    return true;
  }
}
