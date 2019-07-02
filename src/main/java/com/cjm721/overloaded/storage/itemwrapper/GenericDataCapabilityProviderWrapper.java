package com.cjm721.overloaded.storage.itemwrapper;

import com.cjm721.overloaded.storage.GenericDataCapabilityProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.capabilities.CapabilityGenericDataStorage.GENERIC_DATA_STORAGE;

public class GenericDataCapabilityProviderWrapper extends GenericDataCapabilityProvider {
  private static final String NBT_TAG = "overloaded:generic_data";

  @Nonnull private final ItemStack stack;

  public GenericDataCapabilityProviderWrapper(@Nonnull ItemStack stack) {
    this.stack = stack;

    CompoundNBT itemNBT = this.stack.getTag();
    if (itemNBT == null) {
      this.stack.setTag(new CompoundNBT());
    }
  }

  @Override
  public void suggestUpdate() {
    CompoundNBT itemNBT = this.stack.getTag();

    if (itemNBT != null && itemNBT.contains(NBT_TAG)) {
      this.readNBT(GENERIC_DATA_STORAGE, this, null, this.stack.getTag().get(NBT_TAG));
    }
  }

  @Override
  public void suggestSave() {
    CompoundNBT data = this.writeNBT(GENERIC_DATA_STORAGE, this, null);
    CompoundNBT itemNBT = this.stack.getTag();
    if (itemNBT == null) {
      itemNBT = new CompoundNBT();
      this.stack.setTag(itemNBT);
    }
    this.stack.getTag().put(NBT_TAG, data);
  }
}
