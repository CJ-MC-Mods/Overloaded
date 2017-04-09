package com.cjm721.overloaded.api.hyper;

import net.minecraftforge.common.capabilities.Capability;

/**
 * Interface that will be for {@link Capability} but with all methods
 * able to handle some type of {@link Number}
 */
public interface ITypeInterface<T,N extends Number> {

    N status();

    /**
     *
     * @param t
     * @param n
     * @param doAction
     * @return
     */
    N give(T t, N n, boolean doAction);

    /**
     *
     * @param t
     * @param n
     * @param doAction
     * @return
     */
    N take(T t, N n, boolean doAction);
}
