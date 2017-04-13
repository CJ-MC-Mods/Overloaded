package com.cjm721.overloaded.common.item;

import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

import static com.cjm721.overloaded.Overloaded.MODID;

public class ItemLinkingCard extends ModItem {

    public ItemLinkingCard() {
        setMaxStackSize(1);
        setRegistryName("itemlinkingcard");
        setUnlocalizedName("itemlinkingcard");
        setCreativeTab(OverloadedCreativeTabs.UTILITY);

        GameRegistry.register(this);
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        NBTTagCompound tag = stack.getTagCompound();
        if(tag != null && tag.hasKey("TYPE")) {
            String type = tag.getString     ("TYPE");
            int x = tag.getInteger("X");
            int y = tag.getInteger("Y");
            int z = tag.getInteger("Z");
            int worldID = tag.getInteger("WORLD");

            tooltip.add(String.format("Bound to %s at %d:%d,%d,%d", type, worldID, x, y, z));
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(MODID,"linking_card"), null);
        ModelLoader.setCustomModelResourceLocation(this, 0, location);
    }
}
