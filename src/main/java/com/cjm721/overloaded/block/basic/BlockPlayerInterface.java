package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.block.ModBlockTile;
import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.tile.functional.TilePlayerInterface;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
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
import net.minecraftforge.common.UsernameCache;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockPlayerInterface extends ModBlockTile {

  public BlockPlayerInterface() {
    super(Properties.create(Material.GLASS).hardnessAndResistance(3).variableOpacity().notSolid());
    setRegistryName("player_interface");
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TilePlayerInterface();
  }

  @Override
  public void onBlockPlacedBy(
      World world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nullable LivingEntity entity, @Nonnull ItemStack stack) {
    ((TilePlayerInterface) world.getTileEntity(pos)).setPlacer(entity);

    super.onBlockPlacedBy(world, pos, state, entity, stack);
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void registerModel() {
    //        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new
    // ModelResourceLocation(getRegistryName(), null));
    ImageUtil.registerDynamicTexture(
        new ResourceLocation(MODID, "textures/block/player_interface.png"),
        OverloadedConfig.INSTANCE.textureResolutions.blockResolution);
  }

  @Override
  @Nonnull
  public ActionResultType onBlockActivated(
      @Nonnull BlockState state,
      World world,
      @Nonnull BlockPos pos,
      @Nonnull PlayerEntity player,
      @Nonnull Hand hand,
      @Nonnull BlockRayTraceResult rayTraceResult) {
    if (!world.isRemote && hand == Hand.MAIN_HAND) {
      TileEntity te = world.getTileEntity(pos);

      if (te instanceof TilePlayerInterface) {
        UUID placer = ((TilePlayerInterface) te).getPlacer();

        if (placer == null) {
          player.sendMessage(
              new StringTextComponent("Not bound to anyone..... ghosts placed this."), player.getUniqueID());
        } else {
          String username = UsernameCache.getLastKnownUsername(placer);
          player.sendMessage(
              new StringTextComponent(
                  "Bound to player: " + (username == null ? placer.toString() : username)), player.getUniqueID());
        }
      }
      return ActionResultType.SUCCESS;
    }

    return super.onBlockActivated(state, world, pos, player, hand, rayTraceResult);
  }

  @Override
  public boolean isTransparent(@Nonnull BlockState state) {
    return true;
  }
}
