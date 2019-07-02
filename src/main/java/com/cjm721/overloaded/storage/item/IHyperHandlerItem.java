package com.cjm721.overloaded.storage.item;

import com.cjm721.overloaded.capabilities.CapabilityHyperItem;
import com.cjm721.overloaded.storage.IHyperHandler;
import com.cjm721.overloaded.storage.stacks.intint.LongItemStack;

/**
 * Used as a concrete type for {@link CapabilityHyperItem} registration
 */
public interface IHyperHandlerItem extends IHyperHandler<LongItemStack> {}
