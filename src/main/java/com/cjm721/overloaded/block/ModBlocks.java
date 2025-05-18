package com.cjm721.overloaded.block;

import com.cjm721.overloaded.block.basic.*;
import com.cjm721.overloaded.block.basic.container.*;
import com.cjm721.overloaded.block.basic.hyperTransfer.*;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import static com.cjm721.overloaded.Overloaded.BLOCKS;
import static com.cjm721.overloaded.Overloaded.ITEMS;

public final class ModBlocks {

//  public static Fluid pureMatter;

    public final static DeferredBlock<BlockCreativeGenerator> creativeGenerator = BLOCKS.registerBlock("creative_generator", rn -> new BlockCreativeGenerator());
    public static final DeferredItem<BlockItem> creativeGeneratorItem = ITEMS.registerSimpleBlockItem(creativeGenerator);
    public final static DeferredBlock<BlockAlmostInfiniteBarrel> almostInfiniteBarrel = BLOCKS.registerBlock("creative_generator", rn -> new BlockAlmostInfiniteBarrel());
    public static final DeferredItem<BlockItem> almostInfiniteBarrelItem = ITEMS.registerSimpleBlockItem(almostInfiniteBarrel);
    public final static DeferredBlock<BlockTrueInfiniteBarrel> trueInfiniteBarrel = BLOCKS.registerBlock("true_infinite_barrel", rn -> new BlockTrueInfiniteBarrel());
    public static final DeferredItem<BlockItem> trueInfiniteBarrelItem = ITEMS.registerSimpleBlockItem(trueInfiniteBarrel);
    public final static DeferredBlock<BlockAlmostInfiniteTank> almostInfiniteTank = BLOCKS.registerBlock("almost_infinite_tank", rn -> new BlockAlmostInfiniteTank());
    public static final DeferredItem<BlockItem> almostInfiniteTankItem = ITEMS.registerSimpleBlockItem(almostInfiniteTank);
    public final static DeferredBlock<BlockTrueInfiniteTank> trueInfiniteTank = BLOCKS.registerBlock("true_infinite_tank", rn -> new BlockTrueInfiniteTank());
    public static final DeferredItem<BlockItem> trueInfiniteTankItem = ITEMS.registerSimpleBlockItem(trueInfiniteTank);
    public final static DeferredBlock<BlockAlmostInfiniteCapacitor> almostInfiniteCapacitor = BLOCKS.registerBlock("almost_infinite_capacitor", rn -> new BlockAlmostInfiniteCapacitor());
    public static final DeferredItem<BlockItem> almostInfiniteCapacitorItem = ITEMS.registerSimpleBlockItem(almostInfiniteCapacitor);
    public final static DeferredBlock<BlockTrueInfiniteCapacitor> trueInfiniteCapacitor = BLOCKS.registerBlock("true_infinite_capacitor", rn -> new BlockTrueInfiniteCapacitor());
    public static final DeferredItem<BlockItem> trueInfiniteCapacitorItem = ITEMS.registerSimpleBlockItem(trueInfiniteCapacitor);
    public final static DeferredBlock<BlockHyperItemReceiver> hyperItemReceiver = BLOCKS.registerBlock("hyper_item_receiver", rn -> new BlockHyperItemReceiver());
    public static final DeferredItem<BlockItem> hyperItemReceiverItem = ITEMS.registerSimpleBlockItem(hyperItemReceiver);
    public final static DeferredBlock<BlockHyperItemSender> hyperItemSender = BLOCKS.registerBlock("hyper_item_sender", rn -> new BlockHyperItemSender());
    public static final DeferredItem<BlockItem> hyperItemSenderItem = ITEMS.registerSimpleBlockItem(hyperItemSender);
    public final static DeferredBlock<BlockHyperFluidReceiver> hyperFluidReceiver = BLOCKS.registerBlock("hyper_fluid_receiver", rn -> new BlockHyperFluidReceiver());
    public static final DeferredItem<BlockItem> hyperFluidReceiverItem = ITEMS.registerSimpleBlockItem(hyperFluidReceiver);
    public final static DeferredBlock<BlockHyperFluidSender> hyperFluidSender = BLOCKS.registerBlock("hyper_fluid_sender", rn -> new BlockHyperFluidSender());
    public static final DeferredItem<BlockItem> hyperFluidSenderItem = ITEMS.registerSimpleBlockItem(hyperFluidSender);
    public final static DeferredBlock<BlockHyperEnergyReceiver> hyperEnergyReceiver = BLOCKS.registerBlock("hyper_energy_receiver", rn -> new BlockHyperEnergyReceiver());
    public static final DeferredItem<BlockItem> hyperEnergyReceiverItem = ITEMS.registerSimpleBlockItem(hyperEnergyReceiver);
    public final static DeferredBlock<BlockHyperEnergySender> hyperEnergySender = BLOCKS.registerBlock("hyper_energy_sender", rn -> new BlockHyperEnergySender());
    public static final DeferredItem<BlockItem> hyperEnergySenderItem = ITEMS.registerSimpleBlockItem(hyperEnergySender);
    public final static DeferredBlock<BlockInfiniteWaterSource> infiniteWaterSource = BLOCKS.registerBlock("infinite_water_source", rn -> new BlockInfiniteWaterSource());
    public static final DeferredItem<BlockItem> infiniteWaterSourceItem = ITEMS.registerSimpleBlockItem(infiniteWaterSource);
    public final static DeferredBlock<BlockEnergyExtractor> energyExtractor = BLOCKS.registerBlock("energy_extractor", rn -> new BlockEnergyExtractor());
    public static final DeferredItem<BlockItem> energyExtractorItem = ITEMS.registerSimpleBlockItem(energyExtractor);
    public final static DeferredBlock<BlockNetherStar> netherStarBlock = BLOCKS.registerBlock("nether_star_block", rn -> new BlockNetherStar());
    public static final DeferredItem<BlockItem> netherStarBlockItem = ITEMS.registerSimpleBlockItem(netherStarBlock);
    public final static DeferredBlock<BlockPlayerInterface> playerInterface = BLOCKS.registerBlock("player_interface", rn -> new BlockPlayerInterface());
    public static final DeferredItem<BlockItem> playerInterfaceItem = ITEMS.registerSimpleBlockItem(playerInterface);
    public final static DeferredBlock<BlockItemInterface> itemInterface = BLOCKS.registerBlock("item_interface", rn -> new BlockItemInterface());
    public static final DeferredItem<BlockItem> itemInterfaceItem = ITEMS.registerSimpleBlockItem(itemInterface);
    public final static DeferredBlock<BlockMatterPurifier> matterPurifier = BLOCKS.registerBlock("matter_purifier", rn -> new BlockMatterPurifier());
    public static final DeferredItem<BlockItem> matterPurifierItem = ITEMS.registerSimpleBlockItem(matterPurifier);
//    public final static DeferredBlock<BlockFusionCore> fusionCore = BLOCKS.registerBlock("fusion_core", rn -> new BlockFusionCore());
//    public static final DeferredItem<BlockItem> fusionCoreItem = ITEMS.registerSimpleBlockItem(fusionCore);
    public final static DeferredBlock<BlockEnergyInjectorChest> energyInjectorChest = BLOCKS.registerBlock("energy_injector_chest", rn -> new BlockEnergyInjectorChest());
    public static final DeferredItem<BlockItem> energyInjectorChestItem = ITEMS.registerSimpleBlockItem(energyInjectorChest);
    public final static DeferredBlock<BlockTeamLoader> teamLoader = BLOCKS.registerBlock("team_loader", rn -> new BlockTeamLoader());
    public static final DeferredItem<BlockItem> teamLoaderItem = ITEMS.registerSimpleBlockItem(teamLoader);
    public final static DeferredBlock<BlockInstantFurnace> instantFurnace = BLOCKS.registerBlock("instant_furnace", rn -> new BlockInstantFurnace());
    public static final DeferredItem<BlockItem> instantFurnaceItem = ITEMS.registerSimpleBlockItem(instantFurnace);
    public final static DeferredBlock<BlockItemManipulator> itemManipulator = BLOCKS.registerBlock("item_manipulator", rn -> new BlockItemManipulator());
    public static final DeferredItem<BlockItem> itemManipulatorItem = ITEMS.registerSimpleBlockItem(itemManipulator);

//  public final static DeferredBlock<BlockFusionInterface> fusionInterface = BLOCKS.registerBlock("", rn -> new ());
}
