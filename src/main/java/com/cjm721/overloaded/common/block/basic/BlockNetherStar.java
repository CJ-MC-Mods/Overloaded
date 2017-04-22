package com.cjm721.overloaded.common.block.basic;

import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.block.ModBlock;
import com.cjm721.overloaded.common.config.RecipeEnabledConfig;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockNetherStar extends ModBlock {

    public BlockNetherStar() {
        super(Material.IRON);

        setRegistryName("nether_star_block");
        setUnlocalizedName("nether_star_block");

        setHardness(16384);
        setCreativeTab(OverloadedCreativeTabs.TECH);
        register();
    }

    @Override
    public void registerRecipe() {
        if(RecipeEnabledConfig.netherStarBlock)
            GameRegistry.addRecipe(new ItemStack(this), "NNN", "NNN", "NNN", 'N', Items.NETHER_STAR);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(MODID, "nether_star_block"), null);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);
    }


}
