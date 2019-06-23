package com.cjm721.overloaded.block.tile.hyperTransfer;

import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.block.tile.hyperTransfer.base.AbstractTileHyperSender;
import com.cjm721.overloaded.storage.LongItemStack;
import com.cjm721.overloaded.storage.item.IHyperHandlerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.util.CapabilityHyperItem.HYPER_ITEM_HANDLER;

/** {@link TileEntity That is able to receive items from a remote source} */
public class TileHyperItemSender extends AbstractTileHyperSender<LongItemStack, IHyperHandlerItem> {

  public TileHyperItemSender() {
    super(
        TileEntityType.Builder.create(TileHyperItemSender::new, ModBlocks.hyperItemSender)
            .build(null),
        HYPER_ITEM_HANDLER);
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
