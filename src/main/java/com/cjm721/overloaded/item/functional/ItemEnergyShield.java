package com.cjm721.overloaded.item.functional;

import com.cjm721.overloaded.OverloadedCreativeTabs;
import com.cjm721.overloaded.item.ModItem;
import com.cjm721.overloaded.storage.LongEnergyStack;
import com.cjm721.overloaded.storage.energy.IHyperHandlerEnergy;
import com.cjm721.overloaded.storage.itemwrapper.LongEnergyWrapper;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static com.cjm721.overloaded.Overloaded.MODID;
import static com.cjm721.overloaded.util.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;

public class ItemEnergyShield extends ModItem {

    private final long constantUseCost = 100L;
    private final long initialUseCost = 10000L;

    public ItemEnergyShield() {
        setMaxStackSize(1);
        setMaxDamage(500);
        setRegistryName("energy_shield");
        setTranslationKey("energy_shield");
        setCreativeTab(OverloadedCreativeTabs.TECH);

        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
    }


    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(MODID, "energy_shield"), null);
        ModelLoader.setCustomModelResourceLocation(this, 0, location);
    }

    @Override
    @Nonnull
    public EnumAction getItemUseAction(ItemStack stack) {
//        return EnumAction.BLOCK;

//        if(energy.amount == constantUseCost) {
        System.out.println("Blocking");
        return EnumAction.BLOCK;
//        }
//        System.out.println("None");
//        return EnumAction.NONE;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    @Nonnull
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            System.out.println("On Item Use");
            IHyperHandlerEnergy handler = player.getHeldItem(hand).getCapability(HYPER_ENERGY_HANDLER, null);
            LongEnergyStack energy = handler.take(new LongEnergyStack(constantUseCost), true);
            if (energy.amount == constantUseCost) {
                System.out.println("On Item Use Success");
                return EnumActionResult.SUCCESS;
            }

            return EnumActionResult.FAIL;
        }

        return EnumActionResult.PASS;
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
        if (player.isActiveItemStackBlocking()) {
            System.out.println("On Using Tick Blocking: " + count);
            return;
        }
        System.out.println("On Using Tick Not Blocking: " + count);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);

        IHyperHandlerEnergy handler = itemstack.getCapability(HYPER_ENERGY_HANDLER, null);
        LongEnergyStack energy = handler.take(new LongEnergyStack(initialUseCost), true);
        if (energy.amount == initialUseCost) {
            System.out.println("Right click Success");
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        } else {
            System.out.println("Right click FAIL");
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new LongEnergyWrapper(stack);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        IHyperHandlerEnergy handler = stack.getCapability(HYPER_ENERGY_HANDLER, null);
        tooltip.add("Energy Stored: " + handler.status().getAmount());

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, @Nonnull ItemStack newStack, boolean slotChanged) {
        if (slotChanged)
            return true;
        return oldStack.getItem() != newStack.getItem();
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    @Override
    public boolean shouldCauseBlockBreakReset(ItemStack oldStack, ItemStack newStack) {
        return oldStack.getItem() != newStack.getItem();
    }

    @Override
    public boolean getShareTag() {
        return true;
    }

    @Nullable
    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack) {
        return null;
    }

    @Override
    public boolean updateItemStackNBT(NBTTagCompound nbt) {
        return false;
    }
}
