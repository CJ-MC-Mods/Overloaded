//package com.cjm721.overloaded.storage.builder;
//
//import net.minecraft.core.Direction;
//import net.neoforged.neoforge.capabilities.ICapabilityProvider;
//
//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//public class CapabilityContainer implements ICapabilityProvider<T, Capability<T>, Direction> {
//
//  @Nonnull private final List<ICapabilityProvider> capabilityProviders;
//
//  public CapabilityContainer() {
//    capabilityProviders = new ArrayList<>();
//  }
//
//  public CapabilityContainer addCapability(ICapabilityProvider capabilityProvider) {
//    capabilityProviders.add(capabilityProvider);
//    return this;
//  }
//
//  public CapabilityContainer addCapability(Collection<ICapabilityProvider> capabilityProvider) {
//    capabilityProviders.addAll(capabilityProvider);
//    return this;
//  }
//
//  @Override
//  public @org.jetbrains.annotations.Nullable Direction getCapability(T object, Capability<T> context) {
//    return capabilityProviders.stream()
//            .map(cap -> cap.getCapability(capability, facing))
//            .filter(LazyOptional::isPresent)
//            .findAny()
//            .orElse(LazyOptional.empty());
//  }
//}
