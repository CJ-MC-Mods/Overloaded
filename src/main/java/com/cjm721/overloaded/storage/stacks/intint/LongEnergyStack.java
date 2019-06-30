package com.cjm721.overloaded.storage.stacks.intint;

import com.cjm721.overloaded.storage.IHyperType;

public class LongEnergyStack implements IHyperType<Long> {

  public static final LongEnergyStack EMPTY_STACK = new LongEnergyStack(0L);

  public long amount;

  public LongEnergyStack(long amount) {
    this.amount = amount;
  }

  @Override
  public Long getAmount() {
    return amount;
  }
}
