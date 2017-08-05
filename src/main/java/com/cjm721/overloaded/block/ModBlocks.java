package com.cjm721.overloaded.block;

import com.cjm721.overloaded.block.basic.*;
import com.cjm721.overloaded.block.basic.container.BlockInfiniteBarrel;
import com.cjm721.overloaded.block.basic.container.BlockInfiniteCapacitor;
import com.cjm721.overloaded.block.basic.container.BlockInfiniteTank;
import com.cjm721.overloaded.block.basic.hyperTransfer.*;
import com.cjm721.overloaded.block.compressed.CompressedBlockHandler;
import com.cjm721.overloaded.block.fluid.BlockPureMatterFluid;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.util.IModRegistrable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.List;

public final class ModBlocks {

    public static ModBlock basicGenerator;
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


    public static ModBlock itemManipulator;
    private static List<IModRegistrable> registerList = new LinkedList<>();

    public static void init() {
        basicGenerator = new BlockCreativeGenerator();
        infiniteBarrel = new BlockInfiniteBarrel();
        infiniteTank = new BlockInfiniteTank();
        infiniteCapacitor = new BlockInfiniteCapacitor();

        hyperItemReceiver = new BlockHyperItemReceiver();
        hyperItemSender = new BlockHyperItemSender();
        hyperFluidReceiver = new BlockHyperFluidReceiver();
        hyperFluidSender = new BlockHyperFluidSender();
        hyperEnergyReceiver = new BlockHyperEnergyReceiver();
        hyperEnergySender = new BlockHyperEnergySender();

        infiniteWaterSource = new BlockInfiniteWaterSource();

        energyExtractor = new BlockEnergyExtractor();

        netherStarBlock = new BlockNetherStar();
        playerInterface = new BlockPlayerInterface();
        itemInterface = new BlockItemInterface();

        if(OverloadedConfig.developmentConfig.wipStuff) {
//            fusionCore = new BlockFusionCore();
            matterPurifier = new BlockMatterPurifier();
            pureMatterFluidBlock = new BlockPureMatterFluid();
//            itemManipulator = new BlockItemManipulator();
//            energyInjectorChest = new BlockEnergyInjectorChest();
        }

        // Disabled
        // CompressedBlockHandler.initFromConfig();
    }

    public static void addToSecondaryInit(IModRegistrable block) {
        registerList.add(block);
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        for(IModRegistrable block: registerList){
            block.registerModel();
        }
    }
}
