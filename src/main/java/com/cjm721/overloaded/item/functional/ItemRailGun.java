package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.OverloadedCreativeTabs;
import com.cjm721.overloaded.client.render.dynamic.general.ResizeableTextureGenerator;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.network.packets.RailGunFireMessage;
import com.cjm721.overloaded.network.packets.RailGunSettingsMessage;
import com.cjm721.overloaded.storage.IGenericDataStorage;
import com.cjm721.overloaded.storage.itemwrapper.GenericDataCapabilityProviderWrapper;
import com.google.common.primitives.Ints;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.cjm721.overloaded.Overloaded.MODID;
import static com.cjm721.overloaded.storage.GenericDataStorage.GENERIC_DATA_STORAGE;
import static com.cjm721.overloaded.util.WorldUtil.rayTraceWithEntities;
import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class ItemRailGun extends PowerModItem {

    @Nonnull
    private static final String RAILGUN_POWER_KEY = "railgun.power";

    public ItemRailGun() {
        setRegistryName("railgun");
        setUnlocalizedName("railgun");
        setCreativeTab(OverloadedCreativeTabs.TECH);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        IGenericDataStorage cap = stack.getCapability(GENERIC_DATA_STORAGE, null);

        if(cap != null) {
            cap.suggestUpdate();
            int energyRequirement = cap.getIntegerMap().getOrDefault(RAILGUN_POWER_KEY, OverloadedConfig.railGun.minEngery);
            tooltip.add(String.format("Power Usage: %s", NumberFormat.getInstance().format(energyRequirement)));
        }

        super.addInformation(stack, worldIn, tooltip, flagIn);
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
    @Nonnull
    @SideOnly(Side.CLIENT)
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        if(worldIn.isRemote) {
            RayTraceResult ray = rayTraceWithEntities(worldIn, playerIn.getPositionEyes(1), playerIn.getLook(1), playerIn, OverloadedConfig.railGun.maxRange);
            if (ray != null && ray.entityHit != null) {
                Vec3d moveVev = playerIn.getPositionEyes(1).subtract(ray.hitVec).normalize().scale(-1.0);

                IMessage message = new RailGunFireMessage(ray.entityHit.getEntityId(), moveVev, handIn);
                Overloaded.proxy.networkWrapper.sendToServer(message);
            } else {
                IMessage message = new RailGunFireMessage(0, Vec3d.ZERO, handIn);
                Overloaded.proxy.networkWrapper.sendToServer(message);
            }
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onMouseEvent(@Nonnull MouseEvent event) {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        if (event.getDwheel() != 0 && player != null && player.isSneaking()) {
            ItemStack stack = player.getHeldItemMainhand();
            if (player.isSneaking() && !stack.isEmpty() && stack.getItem() == this) {
                int powerDelta = Integer.signum(event.getDwheel()) * OverloadedConfig.railGun.stepEnergy;
                if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
                    powerDelta *= 100;
                }
                IMessage message = new RailGunSettingsMessage(powerDelta);
                Overloaded.proxy.networkWrapper.sendToServer(message);
                event.setCanceled(true);
            }
        }
    }

    public void handleFireMessage(@Nonnull EntityPlayerMP player, @Nonnull RailGunFireMessage message) {
        ItemStack itemStack = player.getHeldItem(message.hand);
        if (itemStack.getItem() != this) {
            return;
        }

        IEnergyStorage energy = itemStack.getCapability(ENERGY, null);

        IGenericDataStorage settingCapability = itemStack.getCapability(GENERIC_DATA_STORAGE, null);
        settingCapability.suggestUpdate();
        int energyRequired = settingCapability.getIntegerMap().getOrDefault(RAILGUN_POWER_KEY, OverloadedConfig.railGun.minEngery);

        if (energy.getEnergyStored() < energyRequired) {
            player.sendStatusMessage(new TextComponentString("Not enough power to fire."), true);
            return;
        }

        int energyExtracted = energy.extractEnergy(energyRequired, false);

        @Nullable Entity entity = player.world.getEntityByID(message.id);
        if (entity == null || entity.isDead) {
            return;
        } else if (player.getDistance(entity) > OverloadedConfig.rayGun.maxRange) {
            player.sendStatusMessage(new TextComponentString("Target out of range."), true);
        } else if (entity.attackEntityFrom(DamageSource.causePlayerDamage(player), OverloadedConfig.railGun.damagePerRF * energyExtracted)) {
            Vec3d knockback = message.moveVector.scale(energyExtracted * OverloadedConfig.railGun.knockbackPerRF);
            entity.addVelocity(knockback.x, knockback.y, knockback.z);
        }
    }

    @Override
    public Collection<ICapabilityProvider> collectCapabilities(@Nonnull Collection<ICapabilityProvider> collection, ItemStack stack, @Nullable NBTTagCompound nbt) {
        collection.add(new GenericDataCapabilityProviderWrapper(stack));

        return super.collectCapabilities(collection, stack, nbt);
    }

    public void handleSettingsMessage(@Nonnull EntityPlayerMP player, @Nonnull RailGunSettingsMessage message) {
        ItemStack itemStack = player.getHeldItem(EnumHand.MAIN_HAND);
        if (itemStack.getItem() != this) {
            return;
        }

        IGenericDataStorage cap = itemStack.getCapability(GENERIC_DATA_STORAGE, null);
        Map<String, Integer> integerMap = cap.getIntegerMap();

        int power = integerMap.getOrDefault(RAILGUN_POWER_KEY, 0) + message.powerDelta;
        power = Ints.constrainToRange(power, OverloadedConfig.railGun.minEngery, OverloadedConfig.railGun.maxEnergy);

        integerMap.put(RAILGUN_POWER_KEY, power);
        cap.suggestSave();

        player.sendStatusMessage(new TextComponentString("Power usage set to: " +  NumberFormat.getInstance().format(power)), true);
    }
}
