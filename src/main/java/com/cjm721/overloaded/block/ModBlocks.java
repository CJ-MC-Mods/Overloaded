package com.cjm721.overloaded.block;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.block.basic.*;
import com.cjm721.overloaded.block.basic.container.BlockInfiniteBarrel;
import com.cjm721.overloaded.block.basic.container.BlockInfiniteCapacitor;
import com.cjm721.overloaded.block.basic.container.BlockInfiniteTank;
import com.cjm721.overloaded.block.basic.hyperTransfer.*;
import com.cjm721.overloaded.block.compressed.BlockCompressed;
import com.cjm721.overloaded.block.compressed.CompressedBlockHandler;
import com.cjm721.overloaded.block.fluid.BlockPureMatterFluid;
import com.cjm721.overloaded.block.reactor.BlockFusionCore;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.proxy.CommonProxy;
import com.cjm721.overloaded.util.CraftingRegistry;
import com.cjm721.overloaded.util.IModRegistrable;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public final class ModBlocks {

    public static ModBlock basicGenerator;
    private static ModBlock infiniteBarrel;
    private static ModBlock infiniteTank;
    private static ModBlock infiniteCapacitor;

    private static ModBlock hyperItemReceiver;
    private static ModBlock hyperItemSender;
    private static ModBlock hyperFluidReceiver;
    private static ModBlock hyperFluidSender;
    private static ModBlock hyperEnergyReceiver;
    private static ModBlock hyperEnergySender;

    private static ModBlock infiniteWaterSource;

    private static ModBlock energyExtractor;

    public static ModBlock netherStarBlock;
    private static ModBlock playerInterface;
    private static ModBlock itemInterface;

    private static ModBlock matterPurifier;
    private static BlockPureMatterFluid pureMatterFluidBlock;
    private static ModBlock fusionCore;
    public static ModBlock energyInjectorChest;
    public static List<BlockCompressed> compressedBlocks;


    public static ModBlock itemManipulator;
    private static final List<IModRegistrable> registerList = new LinkedList<>();

    public static void init() {
        basicGenerator = registerFull(new BlockCreativeGenerator());
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

        if (OverloadedConfig.developmentConfig.wipStuff) {
            fusionCore = registerFull(new BlockFusionCore());
            matterPurifier = registerFull(new BlockMatterPurifier());
            pureMatterFluidBlock = registerBlock(new BlockPureMatterFluid());
//            itemManipulator = new BlockItemManipulator();
//            energyInjectorChest = new BlockEnergyInjectorChest();
            for (int i = 0; i < 10; i++) {
                registerFull(new InDevBlock("in_dev_block_" + i));
            }
        }

        try {
            compressedBlocks = CompressedBlockHandler.initFromConfig();
        } catch (IOException e) {
            throw new RuntimeException("IOException while trying to init Compressed Blocks", e);
        }

        for (BlockCompressed block : compressedBlocks) {
            registerBlock(block);
        }
    }

    private static void addToSecondaryInit(IModRegistrable block) {
        registerList.add(block);
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        for (IModRegistrable block : registerList) {
            block.registerModel();
        }
    }


    private static <T extends ModBlock> T registerFull(T block) {
        registerBlock(block);

        CommonProxy.itemToRegister.add(new ItemBlock(block).setRegistryName(block.getRegistryName()));

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

    public static void secondaryCompressedInit() {
        for (BlockCompressed block : compressedBlocks) {
            if (block.baseBlockInit() && block.isRecipeEnabled()) {
                CraftingRegistry.addShapedRecipe(new ItemStack(Item.getItemFromBlock(block), 1, 0), "XXX", "XXX", "XXX", 'X', new ItemStack(block.getBaseBlock(), 1, block.getBaseMeta()));
                CraftingRegistry.addShapelessRecipe(new ItemStack(block.getBaseBlock(), 9, block.getBaseMeta()), new ItemStack(Item.getItemFromBlock(block), 1, 0));

                for (int meta = 0; meta < block.getMaxCompression() - 1; meta++) {
                    CraftingRegistry.addShapedRecipe(new ItemStack(Item.getItemFromBlock(block), 1, meta + 1), "XXX", "XXX", "XXX", 'X', new ItemStack(Item.getItemFromBlock(block), 1, meta));
                    CraftingRegistry.addShapelessRecipe(new ItemStack(Item.getItemFromBlock(block), 9, meta), new ItemStack(Item.getItemFromBlock(block), 1, meta + 1));
                }
            }
        }
    }
}
