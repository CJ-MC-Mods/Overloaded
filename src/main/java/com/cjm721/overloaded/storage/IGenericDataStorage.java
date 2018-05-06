package com.cjm721.overloaded.storage;

import javax.annotation.Nonnull;
import java.util.Map;

public interface IGenericDataStorage {

    @Nonnull
    Map<String, Integer> getIntegerMap();

    @Nonnull
    Map<String, Boolean> getBooleanMap();

    @Nonnull
    Map<String,Double> getDoubleMap();

    default void suggestSave() { }
}
