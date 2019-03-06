package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.OverloadedCreativeTabs;
import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.item.ModItem;
import com.cjm721.overloaded.network.OverloadedGuiHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;

public class ItemSettingEditor extends ModItem {

    public ItemSettingEditor() {
        setMaxStackSize(1);
        setRegistryName("settings_editor");
        setTranslationKey("settings_editor");
        setCreativeTab(OverloadedCreativeTabs.TECH);
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (worldIn.isRemote) {
            playerIn.openGui(Overloaded.instance, OverloadedGuiHandler.MULTI_ARMOR, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }


    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(MODID, "settings_editor"), null);
        ModelLoader.setCustomModelResourceLocation(this, 0, location);

        ImageUtil.registerDynamicTexture(
                new ResourceLocation(MODID, "textures/items/settings_editor.png"),
                Overloaded.cachedConfig.textureResolutions.itemResolution);
    }
}
