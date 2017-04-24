package com.cjm721.overloaded.common.block.basic;

import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.block.ModBlock;
import com.cjm721.overloaded.common.block.tile.TileItemInterface;
import com.cjm721.overloaded.common.block.tile.TileTeamLoader;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockTeamLoader extends ModBlock implements ITileEntityProvider {

    public BlockTeamLoader() {
        super(Material.ROCK);

        setRegistryName("team_loader");
        setUnlocalizedName("team_loader");

        setHardness(10);
        setCreativeTab(OverloadedCreativeTabs.TECH);
        register();

        GameRegistry.registerTileEntity(TileTeamLoader.class, MODID + ":team_loader");
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     *
     * @param worldIn
     * @param meta
     */
    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileTeamLoader();
    }

    @Override
    public void registerRecipe() {

    }

    @Override
    public void registerModel() {

    }
}
