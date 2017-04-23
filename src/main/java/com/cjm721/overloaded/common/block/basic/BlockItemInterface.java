package com.cjm721.overloaded.common.block.basic;

import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.block.ModBlock;
import com.cjm721.overloaded.common.block.tile.TileItemInterface;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by CJ on 4/22/2017.
 */
public class BlockItemInterface extends ModBlock implements ITileEntityProvider {

    public BlockItemInterface() {
        super(Material.ROCK);

        setRegistryName("item_interface");
        setUnlocalizedName("item_interface");

        setHardness(10);
        setCreativeTab(OverloadedCreativeTabs.TECH);
        register();

        GameRegistry.registerTileEntity(TileItemInterface.class, "item_interface");
    }

    @Override
    public void registerRecipe() {

    }

    @Override
    public void registerModel() {

    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileItemInterface();
    }
}
