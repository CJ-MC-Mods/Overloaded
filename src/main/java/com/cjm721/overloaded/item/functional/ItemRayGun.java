package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.OverloadedCreativeTabs;
import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.network.packets.RayGunMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static com.cjm721.overloaded.Overloaded.MODID;
import static com.cjm721.overloaded.util.WorldUtil.rayTraceWithEntities;
import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class ItemRayGun extends PowerModItem {

    public ItemRayGun() {
        setRegistryName("ray_gun");
        setTranslationKey("ray_gun");
        setCreativeTab(OverloadedCreativeTabs.TECH);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("§oThe Little Zapper");
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(MODID, "ray_gun"), null);
        ModelLoader.setCustomModelResourceLocation(this, 0, location);

        ImageUtil.registerDynamicTexture(
                new ResourceLocation(MODID, "textures/items/ray_gun.png"),
                Overloaded.cachedConfig.textureResolutions.itemResolution);
    }

    @Override
    @Nonnull
    @SideOnly(Side.CLIENT)
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        if (!worldIn.isRemote)
            new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));


        RayTraceResult ray = rayTraceWithEntities(worldIn, playerIn.getPositionEyes(Minecraft.getMinecraft().getRenderPartialTicks()), playerIn.getLook(Minecraft.getMinecraft().getRenderPartialTicks()), playerIn, 128);

        if (ray != null) {
            IMessage message = new RayGunMessage(ray.hitVec);
            Overloaded.proxy.networkWrapper.sendToServer(message);
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    public void handleMessage(EntityPlayerMP player, RayGunMessage message) {
        ItemStack itemStack = player.getHeldItem(EnumHand.MAIN_HAND);
        if (itemStack.getItem() != this) {
            return;
        }

        IEnergyStorage energy = itemStack.getCapability(ENERGY, null);

        if (energy.getEnergyStored() < Overloaded.cachedConfig.rayGun.energyPerShot) {
            player.sendStatusMessage(new TextComponentString("Not enough power to fire."), true);
            return;
        }

        Vec3d eyePos = player.getPositionEyes(1);

        if (eyePos.distanceTo(message.vector) > Overloaded.cachedConfig.rayGun.maxRange) {
            player.sendStatusMessage(new TextComponentString("Target out of range."), true);
            return;
        }

        RayTraceResult sanityCheckVec = player.world.rayTraceBlocks(eyePos, message.vector, false, false, false);
        if (sanityCheckVec != null && sanityCheckVec.typeOfHit != RayTraceResult.Type.MISS) {
            player.sendStatusMessage(new TextComponentString("Target no longer in sight."), true);
            return;
        }

        energy.extractEnergy(Overloaded.cachedConfig.rayGun.energyPerShot, false);
        player.world.addWeatherEffect(new EntityLightningBolt(player.world, message.vector.x, message.vector.y, message.vector.z, false));
    }
}
