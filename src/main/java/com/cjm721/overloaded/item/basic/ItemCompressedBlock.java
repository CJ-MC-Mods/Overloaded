package com.cjm721.overloaded.item.basic;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.block.compressed.BlockCompressed;
import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.util.IModRegistrable;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class ItemCompressedBlock extends ItemBlock implements IModRegistrable {

    private final BlockCompressed compressedBlock;

    public ItemCompressedBlock(BlockCompressed block) {
        super(block);

        this.compressedBlock = block;
        setHasSubtypes(true);
        setRegistryName(block.getRegistryName());

        Overloaded.proxy.itemToRegister.add(this);
        ModItems.addToSecondaryInit(this);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public void getSubItems(@Nonnull CreativeTabs tab,@Nonnull NonNullList<ItemStack> items) {
        if(!isInCreativeTab(tab)) {
            return;
        }

        for(int meta = 0; meta < compressedBlock.getMaxCompression(); meta++) {
            items.add(new ItemStack(this,1,meta));
        }
    }

    @Override
    public void registerModel() {

    }
}
