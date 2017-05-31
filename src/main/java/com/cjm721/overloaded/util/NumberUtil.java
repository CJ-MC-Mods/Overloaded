package com.cjm721.overloaded.util;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

public final class NumberUtil {

    /**
     * Adds base and toAdd together and returns the overflow amount. Assumes all parameters are positive
     * @param base A long that must always be non-negative
     * @param toAdd A long that must always be non-negative
     * @return a {@link AddReturn} that contains the result of the operation and also a overflow
     */
    @Nonnull
    public static AddReturn<Long> addToMax(@Nonnegative long base, @Nonnegative long toAdd) {
        long temp = base + toAdd;
        if(temp < 0) {
            long overflow = temp - Long.MAX_VALUE;
            return new AddReturn<>(Long.MAX_VALUE, overflow);
        } else {
            return new AddReturn<>(temp, 0L);
        }
    }

    public static final class AddReturn<T extends Number> {
        public final T result;
        public final T overflow;

        AddReturn(@Nonnull T result,@Nonnull T overflow) {
            this.result = result;
            this.overflow = overflow;
        }
    }
}
