package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.tile.functional.TileItemInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.items.ItemHandlerHelper;

public class BlockItemInterface extends ModBlock implements EntityBlock {

  public BlockItemInterface(Properties properties) {
    super(properties.sound(SoundType.GLASS).strength(3).dynamicShape().noOcclusion());
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    //        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new
    // ModelResourceLocation(getRegistryName(), null));
//    ImageUtil.registerDynamicTexture(
//        new ResourceLocation(MODID, "textures/block/item_interface.png"),
//        OverloadedConfig.INSTANCE.textureResolutions.blockResolution);
  }

  @Override
  public @org.jetbrains.annotations.Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new TileItemInterface(pos,state);
  }

  @Override
  public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
    ((TileItemInterface) world.getBlockEntity(pos)).breakBlock();

    return super.playerWillDestroy(world, pos, state, player);
  }


  @Override
  protected ItemInteractionResult useItemOn(ItemStack handStack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
    if (world.isClientSide) return ItemInteractionResult.CONSUME;

    if (hand != InteractionHand.MAIN_HAND) return ItemInteractionResult.CONSUME;

    BlockEntity te = world.getBlockEntity(pos);

    if (!(te instanceof TileItemInterface anInterface)) {
      return ItemInteractionResult.CONSUME;
    }

    ItemStack currentStack = anInterface.getStoredItem();
    if (currentStack.isEmpty()) {
      if (handStack.isEmpty()) return ItemInteractionResult.FAIL;

      ItemStack returnedItem = anInterface.insertItem(0, handStack, false);
      player.setItemInHand(hand, returnedItem);
    } else {
      if (!player.getItemInHand(hand).isEmpty()) return ItemInteractionResult.FAIL;

      ItemStack toSpawn = anInterface.extractItem(0, 1, false);
      if (toSpawn.isEmpty()) return ItemInteractionResult.FAIL;

      ItemHandlerHelper.giveItemToPlayer(player, toSpawn, player.getInventory().selected);
    }
    return ItemInteractionResult.CONSUME;
  }

  @Override
  public boolean useShapeForLightOcclusion(BlockState state) {
    return true;
  }

}
