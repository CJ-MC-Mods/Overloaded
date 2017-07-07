package com.cjm721.overloaded.block;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.util.IModRegistrable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public abstract class ModBlock extends Block implements IModRegistrable {
    public ModBlock(@Nonnull Material materialIn) {
        super(materialIn);
    }

    protected void register() {
        Overloaded.proxy.blocksToRegister.add(this);
        Overloaded.proxy.itemToRegister.add(new ItemBlock(this).setRegistryName(getRegistryName()));

        ModBlocks.addToSecondaryInit(this);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public abstract void registerModel();
}
