package com.cjm721.overloaded.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by CJ on 4/2/2017.
 */
public abstract class ModBlock extends Block {
    public ModBlock(Material materialIn) {
        super(materialIn);
    }

    protected void defaultRegistery() {
        String name = this.getClass().getName();
        name = name.substring(name.lastIndexOf('.') + 1);

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
}
