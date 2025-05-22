package com.cjm721.overloaded.capabilities;

import com.cjm721.overloaded.storage.IGenericDataStorage;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import static com.cjm721.overloaded.Overloaded.MODID;

public class CapabilityGenericDataStorage {

  private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
      DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MODID);
  //  private static final Supplier<AttachmentType<IGenericDataStorage>> GENERIC_DATA =
  // ATTACHMENT_TYPES.register(
  //          "handler", () -> AttachmentType.serializable(() -> new GenericDataStorage()).build()
  //  );

  public static ItemCapability<IGenericDataStorage, Void> GENERIC_DATA_STORAGE_ITEM =
      ItemCapability.createVoid(
          ResourceLocation.fromNamespaceAndPath(MODID, "generic_data"), IGenericDataStorage.class);

  public static EntityCapability<IGenericDataStorage, Void> GENERIC_DATA_STORAGE_ENTITY =
      EntityCapability.createVoid(
          ResourceLocation.fromNamespaceAndPath(MODID, "generic_data"), IGenericDataStorage.class);
}
