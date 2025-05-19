package com.cjm721.overloaded.tile;

import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.tile.functional.*;
import com.cjm721.overloaded.tile.hyperTransfer.*;
import com.cjm721.overloaded.tile.infinity.*;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

import static com.cjm721.overloaded.Overloaded.BLOCK_ENTITY_TYPES;
import static com.cjm721.overloaded.Overloaded.MODID;

public class ModTiles {

  private static class TileResourceLocations {
    static final String creativeGeneratorFE = MODID + ":creative_generator";
    static final String energyExtractor = MODID + ":energy_extractor";
    static final String energyInjectorChest = MODID + ":energy_injector_chest";
    static final String infiniteWaterSource = MODID + ":infinite_water_source";
    static final String itemInterface = MODID + ":item_interface";
    static final String itemManipulator = MODID + ":item_manipulator";
    static final String matterPurifier = MODID + ":matter_purifier";
    static final String playerInterface = MODID + ":player_interface";
    static final String teamLoader = MODID + ":team_loader";
    static final String instantFurnace = MODID + ":instant_furnace";
    static final String hyperItemReceiver = MODID + ":hyper_item_receiver";
    static final String hyperItemSender = MODID + ":hyper_item_sender";
    static final String almostInfiniteBarrel = MODID + ":almost_infinite_barrel";
    static final String trueInfiniteBarrel = MODID + ":true_infinite_barrel";
    static final String hyperEnergyReceiver = MODID + ":hyper_energy_receiver";
    static final String hyperEnergySender = MODID + ":hyper_energy_sender";
    static final String almostInfiniteCapacitor = MODID + ":almost_infinite_capacitor";
    static final String trueInfiniteCapacitor = MODID + ":true_infinite_capacitor";
    static final String hyperFluidReceiver = MODID + ":hyper_fluid_receiver";
    static final String hyperFluidSender = MODID + ":hyper_fluid_sender";
    static final String almostInfiniteTank = MODID + ":almost_infinite_tank";
    static final String trueInfiniteTank = MODID + ":true_infinite_tank";
  }

