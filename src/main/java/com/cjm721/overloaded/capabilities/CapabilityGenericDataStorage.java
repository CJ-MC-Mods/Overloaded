package com.cjm721.overloaded.capabilities;

import com.cjm721.overloaded.storage.GenericDataStorage;
import com.cjm721.overloaded.storage.IGenericDataStorage;
import com.cjm721.overloaded.storage.energy.IHyperHandlerEnergy;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Direction;
import net.neoforged.common.capabilities.Capability;
import net.neoforged.common.capabilities.CapabilityInject;
import net.neoforged.common.capabilities.CapabilityManager;
import net.neoforged.common.util.Constants;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Supplier;

import static com.cjm721.overloaded.Overloaded.MODID;

public class CapabilityGenericDataStorage {

  private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MODID);
  private static final Supplier<AttachmentType<IGenericDataStorage>> GENERIC_DATA = ATTACHMENT_TYPES.register(
          "handler", () -> AttachmentType.serializable(() -> new GenericDataStorage()).build()
  );

  public static ItemCapability<IGenericDataStorage, Void> GENERIC_DATA_STORAGE = ItemCapability.createVoid(
          ResourceLocation.fromNamespaceAndPath(MODID,"generic_data"),
          IGenericDataStorage.class
  );


  public static void register() {
    CapabilityManager.INSTANCE.register(
        IGenericDataStorage.class,
        new Capability.IStorage<IGenericDataStorage>() {
          @Nullable
          @Override
          public INBT writeNBT(
              Capability<IGenericDataStorage> capability,
              IGenericDataStorage instance,
              Direction side) {
            CompoundNBT tagCompound = new CompoundNBT();
            Map<String, Integer> integers = instance.getIntegerMap();
            Map<String, Boolean> booleans = instance.getBooleanMap();
            Map<String, Float> floats = instance.getFloatMap();
            Map<String, Double> doubles = instance.getDoubleMap();

            for (String key : integers.keySet()) {
              tagCompound.putInt(key, integers.get(key));
            }

            for (String key : booleans.keySet()) {
              tagCompound.putBoolean(key, booleans.get(key));
            }

            for (String key : floats.keySet()) {
              tagCompound.putFloat(key, floats.get(key));
            }

            for (String key : doubles.keySet()) {
              tagCompound.putDouble(key, doubles.get(key));
            }

            return tagCompound;
          }

          @Override
          public void readNBT(
              Capability<IGenericDataStorage> capability,
              IGenericDataStorage instance,
              Direction side,
              INBT nbt) {
            if (!(nbt instanceof CompoundNBT)) return;

            CompoundNBT tagCompound = ((CompoundNBT) nbt);
            Map<String, Integer> integers = instance.getIntegerMap();
            Map<String, Boolean> booleans = instance.getBooleanMap();
            Map<String, Float> floats = instance.getFloatMap();
            Map<String, Double> doubles = instance.getDoubleMap();

            for (String key : tagCompound.getAllKeys()) {
              switch (tagCompound.getTagType(key)) {
                case Constants.NBT.TAG_INT:
                  integers.put(key, tagCompound.getInt(key));
                  break;
                case Constants.NBT.TAG_BYTE:
                  booleans.put(key, tagCompound.getBoolean(key));
                  break;
                case Constants.NBT.TAG_FLOAT:
                  floats.put(key, tagCompound.getFloat(key));
                  break;
                case Constants.NBT.TAG_DOUBLE:
                  doubles.put(key, tagCompound.getDouble(key));
                  break;
              }
            }
          }
        },
        GenericDataStorage::new);
  }
}
