package com.cjm721.ibhstd.common.block.basic;

import com.cjm721.ibhstd.common.IBHSTDCreativeTabs;
import com.cjm721.ibhstd.common.block.ModBlock;
import com.cjm721.ibhstd.common.block.tile.TileCreativeGenerator;
import com.cjm721.ibhstd.common.block.tile.TileInfiniteBarrel;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static com.cjm721.ibhstd.IBHSTD.MODID;

/**
 * Created by CJ on 4/6/2017.
 */
public class BlockInfiniteBarrel extends ModBlock implements ITileEntityProvider {
    public BlockInfiniteBarrel() {
        super(Material.WOOD);

        setRegistryName("BlockInfiniteBarrel");
        setUnlocalizedName("BlockInfiniteBarrel");

        setHardness(10);
        setLightOpacity(0);
        setCreativeTab(IBHSTDCreativeTabs.ENERGY_BLOCKS);
        register();
        GameRegistry.registerTileEntity(TileCreativeGenerator.class, MODID + ":infiniteBarrel");
    }

    @Override
    public void registerRecipe() {

    }

    @Override
    public void registerModel() {

    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileInfiniteBarrel();
    }
}
