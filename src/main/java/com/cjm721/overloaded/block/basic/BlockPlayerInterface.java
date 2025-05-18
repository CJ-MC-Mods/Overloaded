package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.block.ModBlockTile;
import com.cjm721.overloaded.tile.functional.TilePlayerInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.UsernameCache;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class BlockPlayerInterface extends ModBlockTile {

  public BlockPlayerInterface() {
    super(Properties.ofFullCopy(Blocks.GLASS).strength(3).dynamicShape().noOcclusion());
  }

  @Override
  public @org.jetbrains.annotations.Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new TilePlayerInterface(pos,state);
  }

  @Override
  public void setPlacedBy(
          Level world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nullable LivingEntity entity, @Nonnull ItemStack stack) {
    ((TilePlayerInterface) world.getBlockEntity(pos)).setPlacer(entity);

    super.setPlacedBy(world, pos, state, entity, stack);
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void registerModel() {
    //        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new
    // ModelResourceLocation(getRegistryName(), null));
//    ImageUtil.registerDynamicTexture(
//        new ResourceLocation(MODID, "textures/block/player_interface.png"),
//        OverloadedConfig.INSTANCE.textureResolutions.blockResolution);
  }


  @Override
  protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
    if (!world.isClientSide && hand == InteractionHand.MAIN_HAND) {
      BlockEntity te = world.getBlockEntity(pos);

      if (te instanceof TilePlayerInterface) {
        UUID placer = ((TilePlayerInterface) te).getPlacer();

        if (placer == null) {
          player.displayClientMessage(Component.literal("Not bound to anyone..... ghosts placed this."),false);
        } else {
          String username = UsernameCache.getLastKnownUsername(placer);
          player.displayClientMessage(
                  Component.literal(
                  "Bound to player: " + (username == null ? placer.toString() : username)), false);
        }
      }
      return InteractionResult.SUCCESS;
    }

    return super.useItemOn(stack,state, world, pos, player, hand, hitResult);
  }

  @Override
  public boolean useShapeForLightOcclusion(@Nonnull BlockState state) {
    return true;
  }
}
