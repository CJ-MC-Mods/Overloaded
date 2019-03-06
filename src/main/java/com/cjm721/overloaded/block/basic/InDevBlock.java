package com.cjm721.overloaded.block.basic;

import com.cjm721.overloaded.block.ModBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

import javax.annotation.Nonnull;

public class InDevBlock extends ModBlock {

    @Nonnull
    private final String name;

    public InDevBlock(@Nonnull String name) {
        super(Material.ROCK);
        this.name = name;
    }

    @Override
    public void baseInit() {
        setRegistryName(name);
        setTranslationKey(name);
    }

    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);
    }
}
