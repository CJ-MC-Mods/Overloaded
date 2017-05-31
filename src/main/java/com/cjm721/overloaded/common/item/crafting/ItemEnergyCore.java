package com.cjm721.overloaded.common.item.crafting;

import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.config.OverloadedConfig;
import com.cjm721.overloaded.common.config.RecipeEnabledConfig;
import com.cjm721.overloaded.common.item.ModItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.cjm721.overloaded.Overloaded.MODID;

public class ItemEnergyCore extends ModItem {

    public ItemEnergyCore() {
        setMaxStackSize(64);
        setRegistryName("energy_core");
        setUnlocalizedName("energy_core");
        setCreativeTab(OverloadedCreativeTabs.TECH);

        GameRegistry.register(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(MODID,"energy_core"), null);
        ModelLoader.setCustomModelResourceLocation(this, 0, location);
    }

    @Override
    public void registerRecipe() {
        if(OverloadedConfig.recipeEnabledConfig.energyCore)
            GameRegistry.addRecipe(new ItemStack(this), "NNN", "NRN", "NNN", 'N', Items.NETHER_STAR, 'R', Blocks.REDSTONE_BLOCK);
    }
}
