package com.cjm721.ibhstd.magic.item;

import com.cjm721.ibhstd.api.hyper.ITypeInterface;
import net.minecraft.item.ItemStack;

/**
 * Created by CJ on 4/8/2017.
 */
public class HyperItemHandler implements ITypeInterface<ItemStack, Long> {

    @Override
    public Long status() {
        return null;
    }

    /**
     * @param itemStack
     * @param aLong
     * @param doAction
     * @return
     */
    @Override
    public Long give(ItemStack itemStack, Long aLong, boolean doAction) {
        return null;
    }

    /**
     * @param itemStack
     * @param aLong
     * @param doAction
     * @return
     */
    @Override
    public Long take(ItemStack itemStack, Long aLong, boolean doAction) {
        return null;
    }
}
