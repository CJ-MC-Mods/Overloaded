package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.config.OverloadedConfig;
import com.cjm721.overloaded.network.packets.RayGunMessage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

import static com.cjm721.overloaded.util.WorldUtil.rayTraceWithEntities;

public class ItemRayGun extends PowerModItem {

  public ItemRayGun(Properties properties) {
    super(properties);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void appendHoverText(
      ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
    tooltipComponents.add(Component.literal("The Little Zapper").withStyle(ChatFormatting.ITALIC));
    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
//    ModelResourceLocation location =
//        new ModelResourceLocation(new ResourceLocation(MODID, "ray_gun"), null);
//    //    ModelLoader.setCustomModelResourceLocation(this, 0, location);
//
//    ImageUtil.registerDynamicTexture(
//        new ResourceLocation(MODID, "textures/item/ray_gun.png"),
//        OverloadedConfig.INSTANCE.textureResolutions.itemResolution);
  }

  @Override
  public InteractionResult use(Level worldIn, Player playerIn, InteractionHand handIn) {
    if (!worldIn.isClientSide)
      return InteractionResult.SUCCESS;

    BlockHitResult ray =
        rayTraceWithEntities(
            worldIn,
            playerIn.getEyePosition(Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaTicks()),
            playerIn.getViewVector(Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaTicks()),
            playerIn,
            OverloadedConfig.INSTANCE.railGun.maxRange);

    if (ray != null) {
      PacketDistributor.sendToServer(new RayGunMessage(ray.getLocation()));
    }

    return InteractionResult.SUCCESS;
  }

  public void handleMessage(ServerPlayer player, RayGunMessage message) {
    ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
    if (itemStack.getItem() != this) {
      return;
    }

    IEnergyStorage opEnergy = itemStack.getCapability(Capabilities.EnergyStorage.ITEM);

    if (opEnergy == null) {
      Overloaded.logger.warn("Railgun has no Energy Capability? NBT: " + itemStack.getAttributeModifiers());
      return;
    }

    if (opEnergy.getEnergyStored() < OverloadedConfig
        .INSTANCE.rayGun.energyPerShot) {
      player.displayClientMessage(Component.literal("Not enough power to fire."), true);
      return;
    }

    Vec3 eyePos = player.getEyePosition(1);

    if (eyePos.distanceTo(message.vector) > OverloadedConfig.INSTANCE.rayGun.maxRange) {
      player.displayClientMessage(Component.literal("Target out of range."), true);
      return;
    }

    BlockHitResult sanityCheckVec =
        player.level().clip(
            new ClipContext(
                eyePos,
                message.vector,
                    ClipContext.Block.COLLIDER,
                    ClipContext.Fluid.NONE,
                player));
    if (sanityCheckVec.getType() != HitResult.Type.MISS) {
      player.displayClientMessage(Component.literal("Target no longer in sight."), true);
      return;
    }

    opEnergy.extractEnergy(OverloadedConfig.INSTANCE.rayGun.energyPerShot, false);
    LightningBolt entity = new LightningBolt(net.minecraft.world.entity.EntityType.LIGHTNING_BOLT, player.level());
    entity.moveTo(message.vector.x, message.vector.y, message.vector.z, 0,0);
    player.level().addFreshEntity(entity);
  }
}
