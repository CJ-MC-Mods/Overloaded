package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.OverloadedCreativeTabs;
import com.cjm721.overloaded.block.ModBlock;
import com.cjm721.overloaded.block.tile.TileMatterPurifier;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockMatterPurifier extends ModBlock {

    public BlockMatterPurifier() {
        super(Material.ROCK);

        setRegistryName("matter_purifier");
        setUnlocalizedName("matter_purifier");

        setHardness(10);
        setLightOpacity(0);
        setCreativeTab(OverloadedCreativeTabs.TECH);
        register();
        GameRegistry.registerTileEntity(TileMatterPurifier.class, MODID + ":matter_purifier");
    }

    @Override
    public void registerRecipe() {

    }

    @Override
    public void registerModel() {

    }
}
