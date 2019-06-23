package com.cjm721.overloaded.storage;

public class LongEnergyStack implements IHyperType {

  public static final LongEnergyStack EMPTY_STACK = new LongEnergyStack(0L);

  public long amount;

  public LongEnergyStack(long amount) {
    this.amount = amount;
  }

  @Override
  public long getAmount() {
    return amount;
  }
}
