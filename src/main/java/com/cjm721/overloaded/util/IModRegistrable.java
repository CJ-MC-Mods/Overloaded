package com.cjm721.overloaded.util;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public interface IModRegistrable {
    @OnlyIn(Dist.CLIENT)
    void registerModel();
}
