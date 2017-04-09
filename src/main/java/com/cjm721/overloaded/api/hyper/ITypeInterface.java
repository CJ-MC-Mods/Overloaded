package com.cjm721.overloaded.api.hyper;

import net.minecraftforge.common.capabilities.Capability;

/**
 * Interface that will be for {@link Capability} but with all methods
 * able to handle some type of {@link Number}
 */
public interface ITypeInterface<T,N extends Number> {

    T status();

    T give(T t, boolean doAction);

    T take(T t, boolean doAction);
}
