package com.cjm721.overloaded.util;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModRegistrable {
    @SideOnly(Side.CLIENT)
    void registerModel();

    void registerRecipe();
}
