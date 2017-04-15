package com.cjm721.overloaded.common.block;

import mcjty.lib.compat.CompatBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public abstract class ModBlock extends CompatBlock {
    public ModBlock(@Nonnull Material materialIn) {
        super(materialIn);
    }

    protected void defaultRegistry() {
        String name = this.getClass().getName();
        name = name.substring(name.lastIndexOf('.') + 1).toLowerCase();

        setRegistryName(name);
        setUnlocalizedName(name);
    }

    protected void register() {
        GameRegistry.register(this);
        registerItemForm();
    }

    private void registerItemForm() {
        GameRegistry.register(new ItemBlock(this), getRegistryName());
    }

    public abstract void registerRecipe();


    @SideOnly(Side.CLIENT)
    public abstract void registerModel();

    @Override
    public final boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, side, hitX, hitY, hitZ);
    }

    @Override
    public final void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        super.onNeighborChange(world, pos, neighbor);
    }
}