  public static final Supplier<BlockEntityType<TileCreativeGeneratorFE>> creativeGeneratorFE = BLOCK_ENTITY_TYPES.register(TileResourceLocations.creativeGeneratorFE, () -> new BlockEntityType<>(TileCreativeGeneratorFE::new,ModBlocks.creativeGenerator.get()));
  public static final Supplier<BlockEntityType<TileEnergyExtractor>> energyExtractor = BLOCK_ENTITY_TYPES.register(TileResourceLocations.energyExtractor, () -> new BlockEntityType<>(TileEnergyExtractor::new,ModBlocks.energyExtractor.get()));
  public static final Supplier<BlockEntityType<TileEnergyInjectorChest>> energyInjectorChest = BLOCK_ENTITY_TYPES.register(TileResourceLocations.energyInjectorChest, () -> new BlockEntityType<>(TileEnergyInjectorChest::new,ModBlocks.energyInjectorChest.get()));
  public static final Supplier<BlockEntityType<TileInfiniteWaterSource>> infiniteWaterSource = BLOCK_ENTITY_TYPES.register(TileResourceLocations.infiniteWaterSource, () -> new BlockEntityType<>(TileInfiniteWaterSource::new,ModBlocks.infiniteWaterSource.get()));
  public static final Supplier<BlockEntityType<TileItemInterface>> itemInterface = BLOCK_ENTITY_TYPES.register(TileResourceLocations.itemInterface, () -> new BlockEntityType<>(TileItemInterface::new,ModBlocks.itemInterface.get()));
  public static final Supplier<BlockEntityType<TileItemManipulator>> itemManipulator = BLOCK_ENTITY_TYPES.register(TileResourceLocations.itemManipulator, () -> new BlockEntityType<>(TileItemManipulator::new,ModBlocks.itemManipulator.get()));
  public static final Supplier<BlockEntityType<TileMatterPurifier>> matterPurifier = BLOCK_ENTITY_TYPES.register(TileResourceLocations.matterPurifier, () -> new BlockEntityType<>(TileMatterPurifier::new,ModBlocks.matterPurifier.get()));
  public static final Supplier<BlockEntityType<TilePlayerInterface>> playerInterface = BLOCK_ENTITY_TYPES.register(TileResourceLocations.playerInterface, () -> new BlockEntityType<>(TilePlayerInterface::new,ModBlocks.playerInterface.get()));
  public static final Supplier<BlockEntityType<TileTeamLoader>> teamLoader = BLOCK_ENTITY_TYPES.register(TileResourceLocations.teamLoader, () -> new BlockEntityType<>(TileTeamLoader::new,ModBlocks.teamLoader.get()));
  public static final Supplier<BlockEntityType<TileInstantFurnace>> instantFurnace = BLOCK_ENTITY_TYPES.register(TileResourceLocations.instantFurnace, () -> new BlockEntityType<>(TileInstantFurnace::new,ModBlocks.instantFurnace.get()));
  public static final Supplier<BlockEntityType<TileHyperItemReceiver>> hyperItemReceiver = BLOCK_ENTITY_TYPES.register(TileResourceLocations.hyperItemReceiver, () -> new BlockEntityType<>(TileHyperItemReceiver::new,ModBlocks.hyperItemReceiver.get()));
  public static final Supplier<BlockEntityType<TileHyperItemSender>> hyperItemSender = BLOCK_ENTITY_TYPES.register(TileResourceLocations.hyperItemSender, () -> new BlockEntityType<>(TileHyperItemSender::new,ModBlocks.hyperItemSender.get()));
  public static final Supplier<BlockEntityType<TileAlmostInfiniteBarrel>> almostInfiniteBarrel = BLOCK_ENTITY_TYPES.register(TileResourceLocations.almostInfiniteBarrel, () -> new BlockEntityType<>(TileAlmostInfiniteBarrel::new,ModBlocks.almostInfiniteBarrel.get()));
  public static final Supplier<BlockEntityType<TileTrueInfiniteBarrel>> trueInfiniteBarrel = BLOCK_ENTITY_TYPES.register(TileResourceLocations.trueInfiniteBarrel, () -> new BlockEntityType<>(TileTrueInfiniteBarrel::new,ModBlocks.trueInfiniteBarrel.get()));
  public static final Supplier<BlockEntityType<TileHyperEnergyReceiver>> hyperEnergyReceiver = BLOCK_ENTITY_TYPES.register(TileResourceLocations.hyperEnergyReceiver, () -> new BlockEntityType<>(TileHyperEnergyReceiver::new,ModBlocks.hyperEnergyReceiver.get()));
  public static final Supplier<BlockEntityType<TileHyperEnergySender>> hyperEnergySender = BLOCK_ENTITY_TYPES.register(TileResourceLocations.hyperEnergySender, () -> new BlockEntityType<>(TileHyperEnergySender::new,ModBlocks.hyperEnergySender.get()));
  public static final Supplier<BlockEntityType<TileAlmostInfiniteCapacitor>> almostInfiniteCapacitor = BLOCK_ENTITY_TYPES.register(TileResourceLocations.almostInfiniteCapacitor, () -> new BlockEntityType<>(TileAlmostInfiniteCapacitor::new,ModBlocks.almostInfiniteCapacitor.get()));
  public static final Supplier<BlockEntityType<TileTrueInfiniteCapacitor>> trueInfiniteCapacitor = BLOCK_ENTITY_TYPES.register(TileResourceLocations.trueInfiniteCapacitor, () -> new BlockEntityType<>(TileTrueInfiniteCapacitor::new,ModBlocks.trueInfiniteCapacitor.get()));
  public static final Supplier<BlockEntityType<TileHyperFluidReceiver>> hyperFluidReceiver = BLOCK_ENTITY_TYPES.register(TileResourceLocations.hyperFluidReceiver, () -> new BlockEntityType<>(TileHyperFluidReceiver::new,ModBlocks.hyperFluidReceiver.get()));
  public static final Supplier<BlockEntityType<TileHyperFluidSender>> hyperFluidSender = BLOCK_ENTITY_TYPES.register(TileResourceLocations.hyperFluidSender, () -> new BlockEntityType<>(TileHyperFluidSender::new,ModBlocks.hyperFluidSender.get()));
  public static final Supplier<BlockEntityType<TileAlmostInfiniteTank>> almostInfiniteTank = BLOCK_ENTITY_TYPES.register(TileResourceLocations.almostInfiniteTank, () -> new BlockEntityType<>(TileAlmostInfiniteTank::new,ModBlocks.almostInfiniteTank.get()));
  public static final Supplier<BlockEntityType<TileTrueInfiniteTank>> trueInfiniteTank = BLOCK_ENTITY_TYPES.register(TileResourceLocations.trueInfiniteTank, () -> new BlockEntityType<>(TileTrueInfiniteTank::new,ModBlocks.trueInfiniteTank.get()));
}
