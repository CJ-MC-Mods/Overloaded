package com.cjm721.overloaded.proxy;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.DEDICATED_SERVER)
@Mod.EventBusSubscriber(Dist.DEDICATED_SERVER)
public class ServerProxy extends CommonProxy {}
