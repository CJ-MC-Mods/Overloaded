package com.cjm721.overloaded.common.item.functional;

import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.item.ModItem;
import com.cjm721.overloaded.common.storage.LongEnergyStack;
import com.cjm721.overloaded.common.storage.energy.IHyperHandlerEnergy;
import com.cjm721.overloaded.common.util.EnergyWrapper;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.List;

import static com.cjm721.overloaded.Overloaded.MODID;
import static com.cjm721.overloaded.common.util.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;

public class ItemEnergyShield extends ModItem {

    public final long constantUseCost = 100L;
    public final long initialUseCost = 10000L;

    public ItemEnergyShield() {
        setMaxStackSize(1);
        setMaxDamage(500);
        setRegistryName("energy_shield");
        setUnlocalizedName("energy_shield");
        setCreativeTab(OverloadedCreativeTabs.TECH);

        GameRegistry.register(this);

        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
    }


    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(MODID,"energy_shield"), null);
        ModelLoader.setCustomModelResourceLocation(this, 0, location);
    }

    @Override
    public void registerRecipe() {

    }

    @Override
    @Nonnull
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BLOCK;
//        IHyperHandlerEnergy handler = stack.getCapability(HYPER_ENERGY_HANDLER, null);
//
//        LongEnergyStack energy = handler.take(new LongEnergyStack(constantUseCost), true);
//
//        if(energy.amount == constantUseCost) {
//            System.out.println("Blocking");
//            return EnumAction.BLOCK;
//        }
//        System.out.println("None");
//        return EnumAction.NONE;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);

        if(!worldIn.isRemote) {
            IHyperHandlerEnergy handler = itemstack.getCapability(HYPER_ENERGY_HANDLER, null);
            LongEnergyStack energy = handler.take(new LongEnergyStack(initialUseCost), true);
        }
//
//        if(energy.amount == initialUseCost) {
//            System.out.println("Right click Success");
//            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
//        }
        System.out.println("Right click: " + worldIn.isRemote);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new EnergyWrapper(stack);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        IHyperHandlerEnergy handler = stack.getCapability(HYPER_ENERGY_HANDLER, null);
        tooltip.add("Energy Stored: " + handler.status().getAmount());

        super.addInformation(stack, playerIn, tooltip, advanced);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, @Nonnull ItemStack newStack, boolean slotChanged) {
        if(slotChanged)
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
        return true;
    }
}
