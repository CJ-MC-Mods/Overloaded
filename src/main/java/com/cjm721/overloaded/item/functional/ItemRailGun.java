package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.OverloadedCreativeTabs;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.network.packets.RayGunMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.cjm721.overloaded.Overloaded.MODID;
import static com.cjm721.overloaded.util.WorldUtil.rayTraceWithEntities;

public class ItemRailGun extends PowerModItem {

    public ItemRailGun() {
        setRegistryName("railgun");
        setUnlocalizedName("railgun");
        setCreativeTab(OverloadedCreativeTabs.TECH);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(MODID, "railgun"), null);
        ModelLoader.setCustomModelResourceLocation(this, 0, location);

        ResizeableTextureGenerator.addToTextureQueue(new ResizeableTextureGenerator.ResizableTexture(
                new ResourceLocation(MODID, "textures/items/railgun.png"),
                new ResourceLocation(MODID, "textures/dynamic/items/railgun.png"),
                OverloadedConfig.textureResolutions.blockResolution));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        RayTraceResult ray = rayTraceWithEntities(worldIn, playerIn.getPositionEyes(1), playerIn.getLook(1), playerIn, 128);

        if(ray != null && ray.entityHit != null) {
            Vec3d moveVev = playerIn.getPositionEyes(1).subtract(ray.hitVec).normalize().scale(-20);

            ray.entityHit.addVelocity(moveVev.x,moveVev.y,moveVev.z);

            //worldIn.createExplosion(playerIn,ray.hitVec.x,ray.hitVec.y,ray.hitVec.z,100,true);
//            IMessage message = new RayGunMessage(ray.hitVec);
//            Overloaded.proxy.networkWrapper.sendToServer(message);
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
}
