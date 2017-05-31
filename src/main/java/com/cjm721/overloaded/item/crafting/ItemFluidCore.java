package com.cjm721.overloaded.item.crafting;

import com.cjm721.overloaded.OverloadedCreativeTabs;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.item.ModItem;
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

public class ItemFluidCore extends ModItem {

    public ItemFluidCore() {
        setMaxStackSize(64);
        setRegistryName("fluid_core");
        setUnlocalizedName("fluid_core");
        setCreativeTab(OverloadedCreativeTabs.TECH);

        GameRegistry.register(this);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(MODID,"fluid_core"), null);
        ModelLoader.setCustomModelResourceLocation(this, 0, location);
    }

    @Override
    public void registerRecipe() {
        if(OverloadedConfig.recipeEnabledConfig.fluidCore)
            GameRegistry.addRecipe(new ItemStack(this), "NNN", "NIN", "NNN", 'N', Items.NETHER_STAR, 'I', Blocks.IRON_BLOCK);
    }
}
