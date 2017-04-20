package com.cjm721.overloaded.common.block;

import net.minecraft.block.Block;
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

public abstract class ModBlock extends Block {
    public ModBlock(@Nonnull Material materialIn) {
        super(materialIn);
    }

    protected void register() {
        GameRegistry.register(this);
        registerItemForm();

        ModBlocks.addToSecondaryInit(this);
    }

    private void registerItemForm() {
        GameRegistry.register(new ItemBlock(this), getRegistryName());
    }

    public abstract void registerRecipe();


    @SideOnly(Side.CLIENT)
    public abstract void registerModel();
}
