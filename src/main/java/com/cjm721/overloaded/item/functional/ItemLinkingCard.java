package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.block.basic.hyperTransfer.base.AbstractBlockHyperReceiver;
import com.cjm721.overloaded.block.basic.hyperTransfer.base.AbstractBlockHyperSender;
import com.cjm721.overloaded.item.ModItem;
import com.cjm721.overloaded.item.ModItems;
import com.cjm721.overloaded.storage.GenericDataStorage;
import com.cjm721.overloaded.tile.hyperTransfer.base.AbstractTileHyperSender;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.cjm721.overloaded.item.ModItems.CROSS_LEVEL_POSITION;
import static com.cjm721.overloaded.item.ModItems.HYPER_BOUND_TYPE;

public class ItemLinkingCard extends ModItem {

  public ItemLinkingCard(Properties properties) {
    super(properties.stacksTo(1));
  }

  @Override
  public InteractionResult useOn(UseOnContext context) {
    Block clickedBlock = context.getLevel().getBlockState(context.getClickedPos()).getBlock();
    Optional<Player> player = Optional.ofNullable(context.getPlayer());
    if (clickedBlock instanceof AbstractBlockHyperReceiver) {
      DimensionLocation location =
          new DimensionLocation(context.getLevel().dimension().location(), context.getClickedPos());
      context.getItemInHand().set(CROSS_LEVEL_POSITION, location);
      context
          .getItemInHand()
          .set(HYPER_BOUND_TYPE, ((AbstractBlockHyperReceiver) clickedBlock).getType());
      player.ifPresent(
          p ->
              p.displayClientMessage(
                  Component.literal("Saved linking location to: ").append(location.toComponent()),
                  true));
      return InteractionResult.SUCCESS;
    }
    if (clickedBlock instanceof AbstractBlockHyperSender) {
      DimensionLocation location = context.getItemInHand().get(CROSS_LEVEL_POSITION);
      String type = context.getItemInHand().getOrDefault(HYPER_BOUND_TYPE, "");
      if (location == null) {
        player.ifPresent(
            p -> p.displayClientMessage(Component.literal("Not bound to any block."), true));

        return InteractionResult.SUCCESS;
      }
      if (!context.getLevel().isLoaded(location.pos)) {
        player.ifPresent(
            p -> p.displayClientMessage(Component.literal("Bound block is not loaded."), true));
      }
      BlockEntity blockEntity = context.getLevel().getBlockEntity(context.getClickedPos());
      if (blockEntity == null) {
        player.ifPresent(
            p ->
                p.displayClientMessage(
                    Component.literal(
                        "Bound block location is corrupt. Please report to Overloaded Issue Tracker"),
                    true));
        return InteractionResult.SUCCESS;
      }
      AbstractTileHyperSender<?, ?> hyperSender = ((AbstractTileHyperSender<?, ?>) blockEntity);
      if (!type.equals(((AbstractBlockHyperSender) clickedBlock).getType())) {
        player.ifPresent(
            p ->
                p.displayClientMessage(
                    Component.literal(
                        String.format(
                            "Cannot bind HyperSender of type %s to type %s",
                            ((AbstractBlockHyperSender) clickedBlock).getType(), type)),
                    true));
        return InteractionResult.SUCCESS;
      }
      ((AbstractTileHyperSender<?, ?>) blockEntity).setPartnerInfo(location);
      player.ifPresent(
          p ->
              p.displayClientMessage(
                  Component.literal("Bound to: ").append(location.toComponent()), true));
    }

    return super.useOn(context);
  }

  public record DimensionLocation(ResourceLocation level, BlockPos pos) {
    public static final Codec<DimensionLocation> CODEC =
        RecordCodecBuilder.create(
            instance ->
                instance
                    .group(
                        ResourceLocation.CODEC.fieldOf("level").forGetter(data -> data.level),
                        BlockPos.CODEC.fieldOf("pos").forGetter(data -> data.pos))
                    .apply(instance, DimensionLocation::new));

    public static final StreamCodec<ByteBuf, DimensionLocation> STREAM_CODEC =
        StreamCodec.composite(
            ResourceLocation.STREAM_CODEC,
            DimensionLocation::level,
            BlockPos.STREAM_CODEC,
            DimensionLocation::pos,
            DimensionLocation::new);

    public Component toComponent() {
      return Component.literal(String.format("%s:%s", level.toString(), pos.toShortString()));
    }
  }

  @Override
  public void appendHoverText(
      ItemStack stack,
      TooltipContext context,
      List<Component> tooltipComponents,
      TooltipFlag tooltipFlag) {

    String type = stack.getOrDefault(HYPER_BOUND_TYPE, "");
    DimensionLocation pos = stack.get(CROSS_LEVEL_POSITION);

    if (!type.isBlank() && pos != null) {
      tooltipComponents.add(
          Component.literal(
              String.format("Bound to %s at %s: %s", type, pos.level, pos.pos.toShortString())));
    }

    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
    //    ModelResourceLocation location =
    //        new ModelResourceLocation(new ResourceLocation(MODID, "linking_card"), null);
    //    //        ModelLoader.setCustomModelResourceLocation(this, 0, location);
    //
    //    ImageUtil.registerDynamicTexture(
    //        new ResourceLocation(MODID, "textures/item/linking_card.png"),
    //        OverloadedConfig.INSTANCE.textureResolutions.itemResolution);
  }
}
