package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.OverloadedCreativeTabs;
import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.item.ModItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

import static com.cjm721.overloaded.Overloaded.MODID;

public class ItemLinkingCard extends ModItem {

    public ItemLinkingCard() {
        setMaxStackSize(1);
        setRegistryName("linking_card");
        setTranslationKey("linking_card");
        setCreativeTab(OverloadedCreativeTabs.TECH);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag != null && tag.hasKey("TYPE")) {
            String type = tag.getString("TYPE");
            int x = tag.getInteger("X");
            int y = tag.getInteger("Y");
            int z = tag.getInteger("Z");
            int worldID = tag.getInteger("WORLD");

            tooltip.add(String.format("Bound to %s at %d:%d,%d,%d", type, worldID, x, y, z));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(MODID, "linking_card"), null);
        ModelLoader.setCustomModelResourceLocation(this, 0, location);

        ImageUtil.registerDynamicTexture(
                new ResourceLocation(MODID, "textures/items/linkingcard.png"),
                Overloaded.cachedConfig.textureResolutions.itemResolution);
    }
}
