package com.cjm721.overloaded.item.functional.armor;

import javax.annotation.Nonnull;
import java.util.Map;

public interface IOverloadedPlayerDataStorage {

    @Nonnull
    Map<String, Integer> getIntegerMap();

    @Nonnull
    Map<String, Boolean> getBooleanMap();
}
