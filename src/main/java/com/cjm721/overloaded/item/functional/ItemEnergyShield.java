package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.item.ModItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class ItemEnergyShield extends ModItem {

  private final long constantUseCost = 100L;
  private final long initialUseCost = 10000L;

  public ItemEnergyShield(Properties properties) {
    super(properties.stacksTo(1).durability(500));
    DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void registerModel() {
//    ModelResourceLocation location =
//        new ModelResourceLocation(new ResourceLocation(MODID, "energy_shield"), null);
    //    ModelLoader.setCustomModelResourceLocation(this, 0, location);
  }

//  @Override
//  @Nonnull
//  public UseAction getUseAnimation(ItemStack stack) {
//    //        return EnumAction.BLOCK;
//
//    //        if(energy.amount == constantUseCost) {
//    System.out.println("Blocking");
//    return UseAction.BLOCK;
//    //        }
//    //        System.out.println("None");
//    //        return EnumAction.NONE;
//  }

//  @Override
//  public int getUseDuration(ItemStack stack) {
//    return 72000;
//  }
//
//  @Override
//  @Nonnull
//  public InteractionResult useOn(ItemUseContext context) {
//    if (!context.getLevel().isClientSide) {
//      System.out.println("On Item Use");
//      LazyOptional<IHyperHandlerEnergy> opHandler = context.getItemInHand().getCapability(HYPER_ENERGY_HANDLER);
//      if(!opHandler.isPresent()) {
//        Overloaded.logger.warn("EnergyShield has no HyperEnergy Capability? NBT: " + context.getItemInHand().getTag());
//        return InteractionResult.FAIL;
//      }
//      IHyperHandlerEnergy handler = opHandler.orElseThrow(() -> new RuntimeException("Impossible Condition"));
//      LongEnergyStack energy = handler.take(new LongEnergyStack(constantUseCost), true);
//      if (energy.amount == constantUseCost) {
//        System.out.println("On Item Use Success");
//        return InteractionResult.SUCCESS;
//      }
//
//      return InteractionResult.FAIL;
//    }
//
//    return InteractionResult.PASS;
//  }
//
//  @Override
//  public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
//    if (player.isBlocking()) {
//      System.out.println("On Using Tick Blocking: " + count);
//      return;
//    }
//    System.out.println("On Using Tick Not Blocking: " + count);
//  }
//
//  @Nonnull
//  @Override
//  public ActionResult<ItemStack> use(
//      World worldIn, PlayerEntity playerIn, @Nonnull Hand handIn) {
//    ItemStack itemstack = playerIn.getItemInHand(handIn);
//    playerIn.startUsingItem(handIn);
//
//    LazyOptional<IHyperHandlerEnergy> opHandler = itemstack.getCapability(HYPER_ENERGY_HANDLER);
//    if(!opHandler.isPresent()) {
//      Overloaded.logger.warn("EnergyShield has no HyperEnergy Capability? NBT: " + itemstack.getTag());
//      return new ActionResult<>(InteractionResult.FAIL, itemstack);
//    }
//
//    IHyperHandlerEnergy handler = opHandler.orElseThrow(() -> new RuntimeException("Impossible Condition"));
//
//    LongEnergyStack energy = handler.take(new LongEnergyStack(initialUseCost), true);
//    if (energy.amount == initialUseCost) {
//      System.out.println("Right click Success");
//      return new ActionResult<>(InteractionResult.SUCCESS, itemstack);
//    } else {
//      System.out.println("Right click FAIL");
//      return new ActionResult<>(InteractionResult.FAIL, itemstack);
//    }
//  }
//
//  @Nullable
//  @Override
//  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
//    return new LongEnergyWrapper(stack);
//  }
//
//  @OnlyIn(Dist.CLIENT)
//  @Override
//  public void appendHoverText(
//      ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
//    stack
//        .getCapability(HYPER_ENERGY_HANDLER)
//        .ifPresent(
//            handler ->
//                tooltip.add(
//                    Component.literal("Energy Stored: " + handler.status().getAmount())));
//
//    super.appendHoverText(stack, worldIn, tooltip, flagIn);
//  }

  @Override
  public boolean shouldCauseReequipAnimation(
      ItemStack oldStack, @Nonnull ItemStack newStack, boolean slotChanged) {
    if (slotChanged) return true;
    return oldStack.getItem() != newStack.getItem();
  }

  @Override
  public boolean shouldCauseBlockBreakReset(ItemStack oldStack, ItemStack newStack) {
    return oldStack.getItem() != newStack.getItem();
  }

//  @Nullable
//  @Override
//  public CompoundTag getShareTag(ItemStack stack) {
//    return stack.getTag();
//  }
//
//  @Override
//  public boolean verifyTagAfterLoad(CompoundTag nbt) {
//    return false;
//  }
}
