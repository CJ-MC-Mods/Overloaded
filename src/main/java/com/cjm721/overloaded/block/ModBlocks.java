package com.cjm721.overloaded.block;

import com.cjm721.overloaded.OverloadedItemGroups;
import com.cjm721.overloaded.block.basic.*;
import com.cjm721.overloaded.block.basic.container.*;
import com.cjm721.overloaded.block.basic.hyperTransfer.*;
import com.cjm721.overloaded.proxy.CommonProxy;
import com.cjm721.overloaded.util.IModRegistrable;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.LinkedList;
import java.util.List;

public final class ModBlocks {

//  public static Fluid pureMatter;

  public static ModBlock creativeGenerator;
  public static ModBlock almostInfiniteBarrel;
  public static ModBlock trueInfiniteBarrel;
  public static ModBlock almostInfiniteTank;
  public static ModBlock trueInfiniteTank;
  public static ModBlock almostInfiniteCapacitor;
  public static ModBlock trueInfiniteCapacitor;

  public static ModBlock hyperItemReceiver;
  public static ModBlock hyperItemSender;
  public static ModBlock hyperFluidReceiver;
  public static ModBlock hyperFluidSender;
  public static ModBlock hyperEnergyReceiver;
  public static ModBlock hyperEnergySender;

  public static ModBlock infiniteWaterSource;

  public static ModBlock energyExtractor;

  public static ModBlock netherStarBlock;
  public static ModBlock playerInterface;
  public static ModBlock itemInterface;

  public static ModBlock matterPurifier;
  public static ModBlock fusionCore;
  public static ModBlock energyInjectorChest;
  public static ModBlock teamLoader;
  public static ModBlockContainer instantFurnace;
  public static ModBlock itemManipulator;
  public static ModBlock fusionInterface;

  public static final List<IModRegistrable> registerList = new LinkedList<>();

  public static void init(IForgeRegistry<Block> registry) {
    String textureName = "blocks/pure_matter";

//    pureMatter =
//        new Fluid(
//                "pure_matter",
//                new ResourceLocation(MODID, textureName + "_still"),
//                new ResourceLocation(MODID, textureName + "_flow"))
//            .setDensity(3000)
//            .setViscosity(6000)
//            .setRarity(Rarity.EPIC);

    creativeGenerator = registerFull(registry, new BlockCreativeGenerator());
    almostInfiniteBarrel = registerFull(registry, new BlockAlmostInfiniteBarrel());
    trueInfiniteBarrel = registerFull(registry, new BlockTrueInfiniteBarrel());
    almostInfiniteTank = registerFull(registry, new BlockAlmostInfiniteTank());
    trueInfiniteTank = registerFull(registry, new BlockTrueInfiniteTank());
    almostInfiniteCapacitor = registerFull(registry, new BlockAlmostInfiniteCapacitor());
    trueInfiniteCapacitor = registerFull(registry, new BlockTrueInfiniteCapacitor());

    hyperItemReceiver = registerFull(registry, new BlockHyperItemReceiver());
    hyperItemSender = registerFull(registry, new BlockHyperItemSender());
    hyperFluidReceiver = registerFull(registry, new BlockHyperFluidReceiver());
    hyperFluidSender = registerFull(registry, new BlockHyperFluidSender());
    hyperEnergyReceiver = registerFull(registry, new BlockHyperEnergyReceiver());
    hyperEnergySender = registerFull(registry, new BlockHyperEnergySender());

    infiniteWaterSource = registerFull(registry, new BlockInfiniteWaterSource());

    energyExtractor = registerFull(registry, new BlockEnergyExtractor());

    netherStarBlock = registerFull(registry, new BlockNetherStar());
    playerInterface = registerFull(registry, new BlockPlayerInterface());
    itemInterface = registerFull(registry, new BlockItemInterface());
    instantFurnace = registerFull(registry, new BlockInstantFurnace());

    //    fusionCore = registerFull(registry, new BlockFusionCore());
    //    fusionInterface = registerFull(registry, new BlockFusionInterface());
    //    matterPurifier = registerFull(registry, new BlockMatterPurifier());
    //    teamLoader = registerFull(registry, new BlockTeamLoader());
    //    pureMatterFluidBlock = registerBlock(new BlockPureMatterFluid());
    //    itemManipulator = new BlockItemManipulator();
    //    energyInjectorChest = new BlockEnergyInjectorChest();
    //    for (int i = 0; i < 10; i++) {
    //      registerFull(registry, new InDevBlock("in_dev_block_" + i));
    //    }
  }

  private static void addToSecondaryInit(IModRegistrable block) {
    registerList.add(block);
  }

  private static <T extends Block> T registerFull(IForgeRegistry<Block> registry, T block) {
    registerBlock(registry, block);

    CommonProxy.itemToRegister.add(
        new BlockItem(block, new Item.Properties().group(OverloadedItemGroups.TECH))
            .setRegistryName(block.getRegistryName()));

    return block;
  }

  private static <T extends Block> T registerBlock(IForgeRegistry<Block> registry, T block) {
    CommonProxy.blocksToRegister.add(block);

    if (block instanceof IModRegistrable) {
      ModBlocks.addToSecondaryInit((IModRegistrable) block);
    }

    registry.register(block);
    return block;
  }
}
