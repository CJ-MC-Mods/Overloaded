package com.cjm721.overloaded.proxy;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

@OnlyIn(Dist.DEDICATED_SERVER)
@EventBusSubscriber(Dist.DEDICATED_SERVER)
public class ServerProxy extends CommonProxy {}
