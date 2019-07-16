package com.cjm721.overloaded.tile;

import com.cjm721.overloaded.block.ModBlocks;
import com.cjm721.overloaded.tile.functional.*;
import com.cjm721.overloaded.tile.hyperTransfer.*;
import com.cjm721.overloaded.tile.infinity.*;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Supplier;

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

  @ObjectHolder(TileResourceLocations.creativeGeneratorFE)
  public static TileEntityType<?> creativeGeneratorFE;

  @ObjectHolder(TileResourceLocations.energyExtractor)
  public static TileEntityType<?> energyExtractor;

  @ObjectHolder(TileResourceLocations.energyInjectorChest)
  public static TileEntityType<?> energyInjectorChest;

  @ObjectHolder(TileResourceLocations.infiniteWaterSource)
  public static TileEntityType<?> infiniteWaterSource;

  @ObjectHolder(TileResourceLocations.itemInterface)
  public static TileEntityType<?> itemInterface;

  @ObjectHolder(TileResourceLocations.itemManipulator)
  public static TileEntityType<?> itemManipulator;

  @ObjectHolder(TileResourceLocations.matterPurifier)
  public static TileEntityType<?> matterPurifier;

  @ObjectHolder(TileResourceLocations.playerInterface)
  public static TileEntityType<?> playerInterface;

  @ObjectHolder(TileResourceLocations.teamLoader)
  public static TileEntityType<?> teamLoader;

  @ObjectHolder(TileResourceLocations.instantFurnace)
  public static TileEntityType<?> instantFurnace;

  @ObjectHolder(TileResourceLocations.hyperItemReceiver)
  public static TileEntityType<?> hyperItemReceiver;

  @ObjectHolder(TileResourceLocations.hyperItemSender)
  public static TileEntityType<?> hyperItemSender;

  @ObjectHolder(TileResourceLocations.almostInfiniteBarrel)
  public static TileEntityType<?> almostInfiniteBarrel;

  @ObjectHolder(TileResourceLocations.trueInfiniteBarrel)
  public static TileEntityType<?> trueInfiniteBarrel;

  @ObjectHolder(TileResourceLocations.hyperEnergyReceiver)
  public static TileEntityType<?> hyperEnergyReceiver;

  @ObjectHolder(TileResourceLocations.hyperEnergySender)
  public static TileEntityType<?> hyperEnergySender;

  @ObjectHolder(TileResourceLocations.almostInfiniteCapacitor)
  public static TileEntityType<?> almostInfiniteCapacitor;

  @ObjectHolder(TileResourceLocations.trueInfiniteCapacitor)
  public static TileEntityType<?> trueInfiniteCapacitor;

  @ObjectHolder(TileResourceLocations.hyperFluidReceiver)
  public static TileEntityType<?> hyperFluidReceiver;

  @ObjectHolder(TileResourceLocations.hyperFluidSender)
  public static TileEntityType<?> hyperFluidSender;

  @ObjectHolder(TileResourceLocations.almostInfiniteTank)
  public static TileEntityType<?> almostInfiniteTank;

  @ObjectHolder(TileResourceLocations.trueInfiniteTank)
  public static TileEntityType<?> trueInfiniteTank;

  public static void init(IForgeRegistry<TileEntityType<?>> registry) {
    registry.register(
        build(
            TileCreativeGeneratorFE::new,
            ModBlocks.creativeGenerator,
            TileResourceLocations.creativeGeneratorFE));
    registry.register(
        build(
            TileEnergyExtractor::new,
            ModBlocks.energyExtractor,
            TileResourceLocations.energyExtractor));
//    registry.register(
//        build(
//            TileEnergyInjectorChest::new,
//            ModBlocks.energyInjectorChest,
//            TileResourceLocations.energyInjectorChest));
    registry.register(
        build(
            TileInfiniteWaterSource::new,
            ModBlocks.infiniteWaterSource,
            TileResourceLocations.infiniteWaterSource));
    registry.register(
        build(
            TileItemInterface::new, ModBlocks.itemInterface, TileResourceLocations.itemInterface));
//    registry.register(
//        build(
//            TileItemManipulator::new,
//            ModBlocks.itemManipulator,
//            TileResourceLocations.itemManipulator));
//    registry.register(
//        build(
//            TileMatterPurifier::new,
//            ModBlocks.matterPurifier,
//            TileResourceLocations.matterPurifier));
    registry.register(
        build(
            TilePlayerInterface::new,
            ModBlocks.playerInterface,
            TileResourceLocations.playerInterface));
//    registry.register(
//        build(TileTeamLoader::new, ModBlocks.teamLoader, TileResourceLocations.teamLoader));

    registry.register(
        build(
            TileInstanceFurnace::new,
            ModBlocks.instantFurnace,
            TileResourceLocations.instantFurnace));
    registry.register(
        build(
            TileHyperItemReceiver::new,
            ModBlocks.hyperItemReceiver,
            TileResourceLocations.hyperItemReceiver));
    registry.register(
        build(
            TileHyperItemSender::new,
            ModBlocks.hyperItemSender,
            TileResourceLocations.hyperItemSender));
    registry.register(
        build(
            TileAlmostInfiniteBarrel::new,
            ModBlocks.almostInfiniteBarrel,
            TileResourceLocations.almostInfiniteBarrel));
    registry.register(
        build(
            TileTrueInfiniteBarrel::new,
            ModBlocks.trueInfiniteBarrel,
            TileResourceLocations.trueInfiniteBarrel));
    registry.register(
        build(
            TileHyperEnergyReceiver::new,
            ModBlocks.hyperEnergyReceiver,
            TileResourceLocations.hyperEnergyReceiver));
    registry.register(
        build(
            TileHyperEnergySender::new,
            ModBlocks.hyperEnergySender,
            TileResourceLocations.hyperEnergySender));
    registry.register(
        build(
            TileAlmostInfiniteCapacitor::new,
            ModBlocks.almostInfiniteCapacitor,
            TileResourceLocations.almostInfiniteCapacitor));
    registry.register(
        build(
            TileTrueInfiniteCapacitor::new,
            ModBlocks.trueInfiniteCapacitor,
            TileResourceLocations.trueInfiniteCapacitor));
    registry.register(
        build(
            TileHyperFluidReceiver::new,
            ModBlocks.hyperFluidReceiver,
            TileResourceLocations.hyperFluidReceiver));
    registry.register(
        build(
            TileHyperFluidSender::new,
            ModBlocks.hyperFluidSender,
            TileResourceLocations.hyperFluidSender));
    registry.register(
        build(TileAlmostInfiniteTank::new, ModBlocks.almostInfiniteTank, TileResourceLocations.almostInfiniteTank));
    registry.register(
        build(
            TileTrueInfiniteTank::new,
            ModBlocks.trueInfiniteTank,
            TileResourceLocations.trueInfiniteTank));
  }

  private static TileEntityType build(Supplier<TileEntity> e, Block block, String name) {
    return TileEntityType.Builder.create(e, block).build(null).setRegistryName(name);
  }
}
