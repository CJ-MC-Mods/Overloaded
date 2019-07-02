package com.cjm721.overloaded.storage.fluid;

import com.cjm721.overloaded.capabilities.CapabilityHyperFluid;
import com.cjm721.overloaded.storage.IHyperHandler;
import com.cjm721.overloaded.storage.stacks.intint.LongFluidStack;

/**
 * Used as a concrete type for {@link CapabilityHyperFluid} registration
 */
public interface IHyperHandlerFluid extends IHyperHandler<LongFluidStack> {}
