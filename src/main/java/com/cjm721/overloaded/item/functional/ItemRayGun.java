package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.network.packets.RayGunMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static com.cjm721.overloaded.Overloaded.MODID;
import static com.cjm721.overloaded.util.WorldUtil.rayTraceWithEntities;
import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class ItemRayGun extends PowerModItem {

  public ItemRayGun() {
    setRegistryName("ray_gun");
    //        setTranslationKey("ray_gun");
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void addInformation(
      ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    tooltip.add(new StringTextComponent("The Little Zapper").applyTextStyle(TextFormatting.ITALIC));
    super.addInformation(stack, worldIn, tooltip, flagIn);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    ModelResourceLocation location =
        new ModelResourceLocation(new ResourceLocation(MODID, "ray_gun"), null);
    //    ModelLoader.setCustomModelResourceLocation(this, 0, location);

    ImageUtil.registerDynamicTexture(
        new ResourceLocation(MODID, "textures/items/ray_gun.png"),
        OverloadedConfig.INSTANCE.textureResolutions.itemResolution);
  }

  @Override
  @Nonnull
  @OnlyIn(Dist.CLIENT)
  public ActionResult<ItemStack> onItemRightClick(
      World worldIn, PlayerEntity playerIn, @Nonnull Hand handIn) {
    if (!worldIn.isRemote)
      new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));

    RayTraceResult ray =
        rayTraceWithEntities(
            worldIn,
            playerIn.getEyePosition(Minecraft.getInstance().getRenderPartialTicks()),
            playerIn.getLook(Minecraft.getInstance().getRenderPartialTicks()),
            playerIn,
            128);

    if (ray != null) {
      Overloaded.proxy.networkWrapper.sendToServer(new RayGunMessage(ray.getHitVec()));
    }

    return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
  }

  public void handleMessage(ServerPlayerEntity player, RayGunMessage message) {
    ItemStack itemStack = player.getHeldItem(Hand.MAIN_HAND);
    if (itemStack.getItem() != this) {
      return;
    }

    LazyOptional<IEnergyStorage> energy = itemStack.getCapability(ENERGY, null);

    if (!energy.isPresent()
        || energy.orElse(null).getEnergyStored() < OverloadedConfig.INSTANCE.rayGun.energyPerShot) {
      player.sendStatusMessage(new StringTextComponent("Not enough power to fire."), true);
      return;
    }

    Vec3d eyePos = player.getEyePosition(1);

    if (eyePos.distanceTo(message.vector) > OverloadedConfig.INSTANCE.rayGun.maxRange) {
      player.sendStatusMessage(new StringTextComponent("Target out of range."), true);
      return;
    }

    BlockRayTraceResult sanityCheckVec =
        player.world.rayTraceBlocks(
            new RayTraceContext(
                eyePos,
                message.vector,
                RayTraceContext.BlockMode.COLLIDER,
                RayTraceContext.FluidMode.NONE,
                player));
    if (sanityCheckVec.getType() != RayTraceResult.Type.MISS) {
      player.sendStatusMessage(new StringTextComponent("Target no longer in sight."), true);
      return;
    }

    energy.orElse(null).extractEnergy(OverloadedConfig.INSTANCE.rayGun.energyPerShot, false);
    player.world.addEntity(
        new LightningBoltEntity(
            player.world, message.vector.x, message.vector.y, message.vector.z, false));
  }
}
