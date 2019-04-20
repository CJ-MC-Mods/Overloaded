package com.cjm721.overloaded.block;

import com.cjm721.overloaded.OverloadedCreativeTabs;
import com.cjm721.overloaded.util.IModRegistrable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public abstract class ModBlock extends Block implements IModRegistrable {
    protected ModBlock(@Nonnull Material materialIn) {
        super(materialIn);
        setHardness(1);
        setCreativeTab(OverloadedCreativeTabs.TECH);
    }

    public abstract void baseInit();

    @OnlyIn(Dist.CLIENT)
    @Override
    public abstract void registerModel();
}
