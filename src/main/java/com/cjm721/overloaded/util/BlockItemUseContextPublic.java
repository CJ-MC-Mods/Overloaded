package com.cjm721.overloaded.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockItemUseContextPublic extends BlockItemUseContext {

  public BlockItemUseContextPublic(World worldIn, @Nullable PlayerEntity playerIn, Hand handIn, ItemStack stackIn, BlockRayTraceResult rayTraceResultIn) {
    super(worldIn, playerIn, handIn, stackIn, rayTraceResultIn);
  }
}
