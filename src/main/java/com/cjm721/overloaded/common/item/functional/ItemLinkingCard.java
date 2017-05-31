package com.cjm721.overloaded.common.item.functional;

import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.block.ModBlocks;
import com.cjm721.overloaded.common.config.OverloadedConfig;
import com.cjm721.overloaded.common.config.RecipeEnabledConfig;
import com.cjm721.overloaded.common.item.ModItem;
import com.cjm721.overloaded.common.item.ModItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.cjm721.overloaded.Overloaded.MODID;

public class ItemLinkingCard extends ModItem {

    public ItemLinkingCard() {
        setMaxStackSize(1);
        setRegistryName("linking_card");
        setUnlocalizedName("linking_card");
        setCreativeTab(OverloadedCreativeTabs.TECH);

        GameRegistry.register(this);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        NBTTagCompound tag = stack.getTagCompound();
        if(tag != null && tag.hasKey("TYPE")) {
            String type = tag.getString     ("TYPE");
            int x = tag.getInteger("X");
            int y = tag.getInteger("Y");
            int z = tag.getInteger("Z");
            int worldID = tag.getInteger("WORLD");

            tooltip.add(String.format("Bound to %s at %d:%d,%d,%d", type, worldID, x, y, z));
        }
        super.addInformation(stack, playerIn, tooltip, advanced);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(MODID,"linking_card"), null);
        ModelLoader.setCustomModelResourceLocation(this, 0, location);
    }

    @Override
    public void registerRecipe() {
        if(OverloadedConfig.recipeEnabledConfig.linkingCard)
            GameRegistry.addRecipe(new ItemStack(this), "GII", "IRI", "III", 'G', Items.GOLD_NUGGET, 'I', Items.IRON_INGOT, 'R', Items.REDSTONE);
    }
}
