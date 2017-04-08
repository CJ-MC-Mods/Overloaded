package com.cjm721.ibhstd.common.util;

/**
 * Created by CJ on 4/8/2017.
 */
public final class NumberUtil {

    /**
     * Adds base and toAdd together and returns the overflow amount. Assumes all parameters are positive
     * @parm cap
     * @param base
     * @param toAdd
     * @return
     */
    public static final AddReturn<Long> addToMax(long base, long toAdd) {
        long temp = base + toAdd;
        if(temp < 0) {
            long overflow = temp - Long.MAX_VALUE;
            return new AddReturn<>(Long.MAX_VALUE, overflow);
        } else {
            return new AddReturn<Long>(temp, 0L);
        }
    }

    public static final class AddReturn<T extends Number> {
        public final T result;
        public final T overflow;

        public AddReturn(T result, T overflow) {
            this.result = result;
            this.overflow = overflow;
        }
    }
}
