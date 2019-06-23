package com.cjm721.overloaded.block;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.block.basic.*;
import com.cjm721.overloaded.block.basic.container.BlockInfiniteBarrel;
import com.cjm721.overloaded.block.basic.container.BlockInfiniteCapacitor;
import com.cjm721.overloaded.block.basic.container.BlockInfiniteTank;
import com.cjm721.overloaded.block.basic.hyperTransfer.*;
import com.cjm721.overloaded.block.fluid.BlockPureMatterFluid;
import com.cjm721.overloaded.block.reactor.BlockFusionCore;
import com.cjm721.overloaded.block.reactor.BlockFusionInterface;
import com.cjm721.overloaded.proxy.CommonProxy;
import com.cjm721.overloaded.util.IModRegistrable;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.LinkedList;
import java.util.List;

public final class ModBlocks {

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
  public static BlockPureMatterFluid pureMatterFluidBlock;
  public static ModBlock fusionCore;
  public static ModBlock energyInjectorChest;
  public static ModBlock teamLoader;
  public static ModBlock itemManipulator;
  public static ModBlock fusionInterface;

  public static final List<IModRegistrable> registerList = new LinkedList<>();

  public static void init() {
    creativeGenerator = registerFull(new BlockCreativeGenerator());
    infiniteBarrel = registerFull(new BlockInfiniteBarrel());
    infiniteTank = registerFull(new BlockInfiniteTank());
    infiniteCapacitor = registerFull(new BlockInfiniteCapacitor());

    hyperItemReceiver = registerFull(new BlockHyperItemReceiver());
    hyperItemSender = registerFull(new BlockHyperItemSender());
    hyperFluidReceiver = registerFull(new BlockHyperFluidReceiver());
    hyperFluidSender = registerFull(new BlockHyperFluidSender());
    hyperEnergyReceiver = registerFull(new BlockHyperEnergyReceiver());
    hyperEnergySender = registerFull(new BlockHyperEnergySender());

    infiniteWaterSource = registerFull(new BlockInfiniteWaterSource());

    energyExtractor = registerFull(new BlockEnergyExtractor());

    netherStarBlock = registerFull(new BlockNetherStar());
    playerInterface = registerFull(new BlockPlayerInterface());
    itemInterface = registerFull(new BlockItemInterface());

    if (Overloaded.cachedConfig.developmentConfig.wipStuff) {
      fusionCore = registerFull(new BlockFusionCore());
      fusionInterface = registerFull(new BlockFusionInterface());
      matterPurifier = registerFull(new BlockMatterPurifier());
      teamLoader = registerFull(new BlockTeamLoader());
      //            pureMatterFluidBlock = registerBlock(new BlockPureMatterFluid());
      //            itemManipulator = new BlockItemManipulator();
      //            energyInjectorChest = new BlockEnergyInjectorChest();
      for (int i = 0; i < 10; i++) {
        registerFull(new InDevBlock("in_dev_block_" + i));
      }
    }
  }

  private static void addToSecondaryInit(IModRegistrable block) {
    registerList.add(block);
  }

  @OnlyIn(Dist.CLIENT)
  public static void registerModels() {
    for (IModRegistrable block : registerList) {
      block.registerModel();
    }
  }

  private static <T extends ModBlock> T registerFull(T block) {
    registerBlock(block);

    CommonProxy.itemToRegister.add(
        new BlockItem(block, new Item.Properties()).setRegistryName(block.getRegistryName()));

    return block;
  }

  private static <T extends Block> T registerBlock(T block) {
    CommonProxy.blocksToRegister.add(block);

    if (block instanceof ModBlock) {
      ((ModBlock) block).baseInit();
    }

    if (block instanceof IModRegistrable) {
      ModBlocks.addToSecondaryInit((IModRegistrable) block);
    }

    return block;
  }
}
