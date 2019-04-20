package com.cjm721.overloaded.util;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModRegistrable {
    @OnlyIn(Dist.CLIENT)
    void registerModel();
}
