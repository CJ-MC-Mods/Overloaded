package com.cjm721.overloaded.common.storage;

import javax.annotation.Nonnull;

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
     * @param stack of Type {@link T} that will be handed to this handler
     * @param doAction should be actually done
     * @return the Stack that was not able to be absorbed by  the handler
     */
    @Nonnull
    T give(@Nonnull T stack, boolean doAction);
}
