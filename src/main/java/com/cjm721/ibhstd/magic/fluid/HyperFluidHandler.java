package com.cjm721.ibhstd.magic.fluid;

import com.cjm721.ibhstd.api.hyper.ITypeInterface;
import net.minecraftforge.fluids.FluidStack;

/**
 * Created by CJ on 4/8/2017.
 */
public class HyperFluidHandler implements ITypeInterface<FluidStack, Long> {

    @Override
    public Long status() {
        return null;
    }

    /**
     * @param fluidStack
     * @param aLong
     * @param doAction
     * @return
     */
    @Override
    public Long give(FluidStack fluidStack, Long aLong, boolean doAction) {
        return null;
    }

    /**
     * @param fluidStack
     * @param aLong
     * @param doAction
     * @return
     */
    @Override
    public Long take(FluidStack fluidStack, Long aLong, boolean doAction) {
        return null;
    }
}
