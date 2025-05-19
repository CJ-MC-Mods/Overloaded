//package com.cjm721.overloaded.storage.itemwrapper;
//
//import com.cjm721.overloaded.storage.GenericDataCapabilityProvider;
//
//public class GenericDataCapabilityProviderWrapper extends GenericDataCapabilityProvider {
//  private static final String NBT_TAG = "overloaded:generic_data";
//
////  @Nonnull private final ItemStack stack;
//
////  public GenericDataCapabilityProviderWrapper(@Nonnull ItemStack stack) {
////    this.stack = stack;
////
////    CompoundTag itemNBT = this.stack.getTag();
////    if (itemNBT == null) {
////      this.stack.setTag(new CompoundTag());
////    }
////  }
////
//  @Override
//  public void suggestUpdate() {
////    CompoundTag itemNBT = this.stack.getTag();
////
////    if (itemNBT != null && itemNBT.contains(NBT_TAG)) {
////      this.readNBT(GENERIC_DATA_STORAGE, this, null, this.stack.getTag().get(NBT_TAG));
////    }
//  }
////
////  @Override
//  public void suggestSave() {
////    CompoundTag data = this.writeNBT(GENERIC_DATA_STORAGE, this, null);
////    CompoundTag itemNBT = this.stack.getTag();
////    if (itemNBT == null) {
////      itemNBT = new CompoundTag();
////      this.stack.setTag(itemNBT);
////    }
////    this.stack.getTag().put(NBT_TAG, data);
//  }
//}
