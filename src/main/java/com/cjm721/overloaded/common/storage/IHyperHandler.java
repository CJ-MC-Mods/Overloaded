package com.cjm721.overloaded.common.storage;

import javax.annotation.Nonnull;

/**
 * Created by CJ on 4/10/2017.
 */
public interface IHyperHandler<T extends IHyperType> {

    /**
     * @return a Concrete of {@link IHyperType} that represents the internal inventory
     */
    @Nonnull
    T status();

    /**
     * @param stack Represents what you want and how much
     * @param doAction Should
     * @return the Stack that was able to be removed from the Handler
     */
    @Nonnull
    T take(@Nonnull T stack, boolean doAction);

    /**
     * @param stack
     * @param doAction
     * @return the Stack that was not able to be absorbed by  the handler
     */
    @Nonnull
    T give(@Nonnull T stack, boolean doAction);
}
