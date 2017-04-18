package com.cjm721.overloaded.common.block.basic;

import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.block.ModBlock;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockNetherStar extends ModBlock {

    public BlockNetherStar() {
        super(Material.IRON);

        setRegistryName("netherstar_block");
        setUnlocalizedName("netherstar_block");

        setHardness(10);
        setResistance(20);
        setCreativeTab(OverloadedCreativeTabs.TECH);
        register();
    }

    @Override
    public void registerRecipe() {
        GameRegistry.addRecipe(new ItemStack(this), "NNN", "NNN", "NNN", 'N', Items.NETHER_STAR);
    }

    @Override
    public void registerModel() {

    }
}
