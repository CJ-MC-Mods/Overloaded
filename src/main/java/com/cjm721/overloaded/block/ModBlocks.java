package com.cjm721.overloaded.block;

import com.cjm721.overloaded.OverloadedItemGroups;
import com.cjm721.overloaded.block.basic.*;
import com.cjm721.overloaded.block.basic.container.BlockInfiniteBarrel;
import com.cjm721.overloaded.block.basic.container.BlockInfiniteCapacitor;
import com.cjm721.overloaded.block.basic.container.BlockInfiniteTank;
import com.cjm721.overloaded.block.basic.hyperTransfer.*;
import com.cjm721.overloaded.proxy.CommonProxy;
import com.cjm721.overloaded.util.IModRegistrable;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.LinkedList;
import java.util.List;

import static com.cjm721.overloaded.Overloaded.MODID;

public final class ModBlocks {

  public static Fluid pureMatter;

  public static ModBlock creativeGenerator;
  public static ModBlock infiniteBarrel;
  public static ModBlock infiniteTank;
  public static ModBlock infiniteCapacitor;

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
  public static ModBlock itemManipulator;
  public static ModBlock fusionInterface;

  public static final List<IModRegistrable> registerList = new LinkedList<>();

  public static void init(IForgeRegistry<Block> registry) {
    String textureName = "blocks/pure_matter";

    pureMatter =
        new Fluid(
                "pure_matter",
                new ResourceLocation(MODID, textureName + "_still"),
                new ResourceLocation(MODID, textureName + "_flow"))
            .setDensity(3000)
            .setViscosity(6000)
            .setRarity(Rarity.EPIC);

    creativeGenerator = registerFull(registry, new BlockCreativeGenerator());
    infiniteBarrel = registerFull(registry, new BlockInfiniteBarrel());
    infiniteTank = registerFull(registry, new BlockInfiniteTank());
    infiniteCapacitor = registerFull(registry, new BlockInfiniteCapacitor());

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

  private static <T extends ModBlock> T registerFull(IForgeRegistry<Block> registry, T block) {
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
