package com.cjm721.overloaded.tile.hyperTransfer;

import com.cjm721.overloaded.storage.stacks.intint.LongItemStack;
import com.cjm721.overloaded.storage.item.IHyperHandlerItem;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.tile.hyperTransfer.base.AbstractTileHyperSender;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.capabilities.CapabilityHyperItem.HYPER_ITEM_HANDLER;

/** {@link TileEntity That is able to receive items from a remote source} */
public class TileHyperItemSender extends AbstractTileHyperSender<LongItemStack, IHyperHandlerItem> {

  public TileHyperItemSender(BlockPos pos, BlockState state) {
    super(ModTiles.hyperItemSender, HYPER_ITEM_HANDLER, pos,state);
  }

  @Override
  @Nonnull
  protected LongItemStack generate(long amount) {
    return new LongItemStack(ItemStack.EMPTY, amount);
  }

  @Override
  protected boolean isCorrectPartnerType(BlockEntity te) {
    return te instanceof TileHyperItemReceiver;
  }
}
