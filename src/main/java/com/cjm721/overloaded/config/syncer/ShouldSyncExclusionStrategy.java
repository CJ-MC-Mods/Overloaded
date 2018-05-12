package com.cjm721.overloaded.config.syncer;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class ShouldSyncExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(SyncToClient.class) == null;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
