package com.cjm721.overloaded.block;

import com.cjm721.overloaded.block.basic.*;
import com.cjm721.overloaded.block.basic.container.*;
import com.cjm721.overloaded.block.basic.hyperTransfer.*;
import com.cjm721.overloaded.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

import static com.cjm721.overloaded.Overloaded.*;
import static com.cjm721.overloaded.block.ModBlock.getDefaultProperties;

public final class ModBlocks {

//  public static Fluid pureMatter;

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    public final static DeferredBlock<BlockCreativeGenerator> creativeGenerator = registerBlock("creative_generator", BlockCreativeGenerator::new, () -> BlockBehaviour.Properties.of());
    //public static final DeferredItem<BlockItem> creativeGeneratorItem = ITEMS.registerSimpleBlockItem(creativeGenerator);
    public final static DeferredBlock<BlockAlmostInfiniteBarrel> almostInfiniteBarrel = registerBlock("almost_infinite_barrel",BlockAlmostInfiniteBarrel::new, () -> getDefaultProperties());
    public final static DeferredBlock<BlockTrueInfiniteBarrel> trueInfiniteBarrel = registerBlock("true_infinite_barrel",BlockTrueInfiniteBarrel::new, () -> getDefaultProperties());
    public final static DeferredBlock<BlockAlmostInfiniteTank> almostInfiniteTank = registerBlock("almost_infinite_tank",BlockAlmostInfiniteTank::new, () -> getDefaultProperties());
    public final static DeferredBlock<BlockTrueInfiniteTank> trueInfiniteTank = registerBlock("true_infinite_tank",BlockTrueInfiniteTank::new, () -> getDefaultProperties());
    public final static DeferredBlock<BlockAlmostInfiniteCapacitor> almostInfiniteCapacitor = registerBlock("almost_infinite_capacitor",BlockAlmostInfiniteCapacitor::new, () -> getDefaultProperties());
    public final static DeferredBlock<BlockTrueInfiniteCapacitor> trueInfiniteCapacitor = registerBlock("true_infinite_capacitor",BlockTrueInfiniteCapacitor::new, () -> getDefaultProperties());
    public final static DeferredBlock<BlockHyperItemReceiver> hyperItemReceiver = registerBlock("hyper_item_receiver",BlockHyperItemReceiver::new, () -> getDefaultProperties());
    public final static DeferredBlock<BlockHyperItemSender> hyperItemSender = registerBlock("hyper_item_sender",BlockHyperItemSender::new, () -> getDefaultProperties());
    public final static DeferredBlock<BlockHyperFluidReceiver> hyperFluidReceiver = registerBlock("hyper_fluid_receiver",BlockHyperFluidReceiver::new, () -> getDefaultProperties());
    public final static DeferredBlock<BlockHyperFluidSender> hyperFluidSender = registerBlock("hyper_fluid_sender",BlockHyperFluidSender::new, () -> getDefaultProperties());
    public final static DeferredBlock<BlockHyperEnergyReceiver> hyperEnergyReceiver = registerBlock("hyper_energy_receiver",BlockHyperEnergyReceiver::new, () -> getDefaultProperties());
    public final static DeferredBlock<BlockHyperEnergySender> hyperEnergySender = registerBlock("hyper_energy_sender",BlockHyperEnergySender::new, () -> getDefaultProperties());
    public final static DeferredBlock<BlockInfiniteWaterSource> infiniteWaterSource = registerBlock("infinite_water_source",BlockInfiniteWaterSource::new, () -> getDefaultProperties());
    public final static DeferredBlock<BlockEnergyExtractor> energyExtractor = registerBlock("energy_extractor",BlockEnergyExtractor::new, () -> getDefaultProperties());
    public final static DeferredBlock<BlockNetherStar> netherStarBlock = registerBlock("nether_star_block",BlockNetherStar::new, () -> getDefaultProperties());
    public final static DeferredBlock<BlockPlayerInterface> playerInterface = registerBlock("player_interface",BlockPlayerInterface::new, () -> getDefaultProperties());
    public final static DeferredBlock<BlockItemInterface> itemInterface = registerBlock("item_interface",BlockItemInterface::new, () -> getDefaultProperties());
    public final static DeferredBlock<BlockMatterPurifier> matterPurifier = registerBlock("matter_purifier",BlockMatterPurifier::new, () -> getDefaultProperties());
    public final static DeferredBlock<BlockTeamLoader> teamLoader = registerBlock("team_loader",BlockTeamLoader::new, () -> getDefaultProperties());
    public final static DeferredBlock<BlockInstantFurnace> instantFurnace = registerBlock("instant_furnace",BlockInstantFurnace::new, () -> getDefaultProperties());
    public final static DeferredBlock<BlockItemManipulator> itemManipulator = registerBlock("item_manipulator",BlockItemManipulator::new, () -> getDefaultProperties());
    public final static DeferredBlock<BlockEnergyInjectorChest> energyInjectorChest = registerBlock("energy_injector_chest",BlockEnergyInjectorChest::new, () -> getDefaultProperties());
    //    public final static DeferredBlock<BlockFusionCore> fusionCore = BLOCKS.registerBlock("fusion_core", rn -> new BlockFusionCore());
    //    public static final DeferredItem<BlockItem> fusionCoreItem = ITEMS.registerSimpleBlockItem(fusionCore);

//    public static final DeferredItem<BlockItem> almostInfiniteBarrelItem = ITEMS.registerSimpleBlockItem(almostInfiniteBarrel);
//    public static final DeferredItem<BlockItem> trueInfiniteBarrelItem = ITEMS.registerSimpleBlockItem(trueInfiniteBarrel);
//    public static final DeferredItem<BlockItem> almostInfiniteTankItem = ITEMS.registerSimpleBlockItem(almostInfiniteTank);
//    public static final DeferredItem<BlockItem> trueInfiniteTankItem = ITEMS.registerSimpleBlockItem(trueInfiniteTank);
//    public static final DeferredItem<BlockItem> almostInfiniteCapacitorItem = ITEMS.registerSimpleBlockItem(almostInfiniteCapacitor);
//    public static final DeferredItem<BlockItem> trueInfiniteCapacitorItem = ITEMS.registerSimpleBlockItem(trueInfiniteCapacitor);
//    public static final DeferredItem<BlockItem> hyperItemReceiverItem = ITEMS.registerSimpleBlockItem(hyperItemReceiver);
//    public static final DeferredItem<BlockItem> hyperItemSenderItem = ITEMS.registerSimpleBlockItem(hyperItemSender);
//    public static final DeferredItem<BlockItem> hyperFluidReceiverItem = ITEMS.registerSimpleBlockItem(hyperFluidReceiver);
//    public static final DeferredItem<BlockItem> hyperFluidSenderItem = ITEMS.registerSimpleBlockItem(hyperFluidSender);
//    public static final DeferredItem<BlockItem> hyperEnergyReceiverItem = ITEMS.registerSimpleBlockItem(hyperEnergyReceiver);
//    public static final DeferredItem<BlockItem> hyperEnergySenderItem = ITEMS.registerSimpleBlockItem(hyperEnergySender);
//    public static final DeferredItem<BlockItem> infiniteWaterSourceItem = ITEMS.registerSimpleBlockItem(infiniteWaterSource);
//    public static final DeferredItem<BlockItem> energyExtractorItem = ITEMS.registerSimpleBlockItem(energyExtractor);
//    public static final DeferredItem<BlockItem> netherStarBlockItem = ITEMS.registerSimpleBlockItem(netherStarBlock);
//    public static final DeferredItem<BlockItem> playerInterfaceItem = ITEMS.registerSimpleBlockItem(playerInterface);
//    public static final DeferredItem<BlockItem> itemInterfaceItem = ITEMS.registerSimpleBlockItem(itemInterface);
//    public static final DeferredItem<BlockItem> matterPurifierItem = ITEMS.registerSimpleBlockItem(matterPurifier);
//    public static final DeferredItem<BlockItem> energyInjectorChestItem = ITEMS.registerSimpleBlockItem(energyInjectorChest);
//    public static final DeferredItem<BlockItem> teamLoaderItem = ITEMS.registerSimpleBlockItem(teamLoader);
//    public static final DeferredItem<BlockItem> instantFurnaceItem = ITEMS.registerSimpleBlockItem(instantFurnace);
//    public static final DeferredItem<BlockItem> itemManipulatorItem = ITEMS.registerSimpleBlockItem(itemManipulator);
//  public final static DeferredBlock<BlockFusionInterface> fusionInterface = BLOCKS.registerBlock("", rn -> new ());

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> block, Supplier<BlockBehaviour.Properties> properties) {
        logger.atInfo().log("QWER CALLED");
        ResourceKey<Block> blockResourceKey = ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MODID,name));
        DeferredBlock<T> defBlock = BLOCKS.register(name, () -> block.apply(properties.get()));
        ModItems.ITEMS.registerSimpleBlockItem(defBlock);
        return defBlock;
    }
}
