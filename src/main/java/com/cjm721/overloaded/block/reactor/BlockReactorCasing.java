package com.cjm721.overloaded.block.reactor;

import com.cjm721.overloaded.block.ModBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class BlockReactorCasing extends ModBlock {

    public BlockReactorCasing(@Nonnull Material materialIn) {
        super(Material.IRON);
    }

    @Override
    public void baseInit() {
        setRegistryName("reactor_casing");
        setTranslationKey("reactor_casing");
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), null);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);
    }
}
