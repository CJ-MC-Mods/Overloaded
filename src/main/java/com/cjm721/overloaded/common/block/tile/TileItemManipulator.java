package com.cjm721.overloaded.common.block.tile;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.UUID;

import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;
import static net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;

public class TileItemManipulator extends TileEntity implements ITickable {

    private static GameProfile FAKEPLAYER = new GameProfile(UUID.fromString("85824917-23F6-4B28-8B12-FAED16A3F66B"), "[Overloaded:Item_Manipulator]");

    private EnergyStorage energyStorage;
    private ItemStackHandler itemStack;
    private WeakReference<FakePlayer> player;
    private EnumFacing facing;

    public TileItemManipulator() {
        itemStack = new ItemStackHandler();
        energyStorage = new EnergyStorage(Integer.MAX_VALUE,Integer.MAX_VALUE,0);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        if(compound.hasKey("Item")) {
            itemStack.deserializeNBT(compound.getCompoundTag("Item"));
        }

        if(compound.hasKey("Energy")) {
            energyStorage = new EnergyStorage(Integer.MAX_VALUE,Integer.MAX_VALUE,0, compound.getInteger("Energy"));
        }
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("Item", itemStack.serializeNBT());
        compound.setInteger("Energy", energyStorage.getEnergyStored());
        return super.writeToNBT(compound);
    }

    @Override
    public void update() {
        ItemStack currentItem = itemStack.getStackInSlot(0);
        if(currentItem.isEmpty())
            return;

        FakePlayer player = getPlayer();

        BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos(this.getPos());
        for(int i = 0; i < player.interactionManager.getBlockReachDistance(); i++) {
            if(!this.getWorld().isAirBlock(blockPos.move(this.facing))) {
                EnumActionResult result = currentItem.getItem().onItemUse(player,getWorld(),blockPos,EnumHand.MAIN_HAND,facing.getOpposite(),0.5f,0.5f,0.5f);
//                System.out.println(result);
//                currentItem.onItemUse(player,this.getWorld(),blockPos,EnumHand.MAIN_HAND,
//                        this.facing.getOpposite(),0.5f,0.5f,0.5f);
//                player.interactionManager.processRightClickBlock(player,this.getWorld(),currentItem,EnumHand.MAIN_HAND,
//                        blockPos,this.facing.getOpposite(),0.5f,0.5f,0.5f);
                break;
            }
        }
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if(capability == ENERGY || capability == ITEM_HANDLER_CAPABILITY)
            return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == ENERGY)
            return (T) energyStorage;
        if(capability == ITEM_HANDLER_CAPABILITY)
            return (T) itemStack;
        return super.getCapability(capability, facing);
    }

    public TileItemManipulator setFacing(EnumFacing facing) {
        this.facing = facing;

        return this;
    }

    private FakePlayer getPlayer() {
        if(this.player == null || this.player.get() == null) {
            FakePlayer fakePlayer = FakePlayerFactory.get((WorldServer)this.getWorld(),FAKEPLAYER);
            this.player = new WeakReference<>(fakePlayer);
            fakePlayer.setLocationAndAngles(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), 0f,0f);
            fakePlayer.inventory.clear();
        }

        return this.player.get();
    }

    public void breakBlock() {
        ItemStack storedItem = itemStack.getStackInSlot(0);
        if(!storedItem.isEmpty())
            this.getWorld().spawnEntity(new EntityItem(this.getWorld(),getPos().getX(), getPos().getY(),getPos().getZ(),storedItem));
    }
}
