package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.block.tile.TileTeamLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockTeamLoader extends ModBlock {

    public BlockTeamLoader() {
        super(getDefaultProperties());
    }

    @Override
    public void baseInit() {
        setRegistryName("team_loader");
//        setTranslationKey("team_loader");

//        GameRegistry.registerTileEntity(TileTeamLoader.class, MODID + ":team_loader");
    }

    @Override
    public void registerModel() { }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileTeamLoader();
    }
}
