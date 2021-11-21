package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.network.packets.RayGunMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
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
import net.minecraft.util.math.vector.Vector3d;
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
  public void appendHoverText(
      ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    tooltip.add(new StringTextComponent("The Little Zapper").withStyle(TextFormatting.ITALIC));
    super.appendHoverText(stack, worldIn, tooltip, flagIn);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    ModelResourceLocation location =
        new ModelResourceLocation(new ResourceLocation(MODID, "ray_gun"), null);
    //    ModelLoader.setCustomModelResourceLocation(this, 0, location);

    ImageUtil.registerDynamicTexture(
        new ResourceLocation(MODID, "textures/item/ray_gun.png"),
        OverloadedConfig.INSTANCE.textureResolutions.itemResolution);
  }

  @Override
  @Nonnull
  @OnlyIn(Dist.CLIENT)
  public ActionResult<ItemStack> use(
      World worldIn, @Nonnull PlayerEntity playerIn, @Nonnull Hand handIn) {
    if (!worldIn.isClientSide)
      new ActionResult<>(ActionResultType.SUCCESS, playerIn.getItemInHand(handIn));

    RayTraceResult ray =
        rayTraceWithEntities(
            worldIn,
            playerIn.getEyePosition(Minecraft.getInstance().getFrameTime()),
            playerIn.getViewVector(Minecraft.getInstance().getFrameTime()),
            playerIn,
            OverloadedConfig.INSTANCE.railGun.maxRange);

    if (ray != null) {
      Overloaded.proxy.networkWrapper.sendToServer(new RayGunMessage(ray.getLocation()));
    }

    return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getItemInHand(handIn));
  }

  public void handleMessage(ServerPlayerEntity player, RayGunMessage message) {
    ItemStack itemStack = player.getItemInHand(Hand.MAIN_HAND);
    if (itemStack.getItem() != this) {
      return;
    }

    LazyOptional<IEnergyStorage> opEnergy = itemStack.getCapability(ENERGY);

    if (!opEnergy.isPresent()) {
      Overloaded.logger.warn("Railgun has no Energy Capability? NBT: " + itemStack.getTag());
      return;
    }
    IEnergyStorage energy = opEnergy.orElseThrow(() -> new RuntimeException("Impossible Condition"));

    if (energy.getEnergyStored() < OverloadedConfig
        .INSTANCE.rayGun.energyPerShot) {
      player.displayClientMessage(new StringTextComponent("Not enough power to fire."), true);
      return;
    }

    Vector3d eyePos = player.getEyePosition(1);

    if (eyePos.distanceTo(message.vector) > OverloadedConfig.INSTANCE.rayGun.maxRange) {
      player.displayClientMessage(new StringTextComponent("Target out of range."), true);
      return;
    }

    BlockRayTraceResult sanityCheckVec =
        player.level.clip(
            new RayTraceContext(
                eyePos,
                message.vector,
                RayTraceContext.BlockMode.COLLIDER,
                RayTraceContext.FluidMode.NONE,
                player));
    if (sanityCheckVec.getType() != RayTraceResult.Type.MISS) {
      player.displayClientMessage(new StringTextComponent("Target no longer in sight."), true);
      return;
    }

    energy.extractEnergy(OverloadedConfig.INSTANCE.rayGun.energyPerShot, false);
    LightningBoltEntity entity = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, player.level);
    entity.moveTo(message.vector.x, message.vector.y, message.vector.z, 0,0);
    player.level.addFreshEntity(entity);
  }
}
