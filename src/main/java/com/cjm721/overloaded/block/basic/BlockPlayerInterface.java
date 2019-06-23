package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.block.ModBlockTile;
import com.cjm721.overloaded.block.tile.TilePlayerInterface;
import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.client.render.tile.PlayerInterfaceRenderer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
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
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import javax.annotation.Nullable;
import java.util.UUID;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockPlayerInterface extends ModBlockTile {

  public BlockPlayerInterface() {
    super(getDefaultProperties());
  }

  @Override
  public void baseInit() {
    setRegistryName("player_interface");
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TilePlayerInterface();
  }

  @Override
  public void onBlockPlacedBy(
      World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
    ((TilePlayerInterface) world.getTileEntity(pos)).setPlacer(entity);

    super.onBlockPlacedBy(world, pos, state, entity, stack);
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void registerModel() {
    //        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new
    // ModelResourceLocation(getRegistryName(), null));
    ClientRegistry.bindTileEntitySpecialRenderer(
        TilePlayerInterface.class, new PlayerInterfaceRenderer());

    ImageUtil.registerDynamicTexture(
        new ResourceLocation(MODID, "textures/blocks/block_player.png"),
        Overloaded.cachedConfig.textureResolutions.blockResolution);
  }

  @Override
  public void onBlockClicked(BlockState state, World world, BlockPos pos, PlayerEntity player) {
    if (!world.isRemote && player.getActiveHand() == Hand.MAIN_HAND) {
      TileEntity te = world.getTileEntity(pos);

      if (te instanceof TilePlayerInterface) {
        UUID placer = ((TilePlayerInterface) te).getPlacer();

        if (placer == null) {
          player.sendMessage(
              new StringTextComponent("Not bound to anyone..... ghosts placed this."));
        } else {
          String username = UsernameCache.getLastKnownUsername(placer);
          player.sendMessage(
              new StringTextComponent(
                  "Bound to player: " + (username == null ? placer.toString() : username)));
        }
      }
    }

    super.onBlockClicked(state, world, pos, player);
  }
}
