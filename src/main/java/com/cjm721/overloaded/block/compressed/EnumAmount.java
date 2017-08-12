package com.cjm721.overloaded.block.compressed;

public enum EnumAmount {
    ONE      (0,  1,  "number.one"),
    TWO      (1,  2,  "number.two"),
    THREE    (2,  3,  "number.three"),
    FOUR     (3,  4,  "number.four"),
    FIVE     (4,  5,  "number.five"),
    SIX      (5,  6,  "number.six"),
    SEVEN    (6,  7,  "number.seven"),
    EIGHT    (7,  8,  "number.eight"),
    NINE     (8,  9,  "number.nine"),
    TEN      (9,  10, "number.ten"),
    ELEVEN   (10, 11, "number.eleven"),
    TWELVE   (11, 12, "number.twelve"),
    THIRTEEN (12, 13, "number.thirteen"),
    FOURTEEN (13, 14, "number.fourteen"),
    FIFTEEN  (14, 15, "number.fifteen"),
    SIXTEEN  (15, 16, "number.sixteen");

    private final int meta;
    private final int amount;
    private final String unlocalizedName;

    EnumAmount(int meta, int amount, String unlocalizedName) {
        this.meta = meta;
        this.amount = amount;
        this.unlocalizedName = unlocalizedName;
    }

    public int getMeta() {
        return meta;
    }

    public int getAmount() {
        return amount;
    }

    public String getUnlocalizedName() {
        return unlocalizedName;
    }
}
