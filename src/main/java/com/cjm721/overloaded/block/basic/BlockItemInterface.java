package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.tile.functional.TileItemInterface;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;

import net.minecraft.block.AbstractBlock.Properties;

public class BlockItemInterface extends ModBlock {

  public BlockItemInterface() {
    super(Properties.of(Material.GLASS).strength(3).dynamicShape().noOcclusion());
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

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TileItemInterface();
  }

  @Override
  public void playerWillDestroy(World world,@Nonnull BlockPos pos, BlockState state, PlayerEntity player) {
    ((TileItemInterface) world.getBlockEntity(pos)).breakBlock();

    super.playerWillDestroy(world, pos, state, player);
  }

  @Override
  @Nonnull
  public ActionResultType use(
      BlockState state,
      World world,
      BlockPos pos,
      PlayerEntity player,
      Hand hand,
      BlockRayTraceResult rayTraceResult) {
    if (world.isClientSide) return ActionResultType.CONSUME;

    if (hand != Hand.MAIN_HAND) return ActionResultType.CONSUME;

    TileEntity te = world.getBlockEntity(pos);

    if (!(te instanceof TileItemInterface)) return ActionResultType.CONSUME;

    TileItemInterface anInterface = (TileItemInterface) te;

    ItemStack stack = anInterface.getStoredItem();
    if (stack.isEmpty()) {
      ItemStack handStack = player.getItemInHand(hand);

      if (handStack.isEmpty()) return ActionResultType.FAIL;

      ItemStack returnedItem = anInterface.insertItem(0, handStack, false);
      player.setItemInHand(hand, returnedItem);
    } else {
      if (!player.getItemInHand(hand).isEmpty()) return ActionResultType.FAIL;

      ItemStack toSpawn = anInterface.extractItem(0, 1, false);
      if (toSpawn.isEmpty()) return ActionResultType.FAIL;

      ItemHandlerHelper.giveItemToPlayer(player, toSpawn, player.inventory.selected);
    }
    return ActionResultType.CONSUME;
  }

  @Override
  public boolean useShapeForLightOcclusion(BlockState state) {
    return true;
  }

}
