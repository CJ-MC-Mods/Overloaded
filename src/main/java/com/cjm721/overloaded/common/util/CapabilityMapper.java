package com.cjm721.overloaded.common.util;

import com.cjm721.overloaded.common.storage.IHyperHandler;
import net.minecraftforge.common.capabilities.Capability;

import static com.cjm721.overloaded.common.util.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;
import static com.cjm721.overloaded.common.util.CapabilityHyperFluid.HYPER_FLUID_HANDLER;
import static com.cjm721.overloaded.common.util.CapabilityHyperItem.HYPER_ITEM_HANDLER;

/**
 * Created by CJ on 4/10/2017.
 */
public class CapabilityMapper {
    public static Capability<?> getCapabilityForHandler(Class<? extends IHyperHandler> clazz) {
        switch(clazz.getName()) {
            case "IHyperHandlerEnergy":
                return HYPER_ENERGY_HANDLER;
            case "IHyperHandlerFluid":
                return HYPER_FLUID_HANDLER;
            case "IHyperHandlerItem":
                return HYPER_ITEM_HANDLER;
        }
        return null;
    }
}
