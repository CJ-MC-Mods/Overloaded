package com.cjm721.overloaded.common.storage;

/**
 * Created by CJ on 4/10/2017.
 */
public interface IHyperHandler<T extends IHyperType> {

    T status();

    T take(T stack, boolean doAction);

    T give(T stack, boolean doAction);
}
