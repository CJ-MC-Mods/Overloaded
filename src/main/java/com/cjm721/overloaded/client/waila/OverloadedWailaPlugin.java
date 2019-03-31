package com.cjm721.overloaded.client.waila;

import com.cjm721.overloaded.block.basic.container.BlockInfiniteCapacitor;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.api.WailaPlugin;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;

@WailaPlugin
public class OverloadedWailaPlugin implements IWailaPlugin {

    public OverloadedWailaPlugin() {
        // Required by Walia API
    }

    /**
     * Called during {@link FMLLoadCompleteEvent}.
     *
     * @param registrar - An instance of IWailaRegistrar to register your providers with.
     */
    @Override
    public void register(IWailaRegistrar registrar) {
        HyperEnergyStorageBlockDecorator decorator = new HyperEnergyStorageBlockDecorator();
        registrar.registerTailProvider(decorator, BlockInfiniteCapacitor.class);
        registrar.registerNBTProvider(decorator, BlockInfiniteCapacitor.class);
//        registrar.registerDecorator(decorator, BlockInfiniteTank.class);
//        registrar.registerDecorator(decorator, BlockInfiniteBarrel.class);
    }
}
