package com.cjm721.ibhstd.common.block.basic;

import com.cjm721.ibhstd.common.IBHSTDCreativeTabs;
import com.cjm721.ibhstd.common.ModStart;
import com.cjm721.ibhstd.common.block.ModBlock;
import com.cjm721.ibhstd.common.block.tile.TileCreativeGenerator;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by CJ on 4/5/2017.
 */
public class BlockCreativeGenerator extends ModBlock implements ITileEntityProvider{

    public BlockCreativeGenerator() {
        super(Material.CIRCUITS);

        setRegistryName("BlockCreativeGenerator");
        setUnlocalizedName("BlockCreativeGenerator");

        setHardness(10);
        setCreativeTab(IBHSTDCreativeTabs.ENERGY_BLOCKS);
        register();
        GameRegistry.registerTileEntity(TileCreativeGenerator.class, ModStart.MODID + "creativeGenerator");
    }

    @Override
    public void registerRecipe() {

    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileCreativeGenerator();
    }
}
