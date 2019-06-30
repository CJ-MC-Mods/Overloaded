package com.cjm721.overloaded.storage.stacks.bigint;

import com.cjm721.overloaded.storage.IHyperType;

import java.math.BigInteger;

public class BigIntEnergyStack implements IHyperType<BigInteger> {
  public BigInteger amount;

  public BigIntEnergyStack(BigInteger amount) {
    this.amount = amount;
  }

  @Override
  public BigInteger getAmount() {
    return amount;
  }
}
