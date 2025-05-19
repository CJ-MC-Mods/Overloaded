package com.cjm721.overloaded.tile;

import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.tile.functional.*;
import com.cjm721.overloaded.tile.hyperTransfer.*;
import com.cjm721.overloaded.tile.infinity.*;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.cjm721.overloaded.Overloaded.MODID;

public class ModTiles {

  private static class TileResourceLocations {
    static final String creativeGeneratorFE = "creative_generator";
    static final String energyExtractor = "energy_extractor";
    static final String energyInjectorChest = "energy_injector_chest";
    static final String infiniteWaterSource = "infinite_water_source";
    static final String itemInterface = "item_interface";
    static final String itemManipulator = "item_manipulator";
    static final String matterPurifier = "matter_purifier";
    static final String playerInterface = "player_interface";
    static final String teamLoader = "team_loader";
    static final String instantFurnace = "instant_furnace";
    static final String hyperItemReceiver = "hyper_item_receiver";
    static final String hyperItemSender = "hyper_item_sender";
    static final String almostInfiniteBarrel = "almost_infinite_barrel";
    static final String trueInfiniteBarrel = "true_infinite_barrel";
    static final String hyperEnergyReceiver = "hyper_energy_receiver";
    static final String hyperEnergySender = "hyper_energy_sender";
    static final String almostInfiniteCapacitor = "almost_infinite_capacitor";
    static final String trueInfiniteCapacitor = "true_infinite_capacitor";
    static final String hyperFluidReceiver = "hyper_fluid_receiver";
    static final String hyperFluidSender = "hyper_fluid_sender";
    static final String almostInfiniteTank = "almost_infinite_tank";
    static final String trueInfiniteTank = "true_infinite_tank";
  }

  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
          DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);
  public static final Supplier<BlockEntityType<TileCreativeGeneratorFE>> creativeGeneratorFE = BLOCK_ENTITY_TYPES.register(TileResourceLocations.creativeGeneratorFE, () -> new BlockEntityType<>(TileCreativeGeneratorFE::new, ImmutableSet.of(ModBlocks.creativeGenerator.get()), null));
  public static final Supplier<BlockEntityType<TileEnergyExtractor>> energyExtractor = BLOCK_ENTITY_TYPES.register(TileResourceLocations.energyExtractor, () -> new BlockEntityType<>(TileEnergyExtractor::new,ImmutableSet.of(ModBlocks.energyExtractor.get()),null));
  public static final Supplier<BlockEntityType<TileEnergyInjectorChest>> energyInjectorChest = BLOCK_ENTITY_TYPES.register(TileResourceLocations.energyInjectorChest, () -> new BlockEntityType<>(TileEnergyInjectorChest::new,ImmutableSet.of(ModBlocks.energyInjectorChest.get()),null));
  public static final Supplier<BlockEntityType<TileInfiniteWaterSource>> infiniteWaterSource = BLOCK_ENTITY_TYPES.register(TileResourceLocations.infiniteWaterSource, () -> new BlockEntityType<>(TileInfiniteWaterSource::new,ImmutableSet.of(ModBlocks.infiniteWaterSource.get()),null));
  public static final Supplier<BlockEntityType<TileItemInterface>> itemInterface = BLOCK_ENTITY_TYPES.register(TileResourceLocations.itemInterface, () -> new BlockEntityType<>(TileItemInterface::new,ImmutableSet.of(ModBlocks.itemInterface.get()),null));
  public static final Supplier<BlockEntityType<TileItemManipulator>> itemManipulator = BLOCK_ENTITY_TYPES.register(TileResourceLocations.itemManipulator, () -> new BlockEntityType<>(TileItemManipulator::new,ImmutableSet.of(ModBlocks.itemManipulator.get()),null));
  public static final Supplier<BlockEntityType<TileMatterPurifier>> matterPurifier = BLOCK_ENTITY_TYPES.register(TileResourceLocations.matterPurifier, () -> new BlockEntityType<>(TileMatterPurifier::new,ImmutableSet.of(ModBlocks.matterPurifier.get()),null));
  public static final Supplier<BlockEntityType<TilePlayerInterface>> playerInterface = BLOCK_ENTITY_TYPES.register(TileResourceLocations.playerInterface, () -> new BlockEntityType<>(TilePlayerInterface::new,ImmutableSet.of(ModBlocks.playerInterface.get()),null));
  public static final Supplier<BlockEntityType<TileTeamLoader>> teamLoader = BLOCK_ENTITY_TYPES.register(TileResourceLocations.teamLoader, () -> new BlockEntityType<>(TileTeamLoader::new,ImmutableSet.of(ModBlocks.teamLoader.get()),null));
  public static final Supplier<BlockEntityType<TileInstantFurnace>> instantFurnace = BLOCK_ENTITY_TYPES.register(TileResourceLocations.instantFurnace, () -> new BlockEntityType<>(TileInstantFurnace::new,ImmutableSet.of(ModBlocks.instantFurnace.get()),null));
  public static final Supplier<BlockEntityType<TileHyperItemReceiver>> hyperItemReceiver = BLOCK_ENTITY_TYPES.register(TileResourceLocations.hyperItemReceiver, () -> new BlockEntityType<>(TileHyperItemReceiver::new,ImmutableSet.of(ModBlocks.hyperItemReceiver.get()),null));
  public static final Supplier<BlockEntityType<TileHyperItemSender>> hyperItemSender = BLOCK_ENTITY_TYPES.register(TileResourceLocations.hyperItemSender, () -> new BlockEntityType<>(TileHyperItemSender::new,ImmutableSet.of(ModBlocks.hyperItemSender.get()),null));
  public static final Supplier<BlockEntityType<TileAlmostInfiniteBarrel>> almostInfiniteBarrel = BLOCK_ENTITY_TYPES.register(TileResourceLocations.almostInfiniteBarrel, () -> new BlockEntityType<>(TileAlmostInfiniteBarrel::new,ImmutableSet.of(ModBlocks.almostInfiniteBarrel.get()),null));
  public static final Supplier<BlockEntityType<TileTrueInfiniteBarrel>> trueInfiniteBarrel = BLOCK_ENTITY_TYPES.register(TileResourceLocations.trueInfiniteBarrel, () -> new BlockEntityType<>(TileTrueInfiniteBarrel::new,ImmutableSet.of(ModBlocks.trueInfiniteBarrel.get()),null));
  public static final Supplier<BlockEntityType<TileHyperEnergyReceiver>> hyperEnergyReceiver = BLOCK_ENTITY_TYPES.register(TileResourceLocations.hyperEnergyReceiver, () -> new BlockEntityType<>(TileHyperEnergyReceiver::new,ImmutableSet.of(ModBlocks.hyperEnergyReceiver.get()),null));
  public static final Supplier<BlockEntityType<TileHyperEnergySender>> hyperEnergySender = BLOCK_ENTITY_TYPES.register(TileResourceLocations.hyperEnergySender, () -> new BlockEntityType<>(TileHyperEnergySender::new,ImmutableSet.of(ModBlocks.hyperEnergySender.get()),null));
  public static final Supplier<BlockEntityType<TileAlmostInfiniteCapacitor>> almostInfiniteCapacitor = BLOCK_ENTITY_TYPES.register(TileResourceLocations.almostInfiniteCapacitor, () -> new BlockEntityType<>(TileAlmostInfiniteCapacitor::new,ImmutableSet.of(ModBlocks.almostInfiniteCapacitor.get()),null));
  public static final Supplier<BlockEntityType<TileTrueInfiniteCapacitor>> trueInfiniteCapacitor = BLOCK_ENTITY_TYPES.register(TileResourceLocations.trueInfiniteCapacitor, () -> new BlockEntityType<>(TileTrueInfiniteCapacitor::new,ImmutableSet.of(ModBlocks.trueInfiniteCapacitor.get()),null));
  public static final Supplier<BlockEntityType<TileHyperFluidReceiver>> hyperFluidReceiver = BLOCK_ENTITY_TYPES.register(TileResourceLocations.hyperFluidReceiver, () -> new BlockEntityType<>(TileHyperFluidReceiver::new,ImmutableSet.of(ModBlocks.hyperFluidReceiver.get()),null));
  public static final Supplier<BlockEntityType<TileHyperFluidSender>> hyperFluidSender = BLOCK_ENTITY_TYPES.register(TileResourceLocations.hyperFluidSender, () -> new BlockEntityType<>(TileHyperFluidSender::new,ImmutableSet.of(ModBlocks.hyperFluidSender.get()),null));
  public static final Supplier<BlockEntityType<TileAlmostInfiniteTank>> almostInfiniteTank = BLOCK_ENTITY_TYPES.register(TileResourceLocations.almostInfiniteTank, () -> new BlockEntityType<>(TileAlmostInfiniteTank::new,ImmutableSet.of(ModBlocks.almostInfiniteTank.get()),null));
  public static final Supplier<BlockEntityType<TileTrueInfiniteTank>> trueInfiniteTank = BLOCK_ENTITY_TYPES.register(TileResourceLocations.trueInfiniteTank, () -> new BlockEntityType<>(TileTrueInfiniteTank::new,ImmutableSet.of(ModBlocks.trueInfiniteTank.get()),null));
}
