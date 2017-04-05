package com.cjm721.ibhstd.common.block.basic;

import com.cjm721.ibhstd.common.IBHSTDCreativeTabs;
import com.cjm721.ibhstd.common.ModStart;
import com.cjm721.ibhstd.common.block.ModBlock;
import com.cjm721.ibhstd.common.block.tile.ExampleTileEntity;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by CJ on 4/4/2017.
 */
public class BlockExampleTileEntity extends ModBlock implements ITileEntityProvider {

    public BlockExampleTileEntity() {
        super(Material.ROCK);

        setRegistryName("BlockExampleTileEntity");
        setUnlocalizedName("BlockExampleTileEntity");

        setHardness(10);
        setCreativeTab(IBHSTDCreativeTabs.ENERGY_BLOCKS);
        register();
        GameRegistry.registerTileEntity(ExampleTileEntity.class, ModStart.MODID + "exampleTileEntity");
    }

    @Override
    public void registerRecipe() {

    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new ExampleTileEntity();
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }
}
