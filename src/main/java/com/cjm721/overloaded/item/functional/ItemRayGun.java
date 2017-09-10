package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.OverloadedCreativeTabs;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.item.ModItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;

public class ItemRayGun extends ModItem {

    public ItemRayGun() {
        setMaxStackSize(1);
        setRegistryName("ray_gun");
        setUnlocalizedName("ray_gun");
        setCreativeTab(OverloadedCreativeTabs.TECH);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(MODID, "ray_gun"), null);
        ModelLoader.setCustomModelResourceLocation(this, 0, location);

        ResizeableTextureGenerator.addToTextureQueue(new ResizeableTextureGenerator.ResizableTexture(
                new ResourceLocation(MODID, "textures/items/zapper.png"),
                new ResourceLocation(MODID, "textures/dynamic/items/zapper.png"),
                OverloadedConfig.textureResolutions.blockResolution));
    }

    @Override
    @Nonnull
    @SideOnly(Side.CLIENT)
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        if(!worldIn.isRemote)
            return ActionResult.newResult(EnumActionResult.PASS, playerIn.getHeldItem(handIn));


        RayTraceResult ray = ProjectileHelper.forwardsRaycast(playerIn,true,true,null);
        System.out.println(ray.entityHit);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
