package com.cjm721.overloaded.tile.hyperTransfer;

import com.cjm721.overloaded.storage.stacks.intint.LongItemStack;
import com.cjm721.overloaded.storage.item.IHyperHandlerItem;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.tile.hyperTransfer.base.AbstractTileHyperSender;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.util.CapabilityHyperItem.HYPER_ITEM_HANDLER;

/** {@link TileEntity That is able to receive items from a remote source} */
public class TileHyperItemSender extends AbstractTileHyperSender<LongItemStack, IHyperHandlerItem> {

  public TileHyperItemSender() {
    super(ModTiles.hyperItemSender, HYPER_ITEM_HANDLER);
  }

  @Override
  @Nonnull
  protected LongItemStack generate(long amount) {
    return new LongItemStack(ItemStack.EMPTY, amount);
  }

  @Override
  protected boolean isCorrectPartnerType(TileEntity te) {
    return te instanceof TileHyperItemReceiver;
  }
}
